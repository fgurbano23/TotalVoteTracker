package com.cgts.services.db;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class Recordset implements Serializable {

	private static final long serialVersionUID = 1L;
	private HashMap<String, RecordsetField> campos = new HashMap<String, RecordsetField>();
	private ArrayList<Record> data = new ArrayList<Record>();
	private int recordNumber = -1;

	/**
	 * 
	 * Feed the recordset metadata (column structure).
	 * 
	 * @param HashMap<String, RecordsetField> fields
	 *            HashMap containing the recordset field objects
	 *
	 * 
	 **/
	protected void setFields(HashMap<String, RecordsetField> fields) {
		campos = fields;
	}

	/**
	 * 
	 * Feed the recordset data.
	 * 
	 * @param ArrayList<Record> data
	 *            ArrayList containing record objects
	 *
	 * 
	 **/
	protected void setData(ArrayList<Record> data) {
		this.data = data;
	}

	/**
	 * 
	 * Returns the current record position (0...N-1).
	 * 
	 *  
	 **/
	public int getRecordNumber() {
		return recordNumber;
	}

	/**
	 * 
	 * Returns the number of records in recordset.
	 * 
	 *  
	 **/
	public int getRecordCount() {
		return data.size();
	}

	/**
	 * 
	 * Returns number of fields in recordset.
	 * 
	 *
	 **/
	public int getFieldCount() {
		return campos.size();
	}

	/**
	 * 
	 * Returns HashMap containing RecordsetField objects representing the
	 * Recordset fields.
	 * 
	 *
	 **/
	public HashMap<String, RecordsetField> getFields() {
		return campos;
	}

	/**
	 * 
	 * Returns ArrayList containing the recordset data (the records).
	 * 
	 * 
	 **/
	public ArrayList<Record> getData() {
		return data;
	}

	/**
	 * 
	 * Append a field to the recordset structure.<br>
	 * It is used when creating a Recordset from a JDBC query.
	 * 
	 * @param String fieldName
	 *            Field Name
	 * @param String nativeSqlType
	 *            SQL native data type name
	 * @param int type
	 *            JDBC data type (java.sql.Types)
	 *            
	 * 
	 **/
	private void append(String fieldName, String nativeSqlType, int type) {
		RecordsetField f = new RecordsetField(fieldName, nativeSqlType, type);
		campos.put(fieldName, f);
	}

	/**
	 * 
	 * Append a field to the recordset structure.<br>
	 * It is used when manufacturing a Recordset from code.
	 * 
	 * @param String fieldName
	 *            Field Name
	 * @param int type
	 *            JDBC data type (java.sql.Types) - only INTEGER, LONG, VARCHAR,
	 *            DATE, TIMESTAMP or DOUBLE are supported
	 *            
	 * 
	 **/
	public void append(String fieldName, int type) throws Exception {
		String sqlTypeName = null;

		switch (type) {
		case Types.INTEGER:
			sqlTypeName = "INTEGER";
			break;

		case Types.BIGINT:
			sqlTypeName = "LONG";
			break;

		case Types.VARCHAR:
		case Types.CHAR:
			sqlTypeName = "VARCHAR";
			break;

		case Types.DATE:
			sqlTypeName = "DATE";
			break;

		case Types.TIMESTAMP:
			sqlTypeName = "TIMESTAMP";
			break;

		case Types.DOUBLE:
			sqlTypeName = "DOUBLE";
			break;

		}

		if (sqlTypeName == null) {
			String args[] = { String.valueOf(type) };
			String msg = "INVALID_DATATYPE";
			msg = MessageFormat.format(msg, (Object[]) args);
			
			
			throw new Exception(msg);
		}

		append(fieldName, sqlTypeName, type);

	}

	/**
	 * 
	 * Add a record to the recordset and set record number position to the new
	 * inserted record.
	 *
	 * 
	 **/
	public void addNew() {
		HashMap<String, Object> values = new HashMap<String, Object>();

		Iterator<String> i = campos.keySet().iterator();
		while (i.hasNext()) {
			String f = i.next();
			values.put(f, null);
		}

		data.add(new Record(values));
		recordNumber++;

	}

	/**
	 * 
	 * Set record position inside recordset
	 * 
	 * @param int recNum
	 *            Record Number (0...getRecordCount()-1)
	 *            
	 * @throws Throwable
	 * 
	 * 
	 **/
	public void setRecordNumber(int recNum) throws Exception {
		checkRecordPosition(recNum);
		recordNumber = recNum;

	}

	/**
	 * 
	 * Set field value for current record (determined by getRecordNumber()).
	 * 
	 * @param String fieldName
	 *            Field Name
	 *            
	 * @param Object value
	 *            Field Value (Date, String, int, double, null)
	 * 
	 * @throws Throwable
	 *
	 * 
	 **/
	public void setValue(String fieldName, Object value) throws Exception {

		checkRecordPosition();

		RecordsetField f = null;
		try {
			f = getField(fieldName);
		} catch (Throwable e) {
			
			throw new Exception(e.getMessage());
		}

		if (value != null) {
			switch (f.getType()) {

			case Types.DATE:
				if (!(value instanceof java.util.Date))
					throw new Exception("Invalid data type of field: "
							+ fieldName
							+ "; passed value must be a DATE object.");
				break;

			case Types.INTEGER:
				if (!(value instanceof Integer)
						&& !(value instanceof Long))
					throw new Exception("Invalid data type of field: "
							+ fieldName
							+ "; passed value must be an INTEGER object.");
				break;

			case Types.DOUBLE:
				if (!(value instanceof Double)
						&& !(value instanceof java.math.BigDecimal))
					throw new Exception("Invalid data type of field: "
							+ fieldName
							+ "; passed value must be an DOUBLE object.");
				break;
			}
		}

		Record rec = data.get(recordNumber);
		rec.setValue(fieldName, value);

	}

	/**
	 *
	 * Return field value given a field name.
	 *
	 * @param String fieldName
	 *            Field Name. May be reserved field names: _rowIndex (0...N-1)
	 *            or _rowNumber (1...N)
	 *
	 * @throws Throwable
	 *
	 *
	 **/
	public Object getValue(String fieldName) throws Throwable {
		checkRecordPosition();
		if (fieldName.equals("_rowIndex")) {
			return Integer.valueOf(recordNumber);
		} else if (fieldName.equals("_rowNumber")) {
			return Integer.valueOf(recordNumber + 1);
		} else {

			Record rec = (Record) data.get(recordNumber);
			Object value = rec.getFieldValue(fieldName);

			if (value != null) {
				RecordsetField f = campos.get(fieldName);
				if (f.getType() == Types.CLOB) {
					java.sql.Clob clob = (java.sql.Clob) value;
					value = clob.getSubString((long) 1, (int) clob.length());
				}
			}
			return value;
		}

	}

	/**
	 *
	 * Fill recordset with resultset data and metadata. It is the responsability
	 * of the caller of this method to close the resultset and other jdbc
	 * objects involved. The resultset must be positioned before the first
	 * record.
	 *
	 * @param java.sql.ResultSet rs
	 *            Resultset
	 *
	 * @throws Throwable
	 *
	 *
	 **/
	private void loadRecords(ResultSet rs) throws Throwable {

		ResultSetMetaData md = rs.getMetaData();
		int cols = md.getColumnCount();
		for (int i = 1; i <= cols; i++) {
			append(md.getColumnLabel(i).toLowerCase(), md.getColumnTypeName(i),
					md.getColumnType(i));
		}

		while (rs.next()) {
			HashMap<String, Object> flds = new HashMap<String, Object>(cols);
			for (int i = 1; i <= cols; i++) {
				if(md.getColumnType(i)==Types.BLOB){
					Blob blob = rs.getBlob(i);
					byte[] bytesBlob = blob.getBytes(1L, (int)blob.length());
					flds.put(md.getColumnLabel(i).toLowerCase(), bytesBlob);
				}else{
					flds.put(md.getColumnLabel(i).toLowerCase(), rs.getObject(i));
				}
			}
			data.add(new Record(flds));
		}

	}

	/**
	 * 
	 * Create a recordset given a resultset. It is the responsability of the
	 * caller of this method to close the resultset and other jdbc objects
	 * involved.
	 * 
	 * @param ResultSet rs
	 *            ResultSet positiones before the first record
	 *            
	 * @throws Throwable
	 * 
	 *
	 **/
	public Recordset(ResultSet rs) throws Throwable {
		loadRecords(rs);
	}

	public Recordset(){}

	/**
	 * 
	 * Move pointer to next record. Returns true if not EOF.
	 * 
	 *
	 **/
	public boolean next() {
		if (recordNumber < (data.size() - 1)) {
			recordNumber++;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * Set cursor position before first record, it is like a "rewind" command.
	 * 
	 *
	 **/
	public void top() {
		recordNumber = -1;
	}

	/**
	 * 
	 * Set cursor position on first record.
	 * 
	 * @throws Throwable
	 * 
	 * 
	 **/
	public void first() throws Throwable {
		setRecordNumber(0);
	}

	/**
	 * 
	 * Set cursor position on last record.
	 * 
	 * @throws Throwable
	 * 
	 *
	 **/
	public void last() throws Throwable {
		setRecordNumber(data.size() - 1);
	}

	/**
	 * Delete record (from memory).
	 * 
	 * @param int recNum
	 *            Record Number (0..N-1)
	 *            
	 * 
	 **/
	public void delete(int recNum) throws Throwable {
		checkRecordPosition(recNum);
		data.remove(recNum);
		recordNumber--;
	}

	/**
	 * 
	 * Return a Recordset field object describing its properties.
	 * 
	 * @param String fieldName
	 *            Field name to locate the field object
	 *            
	 * @return Reference to Recordset field
	 * 
	 * @throws Throwable
	 *             if fieldName does not exist in Recordset metadata
	 *             
	 *              
	 **/
	public RecordsetField getField(String fieldName) throws Throwable {
		if (campos.containsKey(fieldName))
			return (RecordsetField) campos.get(fieldName);
		else
			throw new Throwable("Field not found:" + fieldName);
	}

	/**
	 * 
	 * Wrapper method for getValue() - avoids casting the data type.
	 * 
	 * @param String colName
	 *            Column name to retrieve its value from the current record
	 *            
	 * @return The column value in its native data type
	 * 
	 * @throws Throwable
	 * 
	 *
	 **/
	public String getString(String colName) throws Throwable {
		Object obj = getValue(colName);
		if (obj != null)
			return String.valueOf(obj);
		else
			return null;
	}

	public byte[] getBytes(String colName) throws Throwable {
		Object obj = getValue(colName);
		if (obj != null)
			return (byte[]) obj;
		else
			return null;
	}
	/**
	 * 
	 * Wrapper method for getValue() - avoids casting the data type.
	 * 
	 * @param String colName
	 *            Column name to retrieve its value from the current record
	 *            
	 * @return The column value in its native data type
	 * 
	 * @throws Throwable
	 * 
	 *
	 **/
	public java.util.Date getDate(String colName) throws Throwable {
		java.util.Date d = null;
		d = (java.util.Date) getValue(colName);
		return d;
	}

	/**
	 * 
	 * Wrapper method for getValue() - avoids casting the data type.
	 * 
	 * @param String colName
	 *            Column name to retrieve its value from the current record
	 *            
	 * @return The column value in its native data type
	 * 
	 * @throws Throwable
	 * 
	 * 
	 **/
	public double getDouble(String colName) throws Throwable {
		Double d = new Double(String.valueOf(getValue(colName)));
		return d.doubleValue();
	}

	/**
	 * 
	 * Wrapper method for getValue() - avoids casting the data type.
	 * 
	 * @param String colName
	 *            Column name to retrieve its value from the current record
	 *            
	 * @return The column value in its native data type
	 *
	 * @throws Throwable
	 *
	 * 
	 **/
	public int getInt(String colName) throws Throwable {
		Integer i = new Integer(String.valueOf(getValue(colName)));
		return i.intValue();
	}

	/**
	 * Wrapper method for getValue() - avoids casting the data type
	 * 
	 * @param String colName
	 *            Column name to retrieve its value from the current record
	 *            
	 * @return The column value in its native data type
	 * 
	 * @throws Throwable
	 *
	 * 
	 **/
	public Integer getInteger(String colName) throws Throwable {
		Integer i = new Integer(String.valueOf(getValue(colName)));
		return i;
	}

	/**
	 * 
	 * Tests if the give column value is null for the current record.
	 * 
	 * @param String colName
	 *            Column name
	 *            
	 * @return TRUE if the value is null
	 * 
	 * @throws Throwable
	 *             If record position is not valid or the column does not exist
	 *             in the recordset
	 *             
	 * 
	 **/
	public boolean isNull(String colName) throws Throwable {
		if (getValue(colName) == null)
			return true;
		else
			return false;
	}

	/**
	 * 
	 * Check if recordset contains field with a given name.
	 * 
	 * @param String name
	 *            Name of the field to check its existence
	 *            
	 * @return TRUE if field exists, FALSE if not
	 *
	 * 
	 **/
	public boolean containsField(String name) {
		if (campos.containsKey(name))
			return true;
		else
			return false;
	}

	/**
	 * 
	 * Tests if a given record number represents a valid record position in the
	 * Recordset.
	 * 
	 * @param int recNum
	 *            Record number (between 0...N-1 where N is the number of
	 *            records)
	 * 
	 * @throws RecordsetException
	 *             If the test fails
	 *
	 * 
	 **/
	private void checkRecordPosition(int recNum) throws Exception {
		if (recNum < 0 || recNum > data.size() - 1) {

			StringBuffer errMsg = new StringBuffer();

			errMsg.append("Invalid record position: " + recNum + "; ");
			if (recNum == -1)
				errMsg.append("After creating a Recordset you must move to a valid record using next(), first(), last() or setRecordNumber() methods before attempting read/write operations with any record of this Recordset; ");
			errMsg.append("This Recordset contains "
					+ data.size()
					+ " record(s); Set the record position between 0 and N-1 where N is the number of records.");

			throw new Exception(errMsg.toString());

		}
	}

	/**
	 * 
	 * Tests if the current record number represents a valid record position in
	 * the Recordset. This overload reuses the checkRecordPosition(int recNum)
	 * method.
	 * 
	 * @throws RecordsetException
	 *             If the test fails
	 *
	 * 
	 **/
	private void checkRecordPosition() throws Exception {
		checkRecordPosition(this.recordNumber);
	}

	/**
	 * 
	 * Set the children recordset for the current record.
	 * 
	 * @param Recordset rs
	 *            Children recordset
	 *            
	 * @throws Throwable
	 *             If the record position is not valid
	 *
	 * 
	 **/
	public void setChildrenRecordset(Recordset rs) throws Throwable {
		checkRecordPosition();
		Record rec = (Record) data.get(recordNumber);
		rec.setChildren(rs);
	}

	/**
	 * 
	 * Retrieve current record's children recordset.
	 * 
	 * @return A reference to the children recordset or null if no children
	 *         recordset exists
	 *         
	 * @throws Throwable
	 *             If the record position is not valid
	 *
	 *
	 **/
	public Recordset getChildrenRecordset() throws Throwable {
		checkRecordPosition();
		Record rec = (Record) data.get(recordNumber);
		return rec.getChildren();
	}

	/**
	 * 
	 * Clear current record values, set every field's value to null.
	 *
	 * 
	 **/
	public void clear() throws Throwable {
		checkRecordPosition();
		Iterator<RecordsetField> i = campos.values().iterator();
		while (i.hasNext()) {
			RecordsetField f = (RecordsetField) i.next();
			setValue(f.getName(), null);
		}
	}

}
