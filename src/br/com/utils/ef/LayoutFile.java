package br.com.utils.ef;

import java.util.List;


public interface LayoutFile {
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
}
