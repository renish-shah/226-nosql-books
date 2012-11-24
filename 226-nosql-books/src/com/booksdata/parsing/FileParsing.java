package com.booksdata.parsing;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class FileParsing {

	public HashMap<String,Integer> parse(File f)
	{
		HashMap<String,Integer> matrix = new HashMap<String,Integer>();
		try
		{
			FileInputStream fs = new FileInputStream(f);
			DataInputStream ds = new DataInputStream(fs);
			BufferedReader in = new BufferedReader(new InputStreamReader(ds));
			String line;
			
			//String words[];// = new String[50];
			int i = 0;
			int count = 0;
			line=in.readLine();
			line=in.readLine();
			line=in.readLine();
			line=in.readLine();
			line=in.readLine();
			line=in.readLine();
			line=in.readLine();
			line=in.readLine();
			line=in.readLine();
			String title = line.substring(7);
			System.out.println(title);
			line=in.readLine();
			line=in.readLine();
			String author = line.substring(8);
			System.out.println(author);
			line=in.readLine();
			line=in.readLine();
			line=in.readLine();
			String releaseDate = line.substring(14);
			System.out.println(releaseDate);
			
			while((line=in.readLine())!=null)
			{
				
				//i++;
				line=line.replaceAll("[\\.\\'\\-\\,\\?\\\"\\(\\)\\&\\\\;\\:\\s]", "|");
				line=line.replaceAll("[|*]", "|");
				//System.out.println("line "/* + i*/ +" is : " + line);
				//words = line.split("\\|");
				for(String word : line.split("\\|"))
				{
					//System.out.println(word);
					word = word.toLowerCase();
					try{
					count = (Integer)matrix.get(word);
					}
					catch(Exception ex)
					{
						//System.out.println("Word inserted");
						i++;
						//matrix.put(word, 0);
						count=0;
					}
					count++;
					matrix.put(word, count);
					
				}
			}
			System.out.println("i is: " + i);
			int ret = (Integer)matrix.get("ashamed");
			System.out.println("Ret is: " + ret);
			in.close();
			
		}
		catch(Exception e)
		{
			System.out.println("Error in parse");
			e.printStackTrace();
		}
	return matrix;
	}
}
