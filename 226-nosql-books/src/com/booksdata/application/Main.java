package com.booksdata.application;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.booksdata.dboperation.RiakDbOperation;
import com.booksdata.domain.AttributeWord;
import com.booksdata.domain.FileDetails;
import com.booksdata.parsing.FileParsing;
import com.booksdata.parsing.RemoveStopWords;

public class Main {

	public static void main(String args[]) {
		FileParsing parser = new FileParsing();

		HashMap<String, HashMap<String, FileDetails>> matrix;

		File folder = new File("E:/books226/");
		File[] listOfFiles = folder.listFiles();

		//matrix = (HashMap<String, HashMap<String, FileDetails>>)parser.parse(listOfFiles);
		matrix = (HashMap<String, HashMap<String, FileDetails>>) parser.parse(new File("E:/books226/11.txt"));
		// matrix = (HashMap<String, HashMap<String, FileDetails>>)
		// parser.parse(new File("E:/books226/15.txt"));
		// matrix = (HashMap<String, HashMap<String, FileDetails>>)
		// parser.parse(new File("E:/books226/16.txt"));
		// matrix = (HashMap<String, HashMap<String, FileDetails>>)
		// parser.parse(new File("E:/books226/20.txt"));

		RemoveStopWords rm = new RemoveStopWords();
		matrix = rm.clean(matrix);

		//Main main = new Main();
		//main.cnvrtMtrxToWrd(matrix);
		// matrix.get

		System.out.println("occurance of join in 11.txt");
		System.out.println(matrix.get("doubt").get("11.txt").getPosition());
		
		double ave = 0;
		double sd = 0;
		ArrayList<Integer> position = matrix.get("doubt").get("11.txt").getPosition();
		for (Integer n : position)
			ave += n;
		ave /= position.size();

		for (Integer n : position)
			sd += Math.pow((ave - n), 2);
		sd /= position.size();

		System.out.println("Std dev is : " + Math.sqrt(sd));
		// System.out.println("occurance of have in 12.txt");
		// System.out.println(matrix.get("have").get("12.txt").getNoOfOccurance());
		// System.out.println(matrix.get("have").get("15.txt").getPosition());
		// System.out.println(matrix.get("have").get("16.txt").getPosition());
		// System.out.println(matrix.get("have").get("20.txt").getPosition() );

		// int ret = (Integer)matrix.get("alice");
		// System.out.println("Ret is: " + ret);

		/*
		 * try{ File swf = new File("resources/stopwords-long.txt"); StopWords
		 * swords = new StopWordsFile(swf); File dir = new
		 * File("E:/books226/11.txt"); Loader ldr = new Loader(swords);
		 * List<Document> docs = ldr.load(dir);
		 * //Assert.assertEquals("mismatch in number of files", 1, docs.size());
		 * 
		 * for (Document d : docs) System.out.println(d + "\n"); }
		 * catch(Exception e) { e.printStackTrace(); }
		 */

	}

	public void cnvrtMtrxToWrd(
			HashMap<String, HashMap<String, FileDetails>> matrix) {
		AttributeWord attributeWord = new AttributeWord();

		Set<String> words = matrix.keySet();

		RiakDbOperation dbOperation = new RiakDbOperation();
		for (String wordName : words) {
			List<FileDetails> listOfFileDetails = new ArrayList<FileDetails>();
			HashMap<String, FileDetails> matrixNew = matrix.get(wordName);
			Set<String> fileNames = matrixNew.keySet();
			FileDetails fileDetails;
			for (String fileName : fileNames) {
				fileDetails = matrixNew.get(fileName);
				listOfFileDetails.add(fileDetails);
			}
			attributeWord.setWordName(wordName);
			attributeWord.setListOfFileDetails(listOfFileDetails);
			listOfFileDetails = null;
			matrixNew = null;
			fileNames = null;
			//fileDetails=null;

			dbOperation.storeUsingPB(attributeWord);
		}
		// return attributeWord;
	}

}
