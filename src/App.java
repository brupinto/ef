import br.com.utils.ef.*;


public class App {

	public static void main( String[] args ) {
		(new App()).montaArquivo();
	}

	private void montaArquivo(){
		
		ExportFile ef 		= 	new ExportFile();
		LayoutFile header 	=  	ef.newLayout("HEADER");
		
		ef.newLayout( "TESTE_PEDRO" );
		
		//define a composição do registro
		header.define("F01", LayoutFileImpl.STRING , LayoutFileImpl.ALINHAMENTO_ESQUERDO, LayoutFileImpl.ESPACO, 7, "HEADER");
		header.define("F02", LayoutFileImpl.STRING , LayoutFileImpl.ALINHAMENTO_ESQUERDO, LayoutFileImpl.ESPACO, 3, "ISS");
		header.define("F03", LayoutFileImpl.NUMERIC, LayoutFileImpl.ALINHAMENTO_ESQUERDO, LayoutFileImpl.ESPACO, 5, "21");
		header.define("F04", LayoutFileImpl.STRING , LayoutFileImpl.ALINHAMENTO_ESQUERDO, LayoutFileImpl.ESPACO, 4, "CADR");
		header.define("F05", LayoutFileImpl.STRING , LayoutFileImpl.ALINHAMENTO_ESQUERDO, LayoutFileImpl.ESPACO, 3, "DI");
		header.define("F06", LayoutFileImpl.NUMERIC, LayoutFileImpl.ALINHAMENTO_ESQUERDO, LayoutFileImpl.ESPACO, 6);
		header.define("F07", LayoutFileImpl.STRING , LayoutFileImpl.ALINHAMENTO_ESQUERDO, LayoutFileImpl.ESPACO, 4, ".IN");
		header.define("F08", LayoutFileImpl.NUMERIC, LayoutFileImpl.ALINHAMENTO_ESQUERDO, LayoutFileImpl.ESPACO, 12);
		header.define("F09", LayoutFileImpl.NUMERIC, LayoutFileImpl.ALINHAMENTO_ESQUERDO, LayoutFileImpl.ESPACO, 8, "21");
		header.define("F10", LayoutFileImpl.STRING , LayoutFileImpl.ALINHAMENTO_ESQUERDO, LayoutFileImpl.ESPACO, 16);
		header.define("F11", LayoutFileImpl.NUMERIC, LayoutFileImpl.ALINHAMENTO_ESQUERDO, LayoutFileImpl.ESPACO, 9);
		header.define("F12", LayoutFileImpl.NUMERIC, LayoutFileImpl.ALINHAMENTO_ESQUERDO, LayoutFileImpl.ZERO  , 6);
		
		//define se vai exibir a linha com os titulos dos campos
		header.defineTitle(true);
		
		//define o separador dos campos
		header.defineSeparador(";");
		
		//busca um layout especifico
		header	= ef.getLayout("HEADER");
		
		
		for(int i=1; i < 10; i++){
			//gera uma nova linha de registro
			header.newLine();
			
			//seta valores na linha corrente
			header.set("F06", 1);
			header.set("F08", 1);
			header.set("F11", i);
		}
		
		LayoutFile teste = ef.getLayout( "TESTE_PEDRO" );
		
		teste.define("F01", LayoutFileImpl.STRING , LayoutFileImpl.ALINHAMENTO_ESQUERDO, LayoutFileImpl.ESPACO, 5,"C1");
		teste.define("F02", LayoutFileImpl.STRING , LayoutFileImpl.ALINHAMENTO_ESQUERDO, LayoutFileImpl.ESPACO, 5,"C2");
		teste.define("F03", LayoutFileImpl.STRING , LayoutFileImpl.ALINHAMENTO_ESQUERDO, LayoutFileImpl.ESPACO, 5,"C3");
		teste.define("F04", LayoutFileImpl.STRING , LayoutFileImpl.ALINHAMENTO_ESQUERDO, LayoutFileImpl.ESPACO, 5,"C4");
		teste.define("F05", LayoutFileImpl.STRING , LayoutFileImpl.ALINHAMENTO_ESQUERDO, LayoutFileImpl.ESPACO, 5,"C5");
		teste.define("F06", LayoutFileImpl.NUMERIC, LayoutFileImpl.ALINHAMENTO_DIREITO, LayoutFileImpl.ESPACO  , 5);
		
		teste.defineSeparador(";");
				
		for(int i=0; i < 100; i++){
			teste.newLine();
			teste.set("F06", i);
		}
		
		//configura a ordem que os layouts serão impressos no arquivo
		ef.format("TESTE_PEDRO", "HEADER");
				
		//gera o arquivo
		try{
			ef.saveFile("test/arquivo_test3.txt");
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
}
