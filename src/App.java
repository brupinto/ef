import br.com.utils.ef.ExportFile;
import br.com.utils.ef.LayoutComplexFile;
import br.com.utils.ef.LayoutFile;

public class App {

	public static void main( String[] args ) {
		(new App()).montaArquivo();
		System.out.println("fim.");
	}

	private void montaArquivo(){
		ExportFile ef 		= 	new ExportFile();
		LayoutFile layout 	= ef.newLayout("HEADER");
		layout.defineTruncateField( true );
		
	    layout.define("id", LayoutFile.STRING, LayoutFile.ALINHAMENTO_ESQUERDO, LayoutFile.ESPACO, 7,"HEADER");
	    layout.define("totalLinhas", LayoutFile.NUMERIC, LayoutFile.ALINHAMENTO_DIREITO, LayoutFile.ZERO,33);
		layout.newLine();

		
		layout = ef.newLayout("BODY");
	    layout.defineTruncateField( true );
	    
	    layout.define("idSection", LayoutFile.NUMERIC, LayoutFile.ALINHAMENTO_DIREITO, LayoutFile.ZERO, 10);
	    layout.define("descricao", LayoutFile.STRING, LayoutFile.ALINHAMENTO_ESQUERDO, LayoutFile.ESPACO, 20);
	    layout.define("teste", LayoutFile.STRING, LayoutFile.ALINHAMENTO_ESQUERDO, LayoutFile.ESPACO, 5);
	    layout.define("sequencial", LayoutFile.NUMERIC, LayoutFile.ALINHAMENTO_DIREITO, LayoutFile.ZERO, 5);
	    
	    
	    for (int i = 0; i < 30; i++){
		
	    	int row = layout.newLine();
			layout.set("idSection", i);
			layout.set("descricao", "AAAAAAAAAAAAAAAAA"+i+" ");
			layout.set("sequencial", row);
		} 
	    
	    LayoutComplexFile lcf = ef.newLayoutComplex( "COMPLEX" );
	    
	    lcf.define( "01", 1, LayoutFile.NUMERIC, LayoutFile.ALINHAMENTO_DIREITO, LayoutFile.ZERO, 10);
	    lcf.define( "02", 1, LayoutFile.NUMERIC, LayoutFile.ALINHAMENTO_DIREITO, LayoutFile.ZERO, 10);
	    lcf.define( "03", 1, LayoutFile.NUMERIC, LayoutFile.ALINHAMENTO_DIREITO, LayoutFile.ZERO, 10);
	    lcf.define( "04", 1, LayoutFile.NUMERIC, LayoutFile.ALINHAMENTO_DIREITO, LayoutFile.ZERO, 10);
	    
	    lcf.define( "01", 2, LayoutFile.NUMERIC, LayoutFile.ALINHAMENTO_DIREITO, LayoutFile.ZERO, 10);
	    lcf.define( "02", 2, LayoutFile.NUMERIC, LayoutFile.ALINHAMENTO_DIREITO, LayoutFile.ZERO, 10);
	    lcf.define( "03", 2, LayoutFile.NUMERIC, LayoutFile.ALINHAMENTO_DIREITO, LayoutFile.ZERO, 20);
	    
	    
	    layout.define("idSection", LayoutFile.NUMERIC, LayoutFile.ALINHAMENTO_DIREITO, LayoutFile.ZERO, 10);
	    layout.define("descricao", LayoutFile.STRING, LayoutFile.ALINHAMENTO_ESQUERDO, LayoutFile.ESPACO, 20);
	    layout.define("teste", LayoutFile.STRING, LayoutFile.ALINHAMENTO_ESQUERDO, LayoutFile.ESPACO, 5);
	    layout.define("sequencial", LayoutFile.NUMERIC, LayoutFile.ALINHAMENTO_DIREITO, LayoutFile.ZERO, 5);
	    
		layout = ef.newLayout("TRAILER");
		layout.defineTruncateField( true );
		
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
