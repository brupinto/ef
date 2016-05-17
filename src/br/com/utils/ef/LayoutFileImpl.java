package br.com.utils.ef;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of LayoutFile interface.
 * 
 * @author brupinto
 * @since 17/005/2016
 *
 */
public class LayoutFileImpl  implements LayoutFile {
	private class LayoutIndex{
		private String 	idField;
		private int 	dataType;
		private int		alinhamento;
		private int 	preenchimento;
		private int		tamanho;
		private String  valorDefault;
	
		public String getIdField() { return idField; }
		@SuppressWarnings( "unused" )
		public int getDataType() { return dataType;	}
		public int getAlinhamento() { return alinhamento; }
		public int getPreenchimento() { return preenchimento; }
		public int getTamanho() { return tamanho; }
		public String getValorDefault() { return valorDefault; }
	
		public LayoutIndex( String idField, int datatype, int alinhamento, int preenchimento, int tamanho, String valordefault){
			this.idField		= idField;
			this.dataType		= datatype;
			this.alinhamento	= alinhamento;
			this.preenchimento	= preenchimento;
			this.tamanho		= tamanho;
			this.valorDefault	= valordefault;
		}
	}
	
	private class Data{
		private String 		idField;
		private LayoutIndex li;
		private String 		valor;
		public String getIdField() { return idField; }
		public void setIdField( String idField ) { this.idField = idField; }
		public LayoutIndex getLi() { return li; }
		public void setLi( LayoutIndex li ) { this.li = li; }
		public String getValor() { return valor; }
		public void setValor( String valor ) { this.valor = valor; }
	}
	
	private List<LayoutIndex> 	fields 				= new ArrayList<LayoutIndex>();
	private List<Data>			current				= new ArrayList<Data>();
	private List<List<Data>>	rows				= new ArrayList<List<Data>>();
	private Boolean 			hasTitle			= false;
	private String 				delimitador			= null;

	
	public String getTitle() {
		String result = null;
		
		if (hasTitle){
			result = formatTitle( fields );
		}
		
		return result;
	}
	
	
	public List<String> getRows() {
		List<String> results = new ArrayList<String>();
	
		if (current.size() > 0){
			newLine();
			current = new ArrayList<Data>();
		}
		
		for(List<Data> d : rows)
			results.add(formatData(d));
		
		return results;
	}
	
	
	public void define( String idField, int datatype, int alinhamento, int preenchimento, int tamanho, String valordefault) {
		LayoutIndex li = new LayoutIndex(idField, datatype, alinhamento, preenchimento, tamanho, valordefault);
		fields.add(li);
	}

	
	public void set( String idField, String value ) {
		for(Data data : current){
			if (data.getIdField().equals( idField )){
				data.setValor( value );
				break;
			}
		}
	}
	
	
	public int newLine() {
		if (current.size() > 0){
			rows.add(current);
			current = new ArrayList<Data>();
		}
		
		for(LayoutIndex li : fields){
			Data d = new Data();
			
			d.setIdField( li.getIdField() );
			d.setLi( li );
			d.setValor( li.getValorDefault() );
			
			current.add(d);
		}
		
		return getCurrentRow();
	}
	
	private String formatData(List<Data> datas){
		String result = "";
		for (Data li : datas){
			String r = format(li.getLi(), li.getValor(),false);
			if (delimitador != null)
				r += delimitador;

			result += r;
		}
		
		if (delimitador != null)
			result = result.substring( 0, result.length()-1);
		
		return result;
	}

	private String formatTitle(List<LayoutIndex> fields){
		String result = "";
		for (LayoutIndex li : fields){
			String r = format(li, li.getIdField(), true);
			if (delimitador != null)
				r += delimitador;
			
			result += r;
		}
		
		if (delimitador != null)
			result = result.substring( 0, result.length()-1);
		
		return result;
	}
	
	private String format(LayoutIndex li, String v, Boolean forceSpace){
		String 	result 	= "";
		String 	spaces	= null;
		int		nSpaces	= li.getTamanho() - v.trim().length();
		
		if (li.getPreenchimento() != ABSOLUTO){
			spaces = "";
			for(int i =0; i < nSpaces; i++){
				if (!forceSpace){
					spaces += (li.getPreenchimento() == ZERO)?"0":" ";
				}
				else{
					spaces += " ";
				}
			}
			
			if (li.getAlinhamento() == ALINHAMENTO_DIREITO){
				result = spaces+v.trim();
			}
			else
				result = v.trim()+spaces;
		}
		else{
			result = v;
		}
		
		return result;
	}

	public int	getCurrentRow(){
		int row = 0;
		row = rows.size();

		if (current.size() > 0)
			row ++;
			
		return row;
	}
	public int getTotalRows(){
		int row = 0;
		row = rows.size();
		
		if (current.size() > 0)
			row ++;
		
		if (hasTitle)
			row ++;
		
		return row;
	}
	public void defineSeparador( String separador ) { delimitador = separador; }
	public void defineTitle( boolean b ) { hasTitle = b; }
	public void define( String idField, int datatype, int alinhamento, int preenchimento, int tamanho) { define(idField,datatype,alinhamento,preenchimento,tamanho, ""); }
	public void set( String idField, double value ) { set(idField, String.valueOf( value )); }
	public void set( String idField, int value ) { set(idField, String.valueOf( value )); }
	public void removeAllLines() {
		rows 	= null;
		current = null;
		rows 	= new ArrayList<List<Data>>();
		current = new ArrayList<Data>();
	}
}
