package com.cgts.services.db;

import java.io.Serializable;
import java.sql.Types;


public class RecordsetField  implements Serializable
{

	private static final long serialVersionUID = 1L;
	private String nombre = null;
	private String sqlTypeName = null;
	private int sqlType = 0;
	
	/**
	 * 
	 * Quick way to build an object of this class.
	 * 
	 * @param String name Field Name
	 * 
	 * @param String typeName Native Type name
	 * 
	 * @param int type JDBC Data Type
	 * 
	 * @author hisoft
	 * 
	 **/
	
	public RecordsetField() {}
	
	public RecordsetField(String name, String typeName, int type)
	{
		switch (type) {
			case Types.DECIMAL:
			case Types.FLOAT:
			case Types.NUMERIC:
				type = Types.DOUBLE;
				break;
	
			case Types.BIGINT:
			case Types.SMALLINT:
			case Types.TINYINT:
				type = Types.INTEGER;
				break;
		} 
		
		nombre = name;
		sqlTypeName = typeName;
		sqlType = type;
	}

	/**
	 * 
	 * Getters and Setters.
	 * 
	 * @author hisoft
	 * 
	 **/
	public String getName()
	{
		return nombre;
	}

	public String getSqlTypeName()
	{
		return sqlTypeName;
	}

	public int getType()
	{
		return sqlType;
	}
	
}
