package com.cgts.services.tool;

import java.io.*;

/**
 *	
 *  Clase que se encarga de guardar, eliminar y leer archivos. 
 *  
 *  @author MG Grupo
 *   
 **/
public class MGFileTool {
	

	
	/**
	 *	
	 *  Funcion que guarda en una ruta especifica de los datos deseados.
	 *  
	 *  @param 'String filePath Ruta final del directorio.
	 *  
	 *  @param 'byte[] fileData Informacion deseada a guarda.
	 *  
	 *  @throws Exception 
	 *  
	 *   
	 **/
	public static synchronized void saveFile(String filePath, byte[] fileData) throws Exception {
		
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(filePath);

			BufferedOutputStream outputStream = new BufferedOutputStream(fos);
			outputStream.write(fileData);
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception(e);
		}

	}
	
	/**
	 *	
	 *  Funcion que guarda en una ruta especifica de los datos deseados.
	 *  
	 *  @param 'String path Ruta del directorio.
	 *  
	 *  @param 'String name Nombre del archivo.
	 *  
	 *  @param 'byte[] fileData Informacion deseada a guarda.
	 *  
	 *  @throws Exception 
	 *  
	 *   
	 **/
	public static synchronized void saveFile(String path,String name, byte[] fileData) throws Exception {
		
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(path+name);
			BufferedOutputStream outputStream = new BufferedOutputStream(fos);
			outputStream.write(fileData);
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception(e);
		}

	}
	
	/**
	 *	
	 *  Funcion que lee una ruta especifica de los datos deseados.
	 *  
	 *  @param 'String filePath Ruta final del directorio.
	 *  
	 *  @throws Exception 
	 *  
	 *   
	 **/
	public static synchronized byte[] readFile(String filePath) throws Exception{
			
		File file = new File(filePath);
		FileInputStream fis;
		byte[] dataFile;
		try {
			fis = new FileInputStream(file);
			BufferedInputStream inputStream = new BufferedInputStream(fis);
			dataFile = new byte[(int) file.length()];
			inputStream.read(dataFile);
			inputStream.close();
			
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception(e);
		}
		
		return dataFile;
	}
	
	/**
	 *	
	 *  Funcion que lee una ruta especifica de los datos deseados.
	 *  
	 *  @param 'String path Ruta del directorio.
	 *  
	 *  @param 'String name Nombre del archivo.
	 *  
	 *  @throws Exception 
	 *  
	 *   
	 **/
	public static synchronized byte[] readFile(String path,String name) throws Exception{
	
		return MGFileTool.readFile(path,name,false);
	}
	
	/**
	 *	
	 *  Funcion que lee una ruta especifica de los datos deseados.
	 *  
	 *  @param 'String path Ruta del directorio.
	 *  
	 *  @param 'String name Nombre del archivo.
	 *  
	 *  @param 'boolean delete Booleano que define si luego de la
	 *  lectura del archivo se borrara.
	 *  
	 *  @throws Exception 
	 *  
	 *   
	 **/
	public static synchronized byte[] readFile(String path,String name, boolean delete) throws Exception{
	
		File file = new File(path+name);
		FileInputStream fis=null;
		BufferedInputStream inputStream=null;
		byte[] dataFile;
		try {
			fis = new FileInputStream(file);
			inputStream = new BufferedInputStream(fis);
			
			dataFile = new byte[(int) file.length()];
			
			inputStream.read(dataFile);
			inputStream.close();
			if(delete){
				file.delete();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception(e);
		}
		
		return dataFile;
	}
}
