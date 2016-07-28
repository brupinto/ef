package br.com.utils.ef;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Class ExportFile represente the file want export
 * 
 * @author brupinto
 * @since 17/05/2016
 *
 */
public class ExportFile {
	private class LayoutIndex {
		private String 		identificadorLayout;
		private	LayoutFileImpl 	layoutFile;

		public LayoutIndex(String s, LayoutFileImpl l){
			identificadorLayout = s;
			layoutFile 			= l;
		}
		public String		getIdentificadorLayout(){ return identificadorLayout;}
		public LayoutFileImpl 	getLayoutFile(){ return layoutFile;}
	}
	
	private List<LayoutIndex>	layouts = new ArrayList<LayoutIndex>();
		
	/**
	 * Metodo responsavel para criar um novo layout de registro dentro do arquivo
	 * @param identificadorLayout - Identificador do Layout
	 * @return - Objeto do Layout
	 */
	public LayoutFile newLayout( String identificadorLayout ) {
		LayoutFileImpl 	lf = null;
		try{
			lf = new LayoutFileImpl();
			LayoutIndex	li = new LayoutIndex(identificadorLayout, lf); 
			layouts.add(li);
		}
		catch(Exception e){
			System.out.println("[EF ERRO] "+e);
		}
		
		return lf;
	}
	
	/**
	 * Metodo responsavel para criar um novo layout de registro dentro do arquivo
	 * @param identificadorLayout - Identificador do Layout
	 * @return - Objeto do Layout
	 */
	public LayoutComplexFile newLayoutComplex( String identificadorLayout ) {
		LayoutFileImpl 	lf = null;
		try{
			lf = new LayoutFileImpl();
			LayoutIndex	li = new LayoutIndex(identificadorLayout, lf); 
			layouts.add(li);
		}
		catch(Exception e){
			System.out.println("[EF ERRO] "+e);
		}
		
		return lf;
	}

	/**
	 * metodo que recupera um layout existente na composição do arquivo
	 * @param identificadorLayout - Identificador do Layout
	 * @return - Objeto do Layout
	 */
	public LayoutFileImpl getLayout( String identificadorLayout ) {
		LayoutFileImpl lf = null;
		
		for (LayoutIndex row : layouts){
			if (row.getIdentificadorLayout().equals( identificadorLayout )){
				lf = row.getLayoutFile();
				break;
			}
		}
		
		return lf;
	}

	/**
	 * Define a ordens dos layouts na composição do arquivo
	 * @param identificadorLayout sequencia de exibição dos layouts
	 */
	public void format( String... identificadorLayout ) {
		List<LayoutIndex> listT = new ArrayList<LayoutIndex>();
		
		for (int i =0; i < identificadorLayout.length; i++)
			for (LayoutIndex row : layouts)
				if (identificadorLayout[i].equals( row.getIdentificadorLayout() )){
					listT.add( row );
					break;
				}
		
		layouts.clear();
		
		for (LayoutIndex row : listT)
			layouts.add( row );
		
		listT = null;
	}	

	/**
	 * Compoe o arquivo de com os layout populados
	 * @param pathfilename - nome do arquivo de saida
	 * @throws Exception - dispado se ocorreu algum erro ao tentar escrever o arquivo de saida
	 */
	public void saveFile( String pathfilename )  throws Exception {
		FileWriter	fw	= null;
			
		try{
			File file	= new File(pathfilename);
			fw	 		= new FileWriter(file);

			for (LayoutIndex row : layouts){
				LayoutFile	 	lf 		= row.getLayoutFile();
				String 			title 	= lf.getTitle();
				FileInputStream fis 	= lf.getRows();
				int				c 		= fis.read();
				
				if (title != null){
					fw.write(title);
					fw.write("\r\n");
				}

				while (c != -1){
					fw.write( c );
					c = fis.read();
				}
				fis.close();
				lf.close();
			}
			
			fw.close();
			fw	= null;
		}catch(Exception e){
			fw 	= null;
			throw e;
		}
	}
	
	/**
	 * Limpa os dados de todos os layouts
	 */
	public void removeAllLines(){
		for (LayoutIndex row : layouts){
			row.getLayoutFile().removeAllLines();
		}
	}
	
	/**
	 * Get total rows of file include total rows of each layout in the file
	 * 
	 * @return total of rows in the file
	 */
	public int getTotalRows(){
		int rows = 0;
		
		for (LayoutIndex row : layouts){
			rows += row.getLayoutFile().getTotalRows();
		}
		
		return rows;
	}
}