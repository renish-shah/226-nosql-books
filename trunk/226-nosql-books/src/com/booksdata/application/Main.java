package com.booksdata.application;

import java.io.File;
import java.util.HashMap;

import com.booksdata.domain.FileDetails;
import com.booksdata.parsing.FileParsing;
import com.booksdata.parsing.RemoveStopWords;

public class Main {

	public static void main(String args[])
	{
		FileParsing parser = new FileParsing();
		
		HashMap<String, HashMap<String, FileDetails>> matrix;
		
		File folder = new File("E:/books226/");
		File[] listOfFiles = folder.listFiles(); 

		//matrix = (HashMap<String, HashMap<String, FileDetails>>) parser.parse(listOfFiles);
		matrix = (HashMap<String, HashMap<String, FileDetails>>) parser.parse(new File("E:/books226/1093.txt"));
		matrix = (HashMap<String, HashMap<String, FileDetails>>) parser.parse(new File("E:/books226/15.txt"));
		matrix = (HashMap<String, HashMap<String, FileDetails>>) parser.parse(new File("E:/books226/16.txt"));
		matrix = (HashMap<String, HashMap<String, FileDetails>>) parser.parse(new File("E:/books226/20.txt"));
		
		RemoveStopWords rm = new RemoveStopWords();
		matrix = rm.clean(matrix);
		
		//System.out.println("occurance of have in 11.txt");
		//System.out.println(matrix.get("have").get("11.txt").getNoOfOccurance());
		//System.out.println("occurance of have in 12.txt");
		//System.out.println(matrix.get("have").get("12.txt").getNoOfOccurance());
		System.out.println(matrix.get("have").get("15.txt").getPosition());
		System.out.println(matrix.get("have").get("16.txt").getPosition());
		System.out.println(matrix.get("have").get("20.txt").getPosition()
				);
		
		
		//int ret = (Integer)matrix.get("alice");
		//System.out.println("Ret is: " + ret);
		
		/*try{
		File swf = new File("resources/stopwords-long.txt");
		StopWords swords = new StopWordsFile(swf);
		File dir = new File("E:/books226/11.txt");
		Loader ldr = new Loader(swords);
		List<Document> docs = ldr.load(dir);
		//Assert.assertEquals("mismatch in number of files", 1, docs.size());

		for (Document d : docs)
			System.out.println(d + "\n");
		}
		catch(Exception e)
		{
		e.printStackTrace();
		}
		*/
		
	}
}
