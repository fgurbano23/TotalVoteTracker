package com.cgts.services.tool.hotreload;

import com.cgts.services.tool.LoggerHandler.ContextLog;
import com.cgts.services.tool.properties.PropertiesLoader;
import org.apache.commons.vfs2.FileSystemException;

import java.io.File;
import java.io.FileInputStream;

/**
 * 
 * Clase que maneja los cambios en el archivo tvt.properties.
 * 
 * @author MG Grupo
 * 
 **/
public class PropertiesReloader extends HotReloadingHandler{

	public PropertiesReloader(File file) throws FileSystemException {
		super(file);
	}

	/**
	 * 
	 * Funcion que actualiza los cambios en el archivo tvt.properties.
	 * 

	 * 
	 **/
	@Override
	protected void onFileChangedTasks() {
		try {
			PropertiesLoader.loadFile(new FileInputStream(configFile));
			PropertiesLoader.reloadArrays();
			System.out.println("RELOAD ON");
			this.log.doDebug(ContextLog.INFO, "PropertiesReloader.onFileChangedTasks - Reloaded configuration file: {}", this.configFile.getAbsolutePath());
		} catch (Throwable e) {
			log.doError(ContextLog.ERROR_BL,"PropertiesReloader.onFileChangedTasks - Error while reloading properties file: {}", this.configFile.getAbsolutePath());
		}
	}

	/**
	 * 
	 * Funcion que actualiza los detalles borrados en el archivo sci.properties.
	 * 
	 * @author hisoft
	 * 
	 **/
	@Override
	protected void onFileDeletedTasks() {}

	/**
	 * 
	 * Funcion que crea los detalles en el archivo sci.properties.
	 * 
	 * @author hisoft
	 * 
	 **/
	@Override
	protected void onFileCreatedTasks() {}
	
}
