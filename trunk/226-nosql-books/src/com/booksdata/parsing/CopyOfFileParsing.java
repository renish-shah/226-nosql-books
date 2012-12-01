package com.booksdata.parsing;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.booksdata.domain.AttributeWord;
import com.booksdata.domain.FileDetails;

public class CopyOfFileParsing {

	/*
	 * public static void main(String args[]) { parse(new
	 * File("E:/books226/11.txt")); }
	 */
	static HashMap<String, FileDetails> words = new HashMap<String, FileDetails>();

	public List<AttributeWord> parse(File f) {
		// List<AttributeWord> words = new ArrayList<AttributeWord>();
		HashMap<String, Integer> matrix = new HashMap<String, Integer>();
		try {
			FileInputStream fs = new FileInputStream(f);
			DataInputStream ds = new DataInputStream(fs);
			BufferedReader in = new BufferedReader(new InputStreamReader(ds));
			String line;

			// String words[];// = new String[50];
			int i = 0;
			int pos = 0;
			int count = 0;
			line = in.readLine();
			line = in.readLine();
			line = in.readLine();
			line = in.readLine();
			line = in.readLine();
			line = in.readLine();
			line = in.readLine();
			line = in.readLine();
			line = in.readLine();
			String title = line.substring(7);
			System.out.println(title);
			line = in.readLine();
			line = in.readLine();
			String author = line.substring(8);
			System.out.println(author);
			line = in.readLine();
			line = in.readLine();
			line = in.readLine();
			String releaseDate = line.substring(14);
			System.out.println(releaseDate);

			//ArrayList<AttributeWord> listOfAttributeWords = new ArrayList<AttributeWord>();
			
			// AttributeWord attributeWord=new AttributeWord();
			// ArrayList<FileDetails> listOfFiles=new ArrayList<FileDetails>();
			// FileDetails fileDetails=new FileDetails();
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
						System.out.println(word);

						FileDetails details = new FileDetails();
						if (words.containsKey(word)) {
							// details.setFileName("11");
							details = words.get(word);
							details.getPosition().add(i);
							details.setNoOfOccurance(details.getNoOfOccurance() + 1);
						} else {
							details.setFileName("11");
							details.setNoOfOccurance(1);
							details.getPosition().add(i);
							words.put(word, details);
						}

						/*
						 * third attempt AttributeWord attributeWord=new
						 * AttributeWord(); attributeWord.setWordName(word);
						 * if(listOfAttributeWords.contains(attributeWord)){}
						 * FileDetails fileDetails=new FileDetails();
						 * ArrayList<FileDetails> listOfFiles=new
						 * ArrayList<FileDetails>();
						 * attributeWord.setWordName(word);
						 * fileDetails.setFileName("11");
						 * fileDetails.setNoOfOccurance(1);
						 * fileDetails.setPosition(i);
						 * listOfFiles.add(fileDetails);
						 * attributeWord.setListOfFileDetails(listOfFiles);
						 * listOfAttributeWords.add(attributeWord); i++;
						 */
					}
					i++;
				}
			}

			Iterator it = words.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, FileDetails> pairs = (Map.Entry<String, FileDetails>) it
						.next();
				System.out.println(pairs.getKey() + " = "
						+ pairs.getValue().getPosition());
			}

			/*
			 * part 2 starts {
			 * 
			 * AttributeWord attributeWord=new AttributeWord(); // FileDetails
			 * fileDetails=new FileDetails();
			 * 
			 * //i++;
			 * line=line.replaceAll("[\\.\\'\\-\\,\\?\\\"\\(\\)\\&\\\\;\\:\\s]",
			 * "|"); line=line.replaceAll("[|*]", "|");
			 * //System.out.println("line "/* + i +" is : " + line); //words =
			 * line.split("\\|");
			 * 
			 * ListOfWords listOfWords=new ListOfWords(); for(String word :
			 * line.split("\\|")) //for(int j=0;j<100;j++) {
			 * 
			 * 
			 * System.out.println(word); word = word.toLowerCase();
			 * attributeWord = new AttributeWord();
			 * attributeWord.setWordName(word); List<String> listOfString = new
			 * ArrayList<String>(); for(AttributeWord w : listOfAttributeWords)
			 * { listOfString.add(w.getWordName()); }
			 * if(listOfString.contains(attributeWord.getWordName()))
			 * //if(ListOfWords.getListOfWords().contains(word)) { int index =
			 * listOfString.indexOf(attributeWord.getWordName());
			 * ///*************doubt //int index =
			 * listOfAttributeWords.indexOf(attributeWord); attributeWord =
			 * listOfAttributeWords.get(index);
			 * attributeWord.getListOfFileDetails
			 * ().get(0).setNoOfOccurance(attributeWord
			 * .getListOfFileDetails().get(0).getNoOfOccurance()+1); } else {
			 * 
			 * fileDetails.setFileName("314.txt"); fileDetails.setMean(546.70);
			 * fileDetails.setNoOfOccurance(1);
			 * 
			 * listOfFiles.add(fileDetails); System.out.println(word);
			 * attributeWord.setWordName(word);
			 * 
			 * attributeWord.setListOfFileDetails(listOfFiles);
			 * listOfAttributeWords.add(attributeWord);
			 * 
			 * } } }part 2 ends
			 */

			// System.out.println(word);

			/*
			 * 
			 * List<FileDetails> listOfFileDetails=new ArrayList<FileDetails>();
			 * listOfFileDetails.add(fileDetails);
			 * 
			 * attributeWord.setListOfFileDetails(listOfFileDetails);
			 * 
			 * 
			 * listOfAttributeWords.add(attributeWord); attributeWord=null;
			 * fileDetails=null; }
			 * listOfWords.setListOfWords(listOfAttributeWords);
			 * listOfAttributeWords=null;
			 * 
			 * 
			 * fileDetails.setFileName("fd");
			 * fileDetails.setNoOfOccurance(fileDetails.getNoOfOccurance()+1);
			 * listOfFiles.add(fileDetails);
			 * attributeWord.setFileDetails(listOfFiles);
			 * 
			 * listOfWords2.add(listOfWords);
			 * 
			 * listOfWords.setListOfWords(listOfWords2);
			 * 
			 * AttributeWord w = new AttributeWord(); w.se try{ words.indexOf(o)
			 * words.get(index) w = w.getFromList(word);
			 * 
			 * List<FileDetails> qwe = w.getFileDetails() ; int ind =
			 * qwe.indexOf(w); //words.indexOf(AttributeWord); count =
			 * qwe.get(ind).getNoOfOccurance(); count ++; } catch(Exception ex)
			 * { w.addToList(word); //System.out.println("Word inserted"); i++;
			 * //matrix.put(word, 0); count=0; } count++; matrix.put(word,
			 * count);
			 * 
			 * } } System.out.println("i is: " + i); int ret =
			 * (Integer)matrix.get("ashamed"); System.out.println("Ret is: " +
			 * ret); in.close();
			 */
		} catch (Exception e) {
			System.out.println("Error in parse");
			e.printStackTrace();
		}
		// return matrix;
		// return attributeWords;
		return null;
	}
}
