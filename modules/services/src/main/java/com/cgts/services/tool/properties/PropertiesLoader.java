package com.cgts.services.tool.properties;

import com.cgts.services.tool.hotreload.HotReloadingHandler;
import com.cgts.services.tool.hotreload.PropertiesReloader;
import org.apache.commons.vfs2.FileSystemException;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 
 * Manejador de carga de archivo de propiedades,
 * se encarga de realizar la carga de los archivos 
 * '*.properties' que se encuentran dentro de este paquete.
 * 
 * @author MG Grupo
 * 
 **/

public class PropertiesLoader {
	
	private static final String PROPERTY_FILENAME_PATTERN =  ".+\\.properties$";
	private static Boolean hotReloadInitialized = false;
	private static Map<String, String> properties = new ConcurrentHashMap<String, String>();
	private static List<HotReloadingHandler> reloaders = new ArrayList<HotReloadingHandler>();
	
	/**
	 * 
	 * Datos que se precargan en la clase al inicializarla.
	 * 
	 * 
	 **/
	static  {
		String path = "";
		path = System.getProperty("catalina.home").concat("/conf/");
		//path = "/Users/gmancipe/Desarrollo/projectsJava/TotalVoteTracker/conf/";
		//path = "/datos/projects/tvt/conf/";
		try {
			loadPath(path);
			hotReloadInitialized = true;
		} catch (FileSystemException | InterruptedException e1) {
			e1.printStackTrace();
		}
		try {
			loadFile(PropertiesLoader.class.getResourceAsStream("config.properties"));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * Lee los properties asociados a la configuracion del SS disponibles en directorios especificos 
	 * directorio haciendolos accesibles a traves de un unico objeto.
	 * 
	 * @param String path ruta al directorio donde estan contenidos los archivos .properties
	 * 
	 * @throws FileSystemException 
	 * 
	 * @throws InterruptedException 
	 *
	 * 
	 **/
	public static void loadPath(String path) throws FileSystemException, InterruptedException{
		File source = new File(path);
		HotReloadingHandler h = null;
		
		if(source.isDirectory()){
			FileFilter filter = new FileFilter() {
				@Override
				public boolean accept(File files) {
					return files.isFile() && !files.getName().startsWith("loggin") && !files.getName().startsWith("catalina");
				}
			};
			
			for (final File fileEntry : source.listFiles(filter)) {
				if(fileEntry.getName().matches(PROPERTY_FILENAME_PATTERN)){
					if(!hotReloadInitialized){
						h = new PropertiesReloader(fileEntry);
						h.startListening(h);
						reloaders.add(h);
					}
					processFile(fileEntry);
				}
			}
		}else if(source.isFile()){
			if (source.getName().matches(PROPERTY_FILENAME_PATTERN)) {
				processFile(source);
			}
			
		}else{
			try {
				throw new Exception("La ruta suministrada no es un directorio valido: "+path);
			} catch (Exception e) {
		
				e.printStackTrace();
			}
		}	
	}
	
	/**
	 * 
	 * Funcion que se encarga de procesar el archivo.
	 * 
	 * @param File file archivo *.properties
	 *
	 * 
	 **/
	public static void processFile(File file){
		if(file.isFile()){
			try {
				loadFile(new FileInputStream(file));
			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
			} catch (IOException e) {
				
				e.printStackTrace();
			}			
		}
	}

	/**
	 * 
	 * Funcion que se encarga de cargar en 
	 * Map<String, String> properties los datos que se le pasa en el archivo.
	 * 
	 * @param InputStream file archivo *.properties
	 *
	 **/
	public static void loadFile(InputStream file) throws IOException{
		Properties prop = new Properties();
		prop.load(file);
		String key = "";
		Enumeration<Object> props = prop.keys();
		while (props.hasMoreElements()){
			key = (String) props.nextElement();
			properties.put(key, prop.getProperty(key));
		}
	}

	/**
	 * 
	 * Obtiene el valor asociado al key suministrado.
	 * 
	 * @param String key clave del valor a consultar
	 * 
	 * @return valor asociado o null en caso de no existir
	 * 
	 *
	 **/
	public static String getProperty(String key){
		return properties.containsKey(key)? properties.get(key): null;
	}
	
	/**
	 * 
	 * Obtiene los valores almacenados en los properties que representan
	 * una estructura compuesta (array).
	 * 
	 * @param String key clave del valor a consultar.
	 * 
	 * @param Boolean setProperties booleano encargado de definir como se va a unificar los datos.
	 * 
	 * @return Map de los valores almacenados
	 * 
	 * @throws InvalidPropertiesFormatException
	 * 
	 * @throws IOException
	 * 
	 * 
	 **/
	public static Map<String, PropertiesElement> getArray(String key, Boolean setProperties) throws InvalidPropertiesFormatException, IOException{
		String value = getProperty(key);
		Map<String, PropertiesElement> result = new HashMap<String, PropertiesElement>();
		if(value != null){
			String[] array = value.split(",");
			String[] subarray = null;
			for(String string : array){
				subarray = string.split(";");
				if(subarray.length==3){
					result.put(subarray[0], new PropertiesElement(subarray[0],Long.parseLong(subarray[1]), Integer.parseInt(subarray[2])));
				}else{
					result.put(subarray[0], new PropertiesElement(subarray[0],Long.parseLong(subarray[1]), 1));
				}
				if(setProperties){
					System.setProperty(subarray[0], subarray[1]);
				}
			}
		}
		return result;
	}
	
	public static Map<String, PropertiesElement> getArray(String key) throws InvalidPropertiesFormatException, IOException{
		return getArray(key, false);
	}
	
	/**	 
	 * 
	 * Obtiene la propiedad deseada como objeto PropertiesElement.
	 * 
	 * @param key clave del valor a transformar
	 * 
	 * 
	 **/
	public static PropertiesElement getArrayAsPropertieElement(String key){
		String[] array = getProperty(key).split(";");
		return new PropertiesElement(array[0],Long.parseLong(array[1]), Integer.parseInt(array[2]));
	}
	
	/**	 
	 * 
	 * Funcion que especifica los nombres de los agentes que se buscaran en el *.properties.
	 * 
	 * @throws InvalidPropertiesFormatException, IOException
	 * 
	 * 
	 **/
	public static void reloadArrays() throws InvalidPropertiesFormatException, IOException{
		getArray("broker_agents_array", true);
		getArray("error_control_array", true);
		getArray("agents_array", true);
	}
	
	/**	 
	 * 
	 * Funcion que detiene hotReloadingHandler.
	 * 
	 * @throws Throwable
	 * 
	 * 
	 **/
	public static void destroy() throws Throwable {
		for (HotReloadingHandler hotReloadingHandler : reloaders) {
			hotReloadingHandler.getFileMonitor().stop();
		}
		reloaders.clear();
	}
}
