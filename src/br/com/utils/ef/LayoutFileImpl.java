package br.com.utils.ef;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
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
public class LayoutFileImpl  implements LayoutFile, LayoutComplexFile {
	private class LayoutIndex{
		private String 	idField;
		private int		rowComplex;
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
		public int getRowComplex() { return rowComplex; }
	
		public LayoutIndex( String idField, int rowComplex, int datatype, int alinhamento, int preenchimento, int tamanho, String valordefault){
			this.idField		= idField;
			this.dataType		= datatype;
			this.alinhamento	= alinhamento;
			this.preenchimento	= preenchimento;
			this.tamanho		= tamanho;
			this.valorDefault	= valordefault;
			this.rowComplex		= rowComplex;
		}
	}
	private class Data{
		private String 		idField;
		private LayoutIndex li;
		private String 		valor;
		private int 		rowComplex;
		public int getRowComplex() { return rowComplex; }
		public void setRowComplex(int rowComplex) { this.rowComplex = rowComplex; } 
		public String getIdField() { return idField; }
		public void setIdField( String idField ) { this.idField = idField; }
		public LayoutIndex getLi() { return li; }
		public void setLi( LayoutIndex li ) { this.li = li; }
		public String getValor() { return valor; }
		public void setValor( String valor ) { this.valor = valor; }
	}
	
	private List<LayoutIndex> 	fields 			= new ArrayList<LayoutIndex>();
	private List<Data>			current			= new ArrayList<Data>();
	private List<String>		rowsFormated	= null;
	private int					rows			= 0;
	private Boolean 			hasTitle		= false;
	private Boolean 			isTruncate		= false;
	private String 				delimitador		= null;
	private File    			bf 				= null;
	private Boolean				withBufFile	=	 false;
		 
	public LayoutFileImpl(Boolean withBufFile) throws Exception {
		this.withBufFile = withBufFile;
		
		if (withBufFile)
			createBufFile();
		else{
			rowsFormated = new ArrayList<String>();
		}
		
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
			if (withBufFile)
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
	
	public List<String> getRowsFormated() {
		if (current.size() > 0){
			newLine();
		}
		
		return rowsFormated;
	}
	
	public void define( String idField, int datatype, int alinhamento, int preenchimento, int tamanho, String valordefault) {
		LayoutIndex li = new LayoutIndex(idField, -99999, datatype, alinhamento, preenchimento, tamanho, valordefault);
		fields.add(li);
	}
	
	public void define( String idField,int rowComplex, int datatype, int alinhamento, int preenchimento, int tamanho, String valordefault) {
		
		LayoutIndex li = new LayoutIndex(idField, rowComplex, datatype, alinhamento, preenchimento, tamanho, valordefault);
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
	
	public void set( String idField,int rowComplex, String value ) {
		for(Data data : current){
			if (data.getRowComplex() == rowComplex){
				if (data.getIdField().equals( idField )){
					data.setValor( value );
					break;
				}
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
			
			d.setRowComplex( li.getRowComplex() );
			d.setIdField( li.getIdField() );
			d.setLi( li );
			d.setValor( li.getValorDefault() );
			
			current.add(d);
		}
		
		return getCurrentRow();
	}
	
	private void writeTempFile() {
		try{
			String linha = formatData(current)+"\r\n";
			
			if (withBufFile){
				OutputStreamWriter	fw	= new OutputStreamWriter(new FileOutputStream(bf,true), "UTF-8");
				fw.write(linha);
				fw.close();
			}
			else{
				rowsFormated.add(linha);
			}
				
		}catch(Exception e){
			System.out.println("[EF ERRO] "+e);
		}
	}
	
	private String formatData(List<Data> datas){
		String result 		= "";
		int	   complexId	= datas.get( 0 ).getRowComplex();
		
		for (Data li : datas){
			if (complexId != li.getRowComplex()){
				complexId = li.getRowComplex();
				result	 += "\r\n";
			}
			
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
		
		if (li.getPreenchimento() != LayoutFile.ABSOLUTO){
			spaces = "";
			for(int i =0; i < nSpaces; i++){
				if (!forceSpace){
					spaces += (li.getPreenchimento() == LayoutFile.ZERO)?"0":" ";
				}
				else{
					spaces += " ";
				}
			}
			
			if (isTruncate)
				if (v.trim().length() > li.getTamanho())
					v = v.trim().substring( 0, li.getTamanho());
			
			if (li.getAlinhamento() == LayoutFile.ALINHAMENTO_DIREITO){
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
	
	public void define( String idField, int datatype, int alinhamento, int preenchimento, int tamanho) {
		define(idField,datatype,alinhamento,preenchimento,tamanho, ""); 
	}
	
	public void define( String idField, int rowComplex, int datatype, int alinhamento, int preenchimento, int tamanho) {
		define(idField,rowComplex,datatype,alinhamento,preenchimento,tamanho, ""); 
	}
	
	public void set( String idField, double value ) {
		set(idField, String.valueOf( value )); 
	}
	
	public void set( String idField, int value ){
		set(idField, String.valueOf( value )); 
	}
	
	public void set( String idField, int rowComplex, double value ){
		set(idField, rowComplex, String.valueOf( value )); 
	}
	
	public void set( String idField, int rowComplex, int value ){
		set(idField, rowComplex, String.valueOf( value )); 
	}
	
	public void removeAllLines() {
		close();
		try{
			if (withBufFile){
				createBufFile();
			}
			else
			{
				rowsFormated = new ArrayList<String>();
			}
		} catch(Exception e){
			System.out.println("[EF ERRO] "+e);
		}
		rows	= 0;
		current = null;
		current = new ArrayList<Data>();
	}
}
