package com.cgts.services.tool.properties;

/**
 * 
 * Clase que contiene los datos principales de 
 * un Agente ( nombre, tiempo y cantidad de consumidores).
 * 
 * @author MG Grupo
 * 	 
 **/
public class PropertiesElement {
	private String name = "";
	private Long time;
	private Integer consumers = 0;
	
	/**
	 * 
	 * Constructor base de la clase.
	 * 
	 **/
	public PropertiesElement() {}
	
	public PropertiesElement(String n, Long t, Integer c){
		this.name = n;
		this.time = t;
		this.consumers = c;		
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

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public Integer getConsumers() {
		return consumers;
	}

	public void setConsumers(Integer consumers) {
		this.consumers = consumers;
	}
}
