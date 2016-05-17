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
	    
	    layout.define("idSection", LayoutFile.NUMERIC, LayoutFile.ALINHAMENTO_DIREITO, LayoutFile.ZERO, 2);
	    layout.define("descricao", LayoutFile.STRING, LayoutFile.ALINHAMENTO_ESQUERDO, LayoutFile.ESPACO, 10);
	    layout.define("sequencial", LayoutFile.NUMERIC, LayoutFile.ALINHAMENTO_DIREITO, LayoutFile.ZERO, 5);
	    
	    for (int i = 100; i < 150; i++){
			int row = layout.newLine();
			layout.set("idSection", i);
			layout.set("descricao", "ds_"+i);
			layout.set("sequencial", row);
		} 

		layout = ef.newLayout("TRAILER");
	    layout.define("id", LayoutFile.STRING, LayoutFile.ALINHAMENTO_ESQUERDO, LayoutFile.ESPACO, 7,"TRAILER");
	    layout.define("filler", LayoutFile.NUMERIC, LayoutFile.ALINHAMENTO_DIREITO, LayoutFile.ZERO, 11);
		layout.newLine();
		
		ef.format("HEADER","TRAILER");
		
		try{
			ef.saveFile("test/test.txt");
		}
		catch(Exception e){
			System.out.println(e);
		}
	    
	}
}
