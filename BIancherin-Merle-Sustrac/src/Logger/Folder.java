package Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Folder {

	private long size;
	private String Name;
	private String Date;
	private Message log;
	private String path;
	private BufferedWriter writer;
	private File file;
	private int i;

	public Folder(long size, String Date) { // String path

		this.size = size;
		Name = "log" + i +"_"+GetDate()+ ".dat";
		this.Date = Date;
		//recuperation du path des logs dans le fichier properties (si present)
		path="./";
		if(PropertiesFiles.propertiePresent("path"))
		{path = PropertiesFiles.displayOnePropertie("path");}
		
		//Empeche la création de fichier de log si affichage en console
		if(!(PropertiesFiles.propertiePresent("cible")&&
				PropertiesFiles.displayOnePropertie("cible").equals("CONSOLE")))
		{	
			try {				
				file = new File(path+Name);
				writer = new BufferedWriter(new FileWriter(file, true));			
				// writer.write(log);	
				// writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public long getSize() {

		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public void UpdateFolder(Message log) {
				
		//recuperation de la cible des logs dans le fichier properties
		String target="ROTATE";
		if(PropertiesFiles.propertiePresent("cible"))
		{target = PropertiesFiles.displayOnePropertie("cible");}
		if (file.length() > this.size & file.exists() & target.equals("ROTATE")) {
			System.out.println("Size exceeded! New file created");
			i = i + 1;
			setName("log" + i+"_"+GetDate()+".dat");
			

			try {
				file = new File(path+Name);
				//crée un sous dossier contenu dans le path si non existant
				if(!file.exists())
				{
					boolean result = false;
					try{
						file.mkdir();
						result=true;
					}
					catch(SecurityException se){
						//do something
					}
					if(!result)
					{System.out.println("Can't create the DIR");}
				}
				writer = new BufferedWriter(new FileWriter(file));
				// normalement si le fichier n'existe pas, il est crée à la
				// racine du projet

				writer.write("" + log);
				writer.newLine();

				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		try {
			writer = new BufferedWriter(new FileWriter(new File(path+Name), true));
			// normalement si le fichier n'existe pas, il est crée à la racine
			// du projet
			writer.newLine();
			writer.write("" + log);
			writer.newLine();

			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	private static String GetDate() {
		String format = "yyyy_MM_dd h_mm_ss";
		java.text.SimpleDateFormat formater = new java.text.SimpleDateFormat(
				format);
		java.util.Date date = new java.util.Date();
		System.out.println(formater.format(date));

		return formater.format(date);
	}

}
