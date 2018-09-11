package com.cgts.services.tool.hotreload;

import com.cgts.services.tool.LoggerHandler;
import org.apache.commons.vfs2.*;
import org.apache.commons.vfs2.impl.DefaultFileMonitor;

import java.io.File;

/**
 * 
 * Manejador encargada de verificar si determinados archivos han sido
 * editados, eliminados o agregados a un sistema de archivos apuntado por una ruta 
 * determinada.
 *
 * @see <a href="https://commons.apache.org/proper/commons-vfs/apidocs/index.html">Apache VFS Commons</a> * 
 *
 * @author MG Grupo
 * 
 **/
public abstract class HotReloadingHandler implements FileListener{
	protected DefaultFileMonitor fileMonitor = null;
	protected long timeTowait = 1000;
	protected File configFile = null;
	protected LoggerHandler log = new LoggerHandler(HotReloadingHandler.class);
	
	/**
	 * 
	 * Conjunto de Getters y Setters.
	 * 
	 **/
	public DefaultFileMonitor getFileMonitor() {
		return fileMonitor;
	}

	public void setFileMonitor(DefaultFileMonitor fileMonitor) {
		this.fileMonitor = fileMonitor;
	}

	public long getTimeTowait() {
		return timeTowait;
	}

	public void setTimeTowait(long timeTowait) {
		this.timeTowait = timeTowait;
	}

	public File getConfigFile() {
		return configFile;
	}

	public void setConfigFile(File configFile) {
		this.configFile = configFile;
	}

	/**
	 * 
	 * Constructor base de la clase.
	 * 
	 * @throws FileSystemException
	 * 
	 **/
	public HotReloadingHandler(File file, long time) throws FileSystemException{
		configFile = file;
		timeTowait = time;
	}
	
	public HotReloadingHandler(File file) throws FileSystemException{
		configFile = file;
	}
	 
	/**
	 * 
	 * Funcion que da comienzo al HotReloadingHandler.
	 * 
	 * @throws FileSystemException
	 * 
	 * 	 
	 **/
	public void startListening(HotReloadingHandler fileMonitoring) throws FileSystemException, InterruptedException {
		FileSystemManager fsManager = VFS.getManager();
		FileObject fileObject = fsManager.toFileObject(configFile);
		if (fileMonitor == null) {
			fileMonitor = new DefaultFileMonitor(this);
			fileMonitor.setDelay(timeTowait);
			fileMonitor.addFile(fileObject);
			fileMonitor.start();
		}
	}

	/**
	 * 
	 * Funcion que actualiza los cambios en el archivo sci.properties.
	 * 
	 * 
	 **/
	protected abstract void onFileChangedTasks();
	
	@Override
	public void fileChanged(FileChangeEvent event) throws Exception {
		onFileChangedTasks();
	}

	/**
	 * 
	 * Funcion que crea los detalles en el archivo sci.properties.
	 * 
	 * 
	 **/
	protected abstract void onFileCreatedTasks();
	
	@Override
	public void fileCreated(FileChangeEvent event) throws Exception {
		onFileCreatedTasks();
	}

	/**
	 * 
	 * Funcion que actualiza los detalles borrados en el archivo sci.properties.
	 * 
	 * 
	 **/	
	protected abstract void onFileDeletedTasks();
	 
	@Override
	public void fileDeleted(FileChangeEvent event) throws Exception {
		onFileDeletedTasks();
	}
}
