import br.com.utils.ef.ExportFile;
import br.com.utils.ef.LayoutFile;

public class App {

	public static void main( String[] args ) {
		(new App()).montaArquivo();
		System.out.println("fim.");
	}

	private void montaArquivo(){
		ExportFile ef 		= 	new ExportFile();
		LayoutFile layout 	= ef.newLayout("HEADER");
		
	    layout.define("id", LayoutFile.STRING, LayoutFile.ALINHAMENTO_ESQUERDO, LayoutFile.ESPACO, 7,"HEADER");
	    layout.define("totalLinhas", LayoutFile.NUMERIC, LayoutFile.ALINHAMENTO_DIREITO, LayoutFile.ZERO,33);
	    layout.defineSeparador( ";" );
	    layout.defineTitle( true );
		layout.newLine();

		
		layout = ef.newLayout("BODY");
	    
	    layout.define("idSection", LayoutFile.NUMERIC, LayoutFile.ALINHAMENTO_DIREITO, LayoutFile.ZERO, 10);
	    layout.define("descricao", LayoutFile.STRING, LayoutFile.ALINHAMENTO_ESQUERDO, LayoutFile.ESPACO, 20);
	    layout.define("sequencial", LayoutFile.NUMERIC, LayoutFile.ALINHAMENTO_DIREITO, LayoutFile.ZERO, 10);
	    
	    
	    for (int i = 0; i < 50; i++){
		
	    	int row = layout.newLine();
			layout.set("idSection", i);
			layout.set("descricao", "ds_"+i);
			layout.set("sequencial", row);
		} 
	    
		layout = ef.newLayout("TRAILER");
		
	    layout.define("id", LayoutFile.STRING, LayoutFile.ALINHAMENTO_ESQUERDO, LayoutFile.ESPACO, 7,"TRAILER");
	    layout.define("filler", LayoutFile.NUMERIC, LayoutFile.ALINHAMENTO_DIREITO, LayoutFile.ZERO, 33);
		layout.newLine();
		
		ef.format("HEADER","BODY","TRAILER");
		
		layout = ef.getLayout("HEADER");
		layout.set( "totalLinhas", ef.getTotalRows() );
		
		try{
			ef.saveFile("test/test.txt");
		}
		catch(Exception e){
			System.out.println(e);
		}
	    
	}
}
