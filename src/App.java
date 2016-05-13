import br.com.utils.ef.ExportFile;
import br.com.utils.ef.LayoutFile;

public class App {

	public static void main( String[] args ) {
		(new App()).montaArquivo();
	}

	private void montaArquivo(){
		
		ExportFile ef 		= 	new ExportFile();
		
		ef.newLayout("HEADER");
		ef.newLayout("BODY");
		ef.newLayout("TRAILER");

	    LayoutFile layout = ef.getLayout("HEADER");
	    
	    layout.define("idSection", LayoutFile.NUMERIC, LayoutFile.ALINHAMENTO_DIREITO, LayoutFile.ZERO, 2);
	    layout.define("fileName", LayoutFile.STRING, LayoutFile.ALINHAMENTO_ESQUERDO, LayoutFile.ESPACO, 10);
	    layout.define("filler", LayoutFile.STRING, LayoutFile.ALINHAMENTO_ESQUERDO, LayoutFile.ESPACO, 18);
	        
		layout = ef.getLayout("BODY");

		layout.define("idSection", LayoutFile.NUMERIC, LayoutFile.ALINHAMENTO_DIREITO, LayoutFile.ZERO, 2, "1");
	    layout.define("name", LayoutFile.STRING, LayoutFile.ALINHAMENTO_ESQUERDO, LayoutFile.ESPACO, 3);
	    layout.define("id", LayoutFile.NUMERIC, LayoutFile.ALINHAMENTO_DIREITO, LayoutFile.ESPACO, 5);
		layout.define("filler", LayoutFile.STRING, LayoutFile.ALINHAMENTO_ESQUERDO, LayoutFile.ESPACO, 20);

		layout = ef.getLayout("TRAILER");

		layout.define("idSection", LayoutFile.NUMERIC, LayoutFile.ALINHAMENTO_DIREITO, LayoutFile.ZERO, 2, "99");
	    layout.define("filler", LayoutFile.STRING, LayoutFile.ALINHAMENTO_DIREITO, LayoutFile.ESPACO, 28);
	    
	    ef.format("HEADER", "BODY", "TRAILER");
	    
	    layout = ef.getLayout("HEADER");
		layout.newLine();
		layout.set("fileName", "test.txt");

		layout = ef.getLayout("BODY");

		for (int i = 0; i < 10; i++){
			layout.newLine();
			layout.set("id", i);
		} 

		layout = ef.getLayout("TRAILER");
		layout.newLine();
		layout.set("filler","EOF.");
		
		try{
			ef.saveFile("test/test.txt");
		}
		catch(Exception e){
			System.out.println(e);
		}
	    
	}
}
