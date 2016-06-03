package br.com.utils.ef;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
	
	private List<LayoutIndex> 	fields 		= new ArrayList<LayoutIndex>();
	private List<Data>			current		= new ArrayList<Data>();
	private int					rows		= 0;
	private Boolean 			hasTitle	= false;
	private Boolean 			isTruncate	= false;
	private String 				delimitador	= null;
	private File    			bf 			= null;
		 
	public LayoutFileImpl() throws Exception {
		
		createBufFile();
		
	}
	
	public void defineTruncateField(boolean b){
		isTruncate = b;
	}
	
	private void createBufFile() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssz");
		bf	= File.createTempFile( ".EF_", sdf.format( new Date() ) );
	}
	public void close(){
		try{
			bf.deleteOnExit();
		}catch(Exception e){
			System.out.println("[EF ERRO] "+e);
		}
	}
	
	public String getTitle() {
		String result = null;
		
		if (hasTitle){
			result = formatTitle( fields );
		}
		
		return result;
	}
	
	public FileInputStream getRows() {
		FileInputStream fis = null;
		
		if (current.size() > 0){
			newLine();
		}
		
		try{
			fis = new FileInputStream(bf);
		}catch(Exception e){
			System.out.println("[EF ERRO] "+e);
		}
		return fis;
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
			rows++;
			writeTempFile();
			current.clear();
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
	
	private void writeTempFile() {
		try{
			FileWriter 	fw	= new FileWriter(bf,true);
			fw.write(formatData(current)+"\r\n");
			fw.close();
		}catch(Exception e){
			System.out.println("[EF ERRO] "+e);
		}
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
			
			if (isTruncate)
				if (v.trim().length() > li.getTamanho())
					v = v.trim().substring( 0, li.getTamanho());
			
			if (li.getAlinhamento() == ALINHAMENTO_DIREITO){
				result = spaces+v;
			}
			else
				result = v+spaces;
		}
		else{
			result = v;
		}
		
		return result;
	}

	public int	getCurrentRow(){
		int row = 0;
		row = rows;

		if (current.size() > 0)
			row ++;
			
		return row;
	}
	
	public int getTotalRows(){
		int row = 0;
		row = rows;
		
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
		close();
		try{
			createBufFile();
		} catch(Exception e){
			System.out.println("[EF ERRO] "+e);
		}
		rows	= 0;
		current = null;
		current = new ArrayList<Data>();
	}
}
