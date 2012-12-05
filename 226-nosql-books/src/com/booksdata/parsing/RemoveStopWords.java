package com.booksdata.parsing;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.booksdata.domain.FileDetails;

public class RemoveStopWords {


	
	public HashMap<String, HashMap<String, FileDetails>> clean(HashMap<String, HashMap<String, FileDetails>> matrix)
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
		HashMap<String, HashMap<String, FileDetails>> tempMatrix = new HashMap<String, HashMap<String, FileDetails>>();
		for(String s : matrix.keySet())
		{
			 tempMatrix.put(s,matrix.get(s));
		}
		for (String word : tempMatrix.keySet())
		{
			if(word.length()<=2)
			{
				matrix.remove(word);
			}
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
