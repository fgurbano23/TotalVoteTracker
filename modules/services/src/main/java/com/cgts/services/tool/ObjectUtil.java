package com.cgts.services.tool;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 
 * Clase que se encarga del procesamiento de los objetos.
 *
 * @author MG Grupo
 *
 **/
public class ObjectUtil {

	/**
	 * 
	 * Carga un objeto especifico.
	 * 
	 * @param String className Nombre de la clase que se cargara.
	 *            
	 * @return
	 * 
	 * @throws Throwable
	 *
	 *
	 **/
	public static synchronized Object loadObject(String className)
			throws Throwable {

		ClassLoader classloader = Thread.currentThread()
				.getContextClassLoader();
		Object obj = null;

		try {
			obj = classloader.loadClass(className).newInstance();
		} catch (Throwable e) {
			obj = Class.forName(className).newInstance();
		}

		return obj;
	}

	/**
	 * 
	 * Busqueda de un objeto corriendo en memoria.
	 * 
	 * @param String nameObject Nombre objeto a buscar.
	 *            
	 * @return
	 * 
	 * @throws Throwable
	 *
	 *
	 **/
	public static synchronized Object findObject(String nameObject) {

		Object obj = null;

		ThreadGroup gObjects = Thread.currentThread().getThreadGroup();
		Thread objects[] = new Thread[gObjects.activeCount()];

		gObjects.enumerate(objects);

		for (int i = 0; i < objects.length; i++) {
			if (objects[i].getName().equals(nameObject)) {
				obj = objects[i];
				break;
			}
		}

		return obj;
	}

	/**
	 * 
	 * Serializa objetos que luegos seran guardados en BD o en disco o enviados
	 * por stream
	 * 
	 * @param Object obj
	 *            objecto completo a serializar, el mismo debe implementar
	 *            Serialize
	 *            
	 * @return
	 * 
	 * @throws Throwable
	 *
	 *
	 **/
	public static synchronized byte[] serializeObject(Object obj)
			throws Throwable {
		byte[] res = null;

		ByteArrayOutputStream byteObject = new ByteArrayOutputStream();
		ObjectOutputStream objOut = new ObjectOutputStream(byteObject);
		objOut.writeObject(obj);
		objOut.close();
		res = byteObject.toByteArray();
		byteObject.close();

		return res;
	}
	
	/**
	 * 
	 * Deserializa objetos que luegos seran guardados en BD o en disco o enviados
	 * por stream
	 * 
	 * @param byte[] obj
	 *            objecto completo a serializar, el mismo debe implementar
	 *            Serialize
	 *            
	 * @return
	 * 
	 * @throws Throwable
	 *
	 *
	 **/
	public static synchronized Object deSerializeObject(byte[] obj) throws Throwable{
		Object res=null;
		
		ByteArrayInputStream byteObj = new ByteArrayInputStream(obj);
		ObjectInputStream objIn = new ObjectInputStream(byteObj);
		res = objIn.readObject();
		
		byteObj.close();
		objIn.close();
		
		return res;
	}

}
