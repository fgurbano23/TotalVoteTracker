package com.cgts.services.tool;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * 
 * Clase para contener los helpers en procesamientos de tareas.
 * 
 * @author MG Grupo
 *
 **/
public class Utils {
	
	/**
	 * 
	 * Verifica si el codigo proporcionado un un codigo de error, 
	 * segun regla de composicion de los codigos.
	 * 
	 * @param code codigo del tipo @see ResultCode code
	 * 
	 * @return true para el caso de ser un codigo de error, false al contrario.
	 * 
	 * 
	 **/
	public static boolean isErrorCode(int code){
		return String.valueOf(code).matches("8[0-9]{2}");
		
	}
	
	/**
	 * 
	 * Convierte la fecha al patron indicado en format.
	 * 
	 * @param 'Date d
	 * 
	 * @param 'String format patron de la fecha, ejemplo yyyy-MM-dd HH:mm:ss
	 * 
	 * @return String representa la fecha en formato dado
	 * 
	 * @throws Throwable
	 * 
	 * 
	 **/
	public static String formatDate(Date d, String format) throws Throwable{
		SimpleDateFormat f = new SimpleDateFormat();
		f.applyPattern(format);
		return f.format(d);
	}

	/**
	 * 
	 * Main de prueba de clase.
	 * 
	 * 
	 **/
	public static void main(String[] args){
		Date fecha = new Date(System.currentTimeMillis());
		try {
			System.out.println("Fecha formateada:" + Utils.formatDate(fecha, "yyyy-MM-dd HH:mm:ss"));
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 
	 * CompareVersion.
	 * 
	 * @param 'String versionSS
	 * 
	 * @param 'String versionHiCC
	 * 
	 * Retorna la comparacion de las version en formato X[.<Letter>]
	 * -1  si String versionSS < String versionHiCC
	 * 0   si String versionSS == String versionHiCC
	 * 1   si String versionSS > String versionHiCC
	 * null si la comparacion no ha podido ser establecida
	 * 
	 **/
	public static Integer compareVersion(String versionSS, String versionHiCC){
		if(versionSS== null || versionHiCC== null)
            throw new IllegalArgumentException("Versions identifiers can not be null");
        if(!versionSS.matches("[0-9]+(\\.[0-9]+)*") || !versionHiCC.matches("[0-9]+(\\.[0-9]+)*"))
            throw new IllegalArgumentException("Invalid version format, must be only numbers separated by dot");
		Integer comp = versionSS.equals(versionHiCC)?0:null;
		if(comp == null){
			String[] arrayVersionSS = versionSS.split("\\.");
			String[] arrayVersionHiCC = versionHiCC.split("\\.");
			int lvSS=arrayVersionSS.length;
			int lvCC=arrayVersionHiCC.length;
			int i =0, j =0;
			while( i < lvSS && j<lvCC && arrayVersionSS[i].equals(arrayVersionHiCC[j])){++i; ++j;}
			if(i>=lvSS || j>=lvCC)
			{
				if(lvSS > lvCC){
					comp = 1;
				}else if(lvSS < lvCC){
					comp = -1;
				}
			}else{
				comp = Integer.parseInt(arrayVersionSS[i]) > Integer.parseInt(arrayVersionHiCC[i])?1:-1;
			}
		}
		return comp;
	}
}
