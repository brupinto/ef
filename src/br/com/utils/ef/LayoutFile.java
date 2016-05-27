package br.com.utils.ef;

import java.io.FileInputStream;

/**
 * Interface of manipulating a layout of file
 * 
 * @author brupinto
 * @since 17/05/2016
 *
 */
public interface LayoutFile {
	/**
	 * Datatype alphanumeric
	 */
	public static final int STRING  				= 10;
	/**
	 * Datatype Numeric
	 */
	public static final int NUMERIC 				= 11;
	/**
	 * Datatype decimal
	 */
	public static final int DECIMAL 				= 12;
	/**
	 * Align left
	 */
	public static final int ALINHAMENTO_ESQUERDO 	= 20;
	/**
	 * Data Align right
	 */
	public static final int ALINHAMENTO_DIREITO  	= 21;
	/**
	 * fill up spaces with spaces
	 */
	public static final int ESPACO 					= 30;
	/**
	 * fill up spaces with zeros
	 */
	public static final int ZERO 					= 31;
	/**
	 * Shrink data removing extra spaces
	 */
	public static final int ABSOLUTO 				= 32;
	
	/**
	 * Retrieve the row formatted with fields Labels.
	 * @return - String with title formatted.
	 */
	public String getTitle();
	/**
	 * Retrieve all rows data formatted
	 * @return List<String> with all hows
	 */
	public FileInputStream getRows();
	/**
	 * Define propeties about new field 
	 * @param idField - field identify
	 * @param datatype - field datatype you can use use LayoutFile.NUMERIC. LayoutFile.STRING
	 * @param alinhamento - define if data will align in left side or right side
	 * @param preenchimento - replace spaces with LayoutFile.ESPACO or LayoutFile.ZERO
	 * @param tamanho - length of field
	 * @param valordefault - Default value to field
	 */
	public void define( String idField, int datatype, int alinhamento, int preenchimento, int tamanho, String valordefault);
	/**
	 * Define propeties about new field 
	 * @param idField - field identify
	 * @param datatype - field datatype you can use use LayoutFile.NUMERIC. LayoutFile.STRING
	 * @param alinhamento - define if data will align in left side or right side
	 * @param preenchimento - replace spaces with LayoutFile.ESPACO or LayoutFile.ZERO
	 * @param tamanho - length of field
	 */
	public void define( String idField, int datatype, int alinhamento, int preenchimento, int tamanho);
	/**
	 * Define separator character between the fields
	 * if you not define a character EF will generate the row continium
	 * @param separador - separator character
	 */
	public void defineSeparador( String separador );
	/**
	 * Define if the data of field will truncate the data with field lenght.	
	 * @param b
	 */
	public void defineTruncateField(boolean b);
	
	/**
	 * Define if you want show up field titles in the file
	 * @param b true  - show
	 *          false - don't show 
	 */
	public void defineTitle( boolean b );
	/**
	 * Define the value in the row
	 * @param idField - field identify
	 * @param value  - value you want save in the field of current row
	 */
	public void set( String idField, String value );
	
	/**
	 * save current row in data pool and generate new data row empty
	 * @return number of current row.
	 */
	public int newLine();
	
	/**
	 * Get number of current row
	 * @return  number of current row.
	 */
	public int getCurrentRow();
	/**
	 * Get number of rows store in the data pool.
	 * @return  number rows in data pool.
	 */
	public int getTotalRows();
	
	/**
	 * Define the value in the row
	 * @param idField - field identify
	 * @param value  - value you want save in the field of current row
	 */
	public void set( String idField, double value );
	/**
	 * Define the value in the row
	 * @param idField - field identify
	 * @param value  - value you want save in the field of current row
	 */
	public void set( String idField, int value );
	/**
	 * Clear data pool and put it empty
	 */
	public void removeAllLines();
	
	/**
	 * close layout buffer data
	 */
	public void close();
}
