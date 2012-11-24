package com.booksdata.parsing;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RemoveStopWords {


	
	public HashMap<String,Integer> clean(HashMap<String,Integer> matrix)
	{
		List<String> stopWords = new ArrayList<String>();
		try
		{
		FileInputStream fs = new FileInputStream("resources/stopwords-long.txt");
		DataInputStream ds = new DataInputStream(fs);
		BufferedReader in = new BufferedReader(new InputStreamReader(ds));
		String line="";
		while((line=in.readLine())!=null)
		{
			//System.out.println(line);
			stopWords.add(line);
			matrix.remove(line);
		}
		}
		catch (Exception e)
		{
			System.out.println("Error in getStopWords");
			e.printStackTrace();
		}
		
		return matrix;
	}
}
