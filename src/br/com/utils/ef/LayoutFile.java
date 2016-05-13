package br.com.utils.ef;

import java.util.List;


public interface LayoutFile {
	public static final int STRING  				= 10;
	public static final int NUMERIC 				= 11;
	public static final int DECIMAL 				= 12;
	public static final int ALINHAMENTO_ESQUERDO 	= 20;
	public static final int ALINHAMENTO_DIREITO  	= 21;
	public static final int ESPACO 					= 30;
	public static final int ZERO 					= 31;
	public static final int ABSOLUTO 				= 32;
	
	public String getTitle();
	public List<String> getRows();
	public void define( String idField, int datatype, int alinhamento, int preenchimento, int tamanho, String valordefault);
	public void set( String idField, String value );
	public void newLine();
	public void defineSeparador( String separador );
	public void defineTitle( boolean b );
	public void define( String idField, int datatype, int alinhamento, int preenchimento, int tamanho);
	public void set( String idField, double value );
	public void set( String idField, int value );
	public void removeAllLines();
}
