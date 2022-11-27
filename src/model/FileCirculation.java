package model;
import java.io.*;
import java.util.Objects;
import java.util.Properties;
import java.util.Scanner;


public class FileCirculation 
{
	private static String folder = "saves/";
	private static String _encoding;
	private static File file;

    public FileCirculation(String path, String _encoding) {
    	FileCirculation.file = new File(folder + path);
    	FileCirculation._encoding = _encoding;
    }
    
    public static void saveWMachineToFile(WashingMachine machine) throws IOException{
    	boolean ret = false;
    	FileWriter fW = null;
    	try {
	    	fW = new FileWriter(file.getAbsolutePath());
	    	fW.append(machine.toString()).append(String.valueOf('\n'));
	    	System.out.println("Done");
	    	fW.close();
    	}
    	catch(Exception ex) {
    		System.out.println(ex);
    	}
    }
    
	public static WashingMachine loadMachineFromFile() throws IOException {
    	String temp;
    	String key;
    	String value;
    	WashingMachine machine = new WashingMachine();
    	ColoredLinen linen = new ColoredLinen();
    	boolean start = false;
    	try {
    		FileReader fR = new FileReader(file.getAbsolutePath());
    		char tmp = (char) fR.read();
    		BufferedReader bR = new BufferedReader(fR);
    		while (!Objects.equals(temp = bR.readLine(), "") && temp != null) {
    			if(temp.contains("Machine:")) {
    				start = true;
    			}
    			else if (temp.length() == 0) { start = false; }
    			if (start) {
    				key = temp.substring(0, temp.indexOf(':') - 1);
    				value = temp.substring(temp.indexOf(':'), temp.length() - 1);
    				if(!(key.isEmpty()) && !(value.isEmpty())) {
						switch (key) {
							case "Color type" -> machine.color = Color.valueOf(value);
							case "Temperature:" -> machine.temperature = Integer.parseInt(value);
							case "Powdertype:" -> machine.powdertype = value;
							case "Conditioner:" -> machine.conditioner = value;
							case "Temperature of washing:" -> linen.tWashing = Integer.parseInt(value);
							case "Temperature of ironing:" -> linen.tIroning = Integer.parseInt(value);
							case "Color" -> {
								linen.color = Color.valueOf(value);
								machine.Load(linen);
							}
						}
					}
				}
			}
		}
    	catch(FileNotFoundException ex) {
    		System.out.println("There are no save files");
    	}
    	return machine;
    }
}