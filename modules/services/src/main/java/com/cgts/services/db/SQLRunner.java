package com.cgts.services.db;

import com.cgts.services.tool.ObjectUtil;
import com.cgts.services.tool.properties.PropertiesLoader;
import oracle.jdbc.pool.OracleDataSource;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * Clase que se encarga de procesar los querys a la BD.
 * 
 * @author MG Grupo
 * 
 **/
public class SQLRunner {

	private Connection conn = null;
	private String dbVersion = null;
	private int fetchSize = 0;


	/**
	 * 
	 * Conjunto de Getters y Setters.
	 * 

	 * 
	 **/
	public void setFetchSize(int size) {
		this.fetchSize = size;
	}
	
	public String getInfo() {
		return this.dbVersion;
	}
	
	/**
	 * 
	 * Constructor base de la clase.
	 * 
	 * @param Connection conn conexion a BD.
	 * 
	 * 	 
	 **/
	public SQLRunner(Connection conn) {
		this.conn = conn;
		DatabaseMetaData md;
		try {

			md = this.conn.getMetaData();

			this.dbVersion = md.getDatabaseProductName() + " "
					+ md.getDatabaseProductVersion() + " ("
					+ md.getDriverName() + " " + md.getDriverVersion() + ")";

		} catch (SQLException e) {}
	}

	/**
	 * 
	 * Ejecuta instruccion INSERT,DELETE,UPDATE.
	 * 
	 * @param String sql
	 * 
	 * @return retorna el numero de registros afectados.
	 * 
	 * @throws Throwable
	 *
	 * 
	 **/
	public int exec(String sql) throws Throwable {

		Statement s = null;
		int rows = 0;

		try {

			s = this.conn.createStatement();
			rows = s.executeUpdate(sql);
			return rows;
		}

		catch (Throwable e) {
			String err = e.getMessage() + " [" + sql + "]";
			throw new Throwable(err, e);
		}

		finally {
			if (s != null)
				s.close();
		}

	}

	/**
	 * 
	 * Ejecuta instrucciones sql con datos complejos como Objects y byte[].
	 * 
	 * @param List<AttributeSql> attr
	 * 
	 * @param String sql
	 *            : este parametro debe contener ? donde si fijaria los datos
	 *            complejos
	 * 
	 * @throws Throwable
	 *
	 * 
	 **/
	public void execBin(List<AttributeSql> attr, String sql) throws Throwable {

		PreparedStatement p = null;
		InputStream data = null;
		try {
			String sqlPrepared = this.prepareSQL(attr, sql);
			p = this.conn.prepareStatement(sqlPrepared);
			int posicion = 1;
			for (AttributeSql attributeSql : attr) {
				if (attributeSql.getType() == Types.JAVA_OBJECT) {
					// Esto es para objetos simples serializables
					p.setObject(posicion, attributeSql.getValue());
					posicion++;
				} else if (attributeSql.getType() == Types.BINARY) {
					// Esto es para objetos complejos que necesitan ser serializados
					byte[] obj = ObjectUtil.serializeObject(attributeSql
							.getValue());
					data = new ByteArrayInputStream(obj);
					p.setBinaryStream(posicion, data);
					posicion++;
				} else if (attributeSql.getType() == Types.BLOB) {
					// Esto es para archivos, imagenes, etc
					byte[] obj = (byte[])attributeSql.getValue();
					data = new ByteArrayInputStream(obj);
					p.setBinaryStream(posicion, data);
					posicion++;
				}
			}

			p.execute();
		} catch (Throwable e) {
			e.printStackTrace();
			throw new Throwable("Error cargando el archivo en base de datos", e);

		} finally {
			if (p != null)
				p.close();
			if (data != null)
				data.close();

		}

	}

	/**
	 * 
	 * Conjunto de funciones que se encargan de ejecutar la consulta sql a la BD.
	 * 
	 * @throws Throwable
	 *
	 * 
	 **/
	public Recordset runSQL(String sql) throws Throwable {
		return runSQL(sql, 0);
	}

	/**
	 * 
	 * Conjunto de funciones que se encargan de ejecutar la consulta sql a la BD.
	 * 
	 * @throws Throwable
	 *
	 * 
	 **/
	public Recordset runSQL(String sql, int limit) throws Throwable {

		ResultSet rs = null;
		Statement stmt = null;
		try {

			stmt = this.conn.createStatement();
			stmt.setFetchSize(fetchSize);

			if (limit > 0)
				stmt.setMaxRows(limit);

			rs = stmt.executeQuery(sql);
			Recordset recs = new Recordset(rs);

			return recs;

		} catch (Throwable e) {
			String err = e.getMessage() + " [" + sql + "]";
			throw new Throwable(err, e);
		} finally {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
		}

	}

	/**
	 * 
	 * Conjunto de funciones que se encargan de ejecutar el listado de 
	 * consultas sql a la BD.
	 * 
	 * @throws Throwable
	 *
	 * 
	 **/
	public Recordset runFunctionDb(List<AttributeSql> attr, String funcSign)
			throws Throwable {
		return this.runFunctionDb(attr, funcSign, true);
	}

	/**
	 * 
	 * Conjunto de funciones que se encargan de ejecutar el listado de 
	 * consultas sql a la BD.
	 * 
	 * @throws Throwable
	 *
	 **/
	public Recordset runFunctionDb(List<AttributeSql> attr, String funcSign,
			boolean usePosition) throws Throwable {
		ResultSet result = null;
		CallableStatement upperProc = null;

		try {

			upperProc = this.conn.prepareCall(buildSQlFuction(funcSign));
			int pos = 0;
			for (Iterator<AttributeSql> iterator = attr.iterator(); iterator.hasNext();){

				if (usePosition)
					pos++;
				AttributeSql var = iterator.next();
				this.setTypePLSQL(upperProc, var, pos);
			}

			result = upperProc.executeQuery();
			Recordset recs = new Recordset(result);

			return recs;
		} catch (SQLException e) {
			throw new Throwable(e);
		} finally {
			if (result != null)
				result.close();
			if (upperProc != null)
				upperProc.close();
		}

	}
	
	/**
	 * 
	 * Funcion que devuelve el tipo de dato.
	 * 
	 * @throws Throwable
	 *
	 * 
	 **/
	private void setTypePLSQL(CallableStatement cstm, AttributeSql attr,
			int posicion) throws Throwable {

		switch (attr.getType()) {
		case Types.DECIMAL:
		case Types.FLOAT:
		case Types.NUMERIC:
			if (posicion > 0)
				cstm.setDouble(posicion, (Double) attr.getValue());
			else
				cstm.setDouble(attr.getName(), (Double) attr.getValue());
			break;

		case Types.BIGINT:
		case Types.SMALLINT:
		case Types.TINYINT:
			if (posicion > 0)
				cstm.setInt(posicion, (Integer) attr.getValue());
			else
				cstm.setInt(attr.getName(), (Integer) attr.getValue());
			break;

		case Types.LONGVARCHAR:
		case Types.VARCHAR:
			if (posicion > 0)
				cstm.setString(posicion, (String) attr.getValue());
			else
				cstm.setString(attr.getName(), (String) attr.getValue());
			break;
		}
	}
	
	/**
	 * 
	 * Funcion que cambia el parametro de autocommit a falso.
	 * 
	 * @throws Throwable
	 *
	 * 
	 **/
	public void beginTrans() throws Throwable {
		try {
			this.conn.setAutoCommit(false);
		} catch (SQLException e) {
			throw new Throwable(e);
		}
	}

	/**
	 * 
	 * Funcion que realiza commit de las 
	 * distintas instrucciones que se realizaron previamente.
	 * 
	 * @throws Throwable
	 *
	 * 
	 **/
	public void commit() throws Throwable {
		try {
			this.conn.commit();
		} catch (SQLException e) {
			throw new Throwable(e);
		}
	}
	
	/**
	 * 
	 * Funcion que realiza rollback de las 
	 * distintas instrucciones que se realizaron previamente.
	 * 
	 * @throws Throwable
	 *
	 * 
	 **/
	public void rollBack() throws Throwable {
		try {
			this.conn.rollback();
		} catch (SQLException e) {
			throw new Throwable(e);
		}
	}
	
	/**
	 * 
	 * Funcion que devuelve el proximo valor de la secuencia.
	 * 
	 * @throws Throwable
	 *
	 * 
	 **/
	public int nextValSequence(String nameSequence) throws Throwable{
		ResultSet rs = null;
		Statement stmt = null;
			
		try {
			List<AttributeSql> attr = new ArrayList<AttributeSql>();
			attr.add(new AttributeSql("SEQUENCE",nameSequence));
			String sqlPrepared  = this.prepareSQL(attr, PropertiesLoader.getProperty("sql_sequence_format"));
			stmt = this.conn.createStatement();
			rs = stmt.executeQuery(sqlPrepared);
			
			rs.next();
			return rs.getInt(1);

		} catch (Throwable e) {
			throw  e;
		} finally {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
		}
	}

	/**
	 * 
	 * Funcion que prepare el sql que sera ejecutado.
	 *
	 * 
	 **/
	public String prepareSQL(List<AttributeSql> prm, String sql) {

		StringBuilder sqlPrepared = new StringBuilder(sql);
		int init;
		String prefix = "prm:";

		for (Iterator<AttributeSql> iterator = prm.iterator(); iterator
				.hasNext();) {
			AttributeSql var = iterator.next();
			String namePrm = prefix + var.getName();
			while (true) {
				init = sqlPrepared.indexOf(namePrm);
				if (init > 0) {
					Object value = var.getValue();
					if (value != null) {
						sqlPrepared.replace(init, init + namePrm.length(),
								value.toString());
					} else {
						sqlPrepared.replace(init, init + namePrm.length(),"null");
					}
				} else	break;
			}
		}

		return sqlPrepared.toString();
	}

	/**
	 * 
	 * Funcion que cierra la conexion a BD.
	 * 
	 * @throws Throwable
	 *
	 * 
	 **/
	public void closeConnection() throws Throwable {
		if (this.conn != null){
			this.conn.setAutoCommit(true);
			this.conn.close();
		}
	}
	
	/**
	 * 
	 * Funcion que construye el sql para la llamada de funciones en la BD.
	 *
	 * 
	 **/
	private String buildSQlFuction(String functionName){
		return "{ call ".concat(functionName.concat(" }"));
		
	}
	
	/**
	 * 
	 * Funcion que revisa si la conexion es valida.
	 *
	 * 
	 **/
	public Boolean isAlive(){
		try {
			return this.conn.isValid(Integer.parseInt(PropertiesLoader.getProperty("max_wait_time_valid_db_connection")));
		} catch (SQLException e) {
			return false;
		}
	}
	
	public static void main(String[] args) throws SQLException  {

		String user="TVT";
		String pwd="TVTd738";
		String url="jdbc:oracle:thin:@192.168.7.196:1521:TVTD";

		OracleDataSource ds = new OracleDataSource();
		ds.setUser(user);
		ds.setPassword(pwd);
		ds.setURL(url);
		ds.setImplicitCachingEnabled(true);
		ds.setFastConnectionFailoverEnabled(true);

		Connection conn =null;
		try {
			conn = ds.getConnection();
			SQLRunner db = new SQLRunner(conn);
			System.out.println("=============================");
			System.out.println("Info DB:" + db.getInfo());
			System.out.println("=============================");
			
		} catch (Throwable e) {
			e.printStackTrace();
		}finally{
			try {
				if(conn!=null)	conn.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
		}
	}
}
