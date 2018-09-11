package com.cgts.services.db;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

@SuppressWarnings("serial")
public class GenericDataSource implements DataSource, Serializable {

	/**
	 * Usuario de la base de datos
	 */
	private String username = "";

	/**
	 * Contrasena del usuario de la base de datos
	 */
	private String password = "";

	/**
	 * Nombre de la Clase que gestiona la conexion a la base de datos
	 */
	private String driverClassName = "";

	/**
	 * Tiempo maximo de espera para recibir respuesta del servidor de base de
	 * datos por defecto 5 segundos
	 */
	private long maxWait = 5000;

	/**
	 * Direccion del servidor de base de datos
	 */
	private String url = "";

	/**
	 * Constructora de la Clase
	 * 
	 * @param user
	 *            Usuario de la base de datos
	 * @param passwd
	 *            Contrasena del usuario de la base de datos
	 * @param url
	 *            Direccion de la base de datos
	 * @param driver
	 *            Nombre de la clase a usar para la conexion a la base de datos
	 */
	public GenericDataSource(String user, String passwd, String url,
			String driver) {
		this.username = user;
		this.password = passwd;
		this.url = url;
		this.driverClassName = driver;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.sql.DataSource#getConnection()
	 */
	public Connection getConnection() throws SQLException {
		Connection conex = null;

		try {
			Class.forName(this.driverClassName);
		} catch (ClassNotFoundException e) {
			// throw new Throwable("JDBC driver no encontrado:" +
			// e.getMessage());
			throw new SQLException("JDBC driver no encontrado:"
					+ e.getMessage());
		}
		try {
			conex = java.sql.DriverManager.getConnection(this.url,
					this.username, this.password);
		} catch (SQLException e) {
			// throw new Throwable("No se puede conectar con el driver:" +
			// e.getMessage());
			throw new SQLException("No se puede conectar con el driver:"
					+ e.getMessage());

		}

		return conex;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.sql.DataSource#getConnection(java.lang.String,
	 * java.lang.String)
	 */
	public Connection getConnection(String username, String password)
			throws SQLException {
		Connection conex = null;

		try {
			Class.forName(this.driverClassName);
		} catch (ClassNotFoundException e) {
			// throw new Throwable("JDBC driver no encontrado:" +
			// e.getMessage());
			throw new SQLException("JDBC driver no encontrado:"
					+ e.getMessage());
		}
		try {
			conex = java.sql.DriverManager.getConnection(this.url, username,
					password);
		} catch (SQLException e) {
			// throw new Throwable("No se puede conectar con el driver:" +
			// e.getMessage());
			throw new SQLException("No se puede conectar con el driver:"
					+ e.getMessage());

		}

		return conex;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.sql.DataSource#getLogWriter()
	 */
	public PrintWriter getLogWriter() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.sql.DataSource#setLogWriter(java.io.PrintWriter)
	 */
	public void setLogWriter(PrintWriter out) throws SQLException {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.sql.DataSource#setLoginTimeout(int)
	 */
	public void setLoginTimeout(int seconds) throws SQLException {
		this.maxWait = seconds * 1000;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.sql.DataSource#getLoginTimeout()
	 */
	public int getLoginTimeout() throws SQLException {
		int seg = (int) (this.maxWait / 1000);
		return seg;
	}

	/**
	 * Fija el controlador para la conexion a la base de datos
	 * 
	 * @param driverClassName
	 *            Nombre de la clase manejadora de la conexion
	 */
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	/**
	 * Fija la direccion del servidor de base de datos
	 * 
	 * @param url
	 *            Direccion del servidor de base de datos.
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

}
