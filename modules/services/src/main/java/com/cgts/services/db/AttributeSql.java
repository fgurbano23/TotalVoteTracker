package com.cgts.services.db;

import java.sql.Types;

/**
 * 
 * Representa los parametros usados en las instrucciones SQL.
 * 
 * @author MG Grupo
 *
 **/
public class AttributeSql {
	private String name;
	private Object value;
	private int type;
	
	/**
	 * 
	 * Constructor base de la clase.
	 * 
	 * 	 
	 **/
	public AttributeSql(String name, String value){
		this.name = name;
		this.value = value;
		this.type=Types.VARCHAR;
	}
	
	public AttributeSql(String name, Object value, int type){
		this.name = name;
		this.value = value;
		this.type= type;
	}

	/**
	 * 
	 * Conjunto de Getters y Setters.
	 * 
	 * 
	 **/
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}	
}
