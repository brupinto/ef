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
		header.define("F01", LayoutFile.STRING , LayoutFile.ALINHAMENTO_ESQUERDO, LayoutFile.ESPACO, 7, "HEADER");
		header.define("F02", LayoutFile.STRING , LayoutFile.ALINHAMENTO_ESQUERDO, LayoutFile.ESPACO, 3, "ISS");
		header.define("F03", LayoutFile.NUMERIC, LayoutFile.ALINHAMENTO_ESQUERDO, LayoutFile.ESPACO, 5, "21");
		header.define("F04", LayoutFile.STRING , LayoutFile.ALINHAMENTO_ESQUERDO, LayoutFile.ESPACO, 4, "CADR");
		header.define("F05", LayoutFile.STRING , LayoutFile.ALINHAMENTO_ESQUERDO, LayoutFile.ESPACO, 3, "DI");
		header.define("F06", LayoutFile.NUMERIC, LayoutFile.ALINHAMENTO_ESQUERDO, LayoutFile.ESPACO, 6);
		header.define("F07", LayoutFile.STRING , LayoutFile.ALINHAMENTO_ESQUERDO, LayoutFile.ESPACO, 4, ".IN");
		header.define("F08", LayoutFile.NUMERIC, LayoutFile.ALINHAMENTO_ESQUERDO, LayoutFile.ESPACO, 12);
		header.define("F09", LayoutFile.NUMERIC, LayoutFile.ALINHAMENTO_ESQUERDO, LayoutFile.ESPACO, 8, "21");
		header.define("F10", LayoutFile.STRING , LayoutFile.ALINHAMENTO_ESQUERDO, LayoutFile.ESPACO, 16);
		header.define("F11", LayoutFile.NUMERIC, LayoutFile.ALINHAMENTO_ESQUERDO, LayoutFile.ESPACO, 9);
		header.define("F12", LayoutFile.NUMERIC, LayoutFile.ALINHAMENTO_ESQUERDO, LayoutFile.ZERO  , 6);
		
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
		
		teste.define("F01", LayoutFile.STRING , LayoutFile.ALINHAMENTO_ESQUERDO, LayoutFile.ESPACO, 5,"C1");
		teste.define("F02", LayoutFile.STRING , LayoutFile.ALINHAMENTO_ESQUERDO, LayoutFile.ESPACO, 5,"C2");
		teste.define("F03", LayoutFile.STRING , LayoutFile.ALINHAMENTO_ESQUERDO, LayoutFile.ESPACO, 5,"C3");
		teste.define("F04", LayoutFile.STRING , LayoutFile.ALINHAMENTO_ESQUERDO, LayoutFile.ESPACO, 5,"C4");
		teste.define("F05", LayoutFile.STRING , LayoutFile.ALINHAMENTO_ESQUERDO, LayoutFile.ESPACO, 5,"C5");
		teste.define("F06", LayoutFile.NUMERIC, LayoutFile.ALINHAMENTO_DIREITO, LayoutFile.ZERO  , 5);
		
		teste.defineSeparador(";");
				
		for(int i=0; i < 100; i++){
			teste.newLine();
			teste.set("F06", i);
		}
		
		//configura a ordem que os layouts serão impressos no arquivo
		ef.format("HEADER","TESTE_PEDRO");
				
		//gera o arquivo
		try{
			ef.saveFile("arquivo_test3.txt");
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
}
