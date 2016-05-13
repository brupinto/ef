package br.com.utils.ef;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author brupinto
 *
 */
public class ExportFile {
	private class LayoutIndex {
		private String 		identificadorLayout;
		private	LayoutFileImpl 	layoutFile;

		public LayoutIndex(String s, LayoutFileImpl l){
			identificadorLayout = s;
			layoutFile = l;
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
	public LayoutFileImpl newLayout( String identificadorLayout ) {
		LayoutFileImpl 	lf = new LayoutFileImpl();
		LayoutIndex	li = new LayoutIndex(identificadorLayout, lf); 
		layouts.add(li);
		
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
		FileWriter 		fileWriter 		= null;
		BufferedWriter 	bufferedWriter 	= null;
		
		try{
			File file		= new File(pathfilename);
			fileWriter 		= new FileWriter(file);
			bufferedWriter 	= new BufferedWriter(fileWriter);

			for (LayoutIndex row : layouts){
				LayoutFileImpl 	lf 		= row.getLayoutFile();
				String 		title 	= lf.getTitle();
				
				if (title != null){
					bufferedWriter.write(title);
					bufferedWriter.write("\r\n");
				}
				
				for (String l : lf.getRows()){
					bufferedWriter.write(l);
					bufferedWriter.write("\r\n");
				}
			}
			
			bufferedWriter.close();
			fileWriter.close();
			fileWriter 		= null;
			bufferedWriter 	= null;
			
		}catch(Exception e){
			fileWriter 		= null;
			bufferedWriter 	= null;
			throw e;
		}
	}
}