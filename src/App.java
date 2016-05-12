import br.com.utils.ef.*;


public class App {

	public static void main( String[] args ) {
		(new App()).montaArquivo();
	}

	private void montaArquivo(){
		
		ExportFile ef 		= 	new ExportFile();
		LayoutFile header 	=  	ef.newLayout("HEADER");
		
		//define a composição do registro
		header.define("seq_01", LayoutFile.STRING , LayoutFile.ALINHAMENTO_ESQUERDO, LayoutFile.ESPACO, 7, "HEADER");
		header.define("seq_02", LayoutFile.STRING , LayoutFile.ALINHAMENTO_ESQUERDO, LayoutFile.ESPACO, 3, "ISS");
		header.define("seq_03", LayoutFile.NUMERIC, LayoutFile.ALINHAMENTO_ESQUERDO, LayoutFile.ESPACO, 5, "21");
		header.define("seq_04", LayoutFile.STRING , LayoutFile.ALINHAMENTO_ESQUERDO, LayoutFile.ESPACO, 4, "CADR");
		header.define("seq_05", LayoutFile.STRING , LayoutFile.ALINHAMENTO_ESQUERDO, LayoutFile.ESPACO, 2, "DI");
		header.define("seq_06", LayoutFile.NUMERIC, LayoutFile.ALINHAMENTO_ESQUERDO, LayoutFile.ESPACO, 6);
		header.define("seq_07", LayoutFile.STRING , LayoutFile.ALINHAMENTO_ESQUERDO, LayoutFile.ESPACO, 4, ".IN");
		header.define("seq_08", LayoutFile.NUMERIC, LayoutFile.ALINHAMENTO_ESQUERDO, LayoutFile.ESPACO, 12);
		header.define("seq_09", LayoutFile.NUMERIC, LayoutFile.ALINHAMENTO_ESQUERDO, LayoutFile.ESPACO, 8, "21");
		header.define("seq_10", LayoutFile.STRING , LayoutFile.ALINHAMENTO_ESQUERDO, LayoutFile.ESPACO, 16);
		header.define("seq_11", LayoutFile.NUMERIC, LayoutFile.ALINHAMENTO_ESQUERDO, LayoutFile.ESPACO, 9);
		header.define("seq_12", LayoutFile.NUMERIC, LayoutFile.ALINHAMENTO_ESQUERDO, LayoutFile.ZERO  , 6);
		
		//define se vai exibir a linha com os titulos dos campos
		header.defineTitle(false);
		
		//define o separador dos campos
		header.defineSeparador(";");
		
		//busca um layout especifico
		header	= ef.getLayout("HEADER");
		
		//gera uma nova linha de registro
		header.newLine();
		
		//seta valores na linha corrente
		header.set("seq_06", 1);
		header.set("seq_08", 1);
		header.set("seq_11", 1000);
		
		
		//configura a ordem que os layouts serão impressos no arquivo
		ef.format("HEADER");
				
		//gera o arquivo
		try{
			ef.saveFile("arquivo_teste.txt");
		}
		catch(Exception e){
			System.out.println(e);
		}
		
	}
}
