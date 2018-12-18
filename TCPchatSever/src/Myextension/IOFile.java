package Myextension;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class IOFile {
public static ArrayList<String> ReadFile(String name) throws IOException
{
	ArrayList<String> list=new ArrayList<String>();
	File file=new File("Client");
	if(!file.exists())
	{
		file.mkdir();
	}
	File f=new File(name);
	if(!f.exists())
	{
		f.createNewFile();
	}
	FileReader fileReader=new FileReader(f);
	 BufferedReader br = new BufferedReader(fileReader);
	 String textInALine;
	 while ((textInALine = br.readLine()) != null) {
		 if(textInALine=="\n") break;
		 list.add(textInALine);
     }
     br.close();
     fileReader.close();
     return list;
}
public static void WriteFile(String name,String context) throws IOException
{
	File file=new File("Client");
	if(!file.exists())
	{
		file.mkdir();
	}
	File f=new File(name);
	if(!f.exists())
	{
		f.createNewFile();
	}
	FileWriter fileWriter=new FileWriter(f,true);
	BufferedWriter bw = null;
	bw = new BufferedWriter(fileWriter);
	bw.write(context+"\n");
	bw.close();
	fileWriter.close();
}
public static void DeleteFile(String name)
{
		File file = new File(name);

		file.delete();

	}
}