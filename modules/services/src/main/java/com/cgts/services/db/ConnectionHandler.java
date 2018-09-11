package com.cgts.services.db;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 
 *  Implemetacion de una clase para las conexiones a base de datos
 *  basado en el datasource proveniente de configuraciones propias
 *  de la aplicacion.
 *  
 *  @author MG Grupo
 *  
 **/

public class ConnectionHandler {
	
	private static String connectionString = "";
	private static String tomcatConnectionPrefix = "java:comp/env/";

	/**
	 * 
	 * Obtiene la fuente de datos.
	 * 
	 * @param 'String' connection_prefix Nombre del objeto para hacer el lookup
	 * 
	 * @return Objeto de tipo {@link  DataSource} representando la conexion fisica a la fuente de datos
	 * 
	 * @throws NamingException
	 * 
	 * 
	 **/
	private static DataSource getDataSource(String connection_prefix) throws NamingException{
		Context initCtx = new InitialContext();
		Context envCtx = (Context) initCtx.lookup(tomcatConnectionPrefix);
		DataSource ds = (DataSource)envCtx.lookup(connectionString);
		return ds;
	}
	
	/**
	 * 
	 * Obtiene la conexion del datasource indicado.
	 * 
	 * @param 'DataSource' ds DataSource
	 * 
	 * @return Conexion (sesion) del datasource
	 * 
	 * @throws SQLException
	 * 
	 * 
	 **/
	private static Connection getConnection(DataSource ds) throws SQLException{
		return ds.getConnection();
	}
	
	public Connection getConnection(String connectionPrefix) throws Throwable{
		Context initCtx = new InitialContext();
		Context envCtx = (Context) initCtx.lookup(ConnectionHandler.tomcatConnectionPrefix);
		DataSource ds = (DataSource)envCtx.lookup(connectionPrefix);
		return ds.getConnection();
	}
	
	/**
	 * 
	 * Obtiene la conexion. 
	 * Alias para la obtencion del DS y conexion.
	 * 
	 * @param 'String' connection_prefix
	 * 
	 * @return  Conexion (Sesion) del data source
	 * 
	 * @throws SQLException
	 * 
	 * @throws NamingException
	 * 
	 * 
	 **/
	public static synchronized  Connection open(String connectionPrefix) throws SQLException, NamingException{
		connectionString = !connectionString.isEmpty()?connectionString: connectionPrefix;
		return getConnection(getDataSource(connectionString));
	}
		
}

