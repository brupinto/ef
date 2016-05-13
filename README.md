# EF (Export File)
Library Java for make easy export flat-file with one or more data layout.


## How to use EF: 

**Step 01 -** Initiate the file you want save
    
    import br.com.utils.ef.ExportFile;
    import br.com.utils.ef.LayoutFile;
    
    ...
    
    ExportFile ef 		= 	new ExportFile();

**Step 02 -** Define the layout or layouts you want export inside of your file
	
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


**Step 03 -** Define the order of layouts will be exported in the final file

    ef.format("HEADER", "BODY", "TRAILER");

**Step 04 -** Populate each layout with respectives datas

	layout = ef.getLayout("HEADER");
	layout.newLine();
	layout.set("fileName", "exemple01.txt");

	layout = ef.getLayout("BODY");

	for (int i = 0; i < 10; i++){
		layout.newLine();
		layout.set("id", i);
	} 

	layout = ef.getLayout("TRAILER");
	layout.newLine();
	layout.set("filler","EOF.");


**Step 05 -** Export final file

		try{
			ef.saveFile("test.txt");
		}
		catch(Exception e){
			System.out.println(e);
		}

**Result -** test.txt

	"00test.txt                    "
	"01       0                    "
	"01       1                    "
	"01       2                    "
	"01       3                    "
	"01       4                    "
	"01       5                    "
	"01       6                    "
	"01       7                    "
	"01       8                    "
	"01       9                    "
	"99                        EOF."