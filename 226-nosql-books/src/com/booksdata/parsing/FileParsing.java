package com.booksdata.parsing;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import com.booksdata.domain.FileDetails;

public class FileParsing {

	static HashMap<String, HashMap<String, FileDetails>> words = new HashMap<String, HashMap<String, FileDetails>>();

	public  HashMap<String, HashMap<String, FileDetails>> parse(File f) {
	//public HashMap<String, HashMap<String, FileDetails>> parse(File[] listOfFiles) {
		int fileCount = 0;
		HashMap<String, Integer> matrix = new HashMap<String, Integer>();
		try {
			FileInputStream fs = new FileInputStream(f);
			DataInputStream ds = new DataInputStream(fs);
			BufferedReader in = new BufferedReader(new InputStreamReader(ds));
			String line;
			String filename = f.getName();
			System.out.println(filename);
			int i = 0;
			int pos = 0;
			int count = 0;
			while ((line = in.readLine()) != null) {
				String[] parts = line.trim()
						.split("[\\s,\\.:;\\-#~\\(\\)\\?\\!\\&\\*\\\"\\/\\'\\`\\[\\]]");
				// System.out.println("line "/* + i +" is : " + line);
				// words = line.split("\\|");

				for (String word : parts) 
				{
					if (word.equals("")) 
					{
					} 
					else 
					{
						word = word.toLowerCase();
						//System.out.println(word);
						 
						FileDetails details = new FileDetails();
						if (words.containsKey(word)) {
							details = words.get(word).get(filename);
							if(details==null)
							{
								words.get(word).put(filename, new FileDetails());
								words.get(word).get(filename).setPosition(new ArrayList<Integer>());
								details = words.get(word).get(filename);
								if(details==null)
								throw new Exception("details is null");
							}
							words.get(word).get(filename).getPosition().add(i);
							//details.getPosition().add(i);
							words.get(word).get(filename).setNoOfOccurance(words.get(word).get(filename).getNoOfOccurance()+1);
							//details.setNoOfOccurance(details.getNoOfOccurance() + 1);
							//}
						} else {
							HashMap<String, FileDetails> fileDetails = new HashMap<String, FileDetails>();
							details.setFileName(filename);
							details.setNoOfOccurance(1);
							details.getPosition().add(i);
							fileDetails.put(filename, details);
							words.put(word, fileDetails);
							fileDetails=null;
							details=null;
							//}
						}
					}
					i++;
				}
			}
			
			System.out.println("End of file " + filename);
			
			/*System.out.println("occurance of have in 11.txt");
			System.out.println(words.get("have").get("11.txt").getNoOfOccurance());
			System.out.println("occurance of have in 12.txt");
			System.out.println(words.get("have").get("12.txt").getNoOfOccurance());*/
			
			System.out.println("********************************************");
		} catch (Exception e) {
			System.out.println("Error in parse");
			e.printStackTrace();
		}
		//}
		return words;
	}
}
