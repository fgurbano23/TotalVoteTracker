package com.cgts.services.tool;

import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * 
 * Clase para el manejo del registro del Log.
 *  Maneja los siguientes niveles de logging (Se colocan de manera jerarquica, esto es
 *  que cada nivel engloga a los posteriores): 
 *  	1. Debug
 *  	2. Info
 *  	3. Warns
 *  	4. Error
 *  	  
 * Para no afectar el performance usuar los mensajes de la siguiente forma
 * "Mensaje a registrar en el log {} mas informacion {}", donde cada {} representa
 * cada el placeholder para ubicar la insercion del texto.
 * {@code
 *   LoggerHandler log = new LoggerHandler(MainTest.class);
 *   log.doInfo("Ha ourrido un error por {}, con el ID transaction  {}", "Componente 01","t0001");
 * }
 *
 * @author MG Grupo
 *
 **/
public class LoggerHandler {
	
	/**
	 * 
	 * Representacion de los contextos a usar para el registro del log.
	 * 
	 *
	 **/
	public enum ContextLog {
    	ERROR_BL("error_bl","error_bl.log"),
    	ESTADISTICAS("estadisticas","estadisticas.log"),
    	INFO("info_app","info_app.log"),
    	DEBUG("debug_app","debug_app.log"),
    	DEBUG_QUEUE("queue_app","queue_app.log");
        	
    	private String logName;
    	private String key;
    	
    	private ContextLog(String key,String name) {
			this.key = key;
    		this.logName = name;
		}

    	public String getLogName(){
    		return this.logName;
    	}	  
    	
    	public String getKey(){
    		return this.key;
    	}
    }
	
    private Logger logger = null;
    private ContextLog context = ContextLog.ERROR_BL;
    private final String DEFAULT_LOGNAME = "logFileName";
    
    /**
	 * 
	 * Constructor base de la clase.
	 * 
	 * 	 
	 **/
	public LoggerHandler(Class<?> clase) {
		String path = System.getProperty("catalina.home").concat("/conf/");
		String log4jConfigFile = path + File.separator + "log4j2.xml";
		// Se coloca de manera intencional el valor de la propiedad antes de inicializar cualquier nuevo logger.
		System.setProperty("log4j.configurationFile", log4jConfigFile);
		logger = LoggerFactory.getLogger(clase);
	}
	
	public LoggerHandler(Class<?> clase, ContextLog context) {
		 this(clase);
		 this.context = context;
	}
	
	/**
	 * 
	 * Registra los mensajes a un log level Info.
	 * 
	 * @param ContextLog context Contexto que contiene el nombre con el que se logeara.
	 * 
	 * @param String message Contenido del mensaje a registrar, con los placeholders indicados.
	 * 
	 * @param Object values vars args
	 *
	 * 
	 **/
	public void doInfo(ContextLog context,String message,Object... values  ){
		 ThreadContext.put(DEFAULT_LOGNAME, context.getKey());
		 logger.info(message,values);
		 ThreadContext.remove(DEFAULT_LOGNAME);
	}
		
	/**
	 * 
	 * Registra los mensajes a un log level Info.
	 * 
	 * @param String message Contenido del mensaje a registrar, con los placeholders indicados.
	 * 
	 * @param Object values vars args
	 *
	 * 
	 **/
	public void doInfo(String message,Object... values){
		this.doInfo(this.context,message, values);
	}
	
	/**
	 * 
	 * Registra los mensajes a un log level Error.
	 * 
	 * @param ContextLog context Contexto que contiene el nombre con el que se logeara.
	 * 
	 * @param String message Contenido del mensaje a registrar, con los placeholders indicados
	 * 
	 * @param Object values vars args
	 *
	 * 
	 **/
	public void doError(ContextLog context,String message,Object... values  ){
		 ThreadContext.put(DEFAULT_LOGNAME, context.getKey());
		 logger.error(message,values);
		 ThreadContext.remove(DEFAULT_LOGNAME);
	}
	
	/**
	 * 
	 * Registra los mensajes a un log level Error.
	 * 
	 * @param String message Contenido del mensaje a registrar, con los placeholders indicados
	 * 
	 * @param Object values vars args
	 *
	 * 
	 **/
	public void doError(String message,Object... values  ){
		this.doError(this.context,message, values);
	}
	
	/**
	 * 
	 * Registra los mensajes a un log level Debug.
	 * 
	 * @param ContextLog context Contexto que contiene el nombre con el que se logeara.
	 * 
	 * @param String message Contenido del mensaje a registrar, con los placeholders indicados.
	 * 
	 * @param Object values vars args.
	 * 
	 **/
	public void doDebug(ContextLog context,String message,Object... values){
		 ThreadContext.put(DEFAULT_LOGNAME, context.getKey());
		 logger.debug(message,values);
		 ThreadContext.remove(DEFAULT_LOGNAME);
	}
	
	public void doDebug(String message,Object... values  ){
		this.doDebug(this.context,message, values);
	}
	
	/**
	 * 
	 * Registra los mensajes a un log level Warning.
	 * 
	 * @param ContextLog context Contexto que contiene el nombre con el que se logeara.
	 * 
	 * @param String message Contenido del mensaje a registrar, con los placeholders indicados.
	 * 
	 * @param Object values vars args.
	 * 
	 * 
	 **/
	public void doWarn(ContextLog context,String message,Object... values){
		 ThreadContext.put(DEFAULT_LOGNAME, context.getKey());
		 logger.warn(message,values);
		 ThreadContext.remove(DEFAULT_LOGNAME);
	}
	
	/**
	 * 
	 * Registra los mensajes a un log level Warning.
	 * 
	 * @param String message Contenido del mensaje a registrar, con los placeholders indicados.
	 * 
	 * @param Object values vars args.
	 * 
	 **/
	public void doWarn(String message,Object... values  ){
		this.doWarn(this.context,message, values);
	}


}
