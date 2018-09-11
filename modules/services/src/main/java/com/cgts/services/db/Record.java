package com.cgts.services.db;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;


/**
 * 
 * Clase para representar un registro de la Base de Datos.
 * 
 * @author MG Grupo
 **/
public class Record implements Serializable {

	private static final long serialVersionUID = 1L;
	HashMap<String, Object> record = new HashMap<String, Object>();
	Recordset children = null;

	public Record(HashMap<String, Object> values) {
		record = values;
	}

	
	/**
	 * 
	 * Obtiene el valor en de un campo dentro de un record.
	 * 
	 * @param 'String fieldName Nombre del campo
	 * 
	 * @return Valor del campo
	 * 
	 * @throws Exception
	 * 
	 * 
	 **/
	public Object getFieldValue(String fieldName) throws Exception {
		
		if (!record.containsKey(fieldName)) {
			StringBuilder colNames = new StringBuilder();
			Set<String> s = record.keySet();
			for (Iterator<String> iterator = s.iterator(); iterator.hasNext();) {
				colNames.append(iterator.next() + ";");
			}

			String args[] = { fieldName, colNames.toString() };
			String msg = "FIELD_NOT_FOUND";
			msg = MessageFormat.format(msg, (Object[]) args);
			
			throw new Exception(msg);
		}
		return record.get(fieldName);

	}


	/**
	 * 
	 * Actualiza el valor del campo. En caso de no existir se guarda.
	 * 
	 * @param String fieldName Nombre del campo
	 * 
	 * @param Object value Valor del campo
	 * 
	 * @throws Exception
	 * 
	 * 
	 **/
	public void setValue(String fieldName, Object value)
			throws Exception {

		if (!record.containsKey(fieldName)) {
			StringBuilder colNames = new StringBuilder();
			Set<String> s = record.keySet();
			for (Iterator<String> iterator = s.iterator(); iterator.hasNext();) {
				colNames.append(iterator.next() + ";");
			}

			String args[] = { fieldName, colNames.toString() };
			String msg = "FIELD_NOT_FOUND";
			msg = MessageFormat.format(msg, (Object[]) args);
			
			throw new Exception(msg);
		}
		record.put(fieldName, value);

	}

	/**
	 * 
	 * Asigna el valor de un registro como hijo al recorset.
	 * 
	 * @param Recordset rs
	 *
	 *
	 **/
	public void setChildren(Recordset rs) {
		children = rs;
	}

	
	/**
	 * 
	 * Obtiene el recorset (hijo) asociado.
	 * 
	 * 
	 **/
	public Recordset getChildren() {
		return children;
	}

}
