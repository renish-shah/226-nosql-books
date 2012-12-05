package com.booksdata.application;

import java.io.File;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.booksdata.dboperation.RiakDbOperation;
import com.booksdata.domain.AttributeWord;
import com.booksdata.domain.FileDetails;
import com.booksdata.parsing.FileParsing;
import com.booksdata.parsing.RemoveStopWords;

public class Main {

	RiakDbOperation dbOperation;

	public static void main(String args[]) {
		Main main = new Main();

		FileParsing parser = new FileParsing();

		HashMap<String, HashMap<String, FileDetails>> matrix = null;
		// SerializationDemo serializationDemo = new SerializationDemo();

		File folder = new File("E:/books226/test");
		File[] listOfFiles = folder.listFiles();

		int i = 1;
		boolean serializeFlag = false;
		boolean checkDBFlag = false;
		for (File f : listOfFiles) {

			// matrix = (HashMap<String, HashMap<String,
			// FileDetails>>)parser.parse(listOfFiles);
			matrix = (HashMap<String, HashMap<String, FileDetails>>) parser
					.parse(f, serializeFlag, checkDBFlag);
			// matrix = (HashMap<String, HashMap<String, FileDetails>>)
			// parser.parse(new File("E:/books226/11.txt"));
			// matrix = (HashMap<String, HashMap<String, FileDetails>>)
			// parser.parse(new File("E:/books226/15.txt"));
			// matrix = (HashMap<String, HashMap<String, FileDetails>>)
			// parser.parse(new File("E:/books226/16.txt"));
			// matrix = (HashMap<String, HashMap<String, FileDetails>>)
			// parser.parse(new File("E:/books226/20.txt"));

			RemoveStopWords rm = new RemoveStopWords();
			matrix = rm.clean(matrix);

			if (i % 10 == 0) {

				main.calculateStdDev(matrix);
				main.cnvrtMtrxToWrdInsertIntoDB(matrix);

				// serializationDemo.serializeMatrix(matrix);
				System.out.println(matrix.keySet());
				System.out.println(matrix.keySet().size());
				matrix = null;
				checkDBFlag = true;
				// serializeFlag=true;
			}
			i++;

		}

		// System.out.println("Matrix size is: " + matrix.size());
		// serializationDemo.deserializeMatrix();

		main.calculateStdDev(matrix);
		main.cnvrtMtrxToWrdInsertIntoDB(matrix);
		// matrix.get
		// System.out.println("Book with high rank is : "
		// + main.findBook("mimicry", matrix));

		// System.out.println("occurance of join in 11.txt");
		// System.out.println(matrix.get("doubt").get("11.txt").getPosition());

		// System.out.println("occurance of have in 12.txt");
		// System.out.println(matrix.get("have").get("12.txt").getNoOfOccurance());
		// System.out.println(matrix.get("have").get("15.txt").getPosition());
		// System.out.println(matrix.get("have").get("16.txt").getPosition());
		// System.out.println(matrix.get("have").get("20.txt").getPosition() );

	}

	public String findBook(String keyword,
			HashMap<String, HashMap<String, FileDetails>> matrix) {
		String book = null;
		HashMap<String, FileDetails> fileDetails = matrix.get(keyword);
		List<Integer> std = new ArrayList<Integer>();
		try {
			for (String s : fileDetails.keySet()) {
				std.add((int) fileDetails.get(s).getStdev());
			}
		} catch (NullPointerException ex) {
			System.out.println("null in key");
		}

		// Integer i = Collections.max(std);

		Map.Entry<String, FileDetails> maxEntry = null;

		for (Map.Entry<String, FileDetails> entry : fileDetails.entrySet()) {
			Double a = entry.getValue().getStdev();
			if (maxEntry == null
					|| a.compareTo(maxEntry.getValue().getStdev()) > 0) {
				maxEntry = entry;
			}
		}
		book = maxEntry.getKey();

		return book;
	}

	public void calculateStdDev(
			HashMap<String, HashMap<String, FileDetails>> matrix) {

		System.out.println("=== In calculateStdDev ===");

		for (String s : matrix.keySet()) {
			double ave = 0;
			double sd = 0;

			for (String f : matrix.get(s).keySet()) {
				ArrayList<Integer> position = matrix.get(s).get(f)
						.getPosition();
				for (Integer n : position)
					ave += n;
				ave /= position.size();

				for (Integer n : position)
					sd += Math.pow((ave - n), 2);
				sd /= position.size();
				sd = Math.sqrt(sd);
				// System.out.println("std dev is " + sd);
				matrix.get(s).get(f).setStdev(sd);
			}
		}
		// System.out.println("Std dev is : " + Math.sqrt(sd));

	}

	public void cnvrtMtrxToWrdInsertIntoDB(
			HashMap<String, HashMap<String, FileDetails>> matrix) {
		AttributeWord attributeWord = new AttributeWord();

		System.out.println("=== In cnvrtMtrxToWrdInsertIntoDB ===");

		Set<String> words = matrix.keySet();

		RiakDbOperation dbOperation = RiakDbOperation.getDBInstance();
		
		
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
			// fileDetails=null;
			
			dbOperation.storeUsingProtoBuff(attributeWord);

		}
		attributeWord = null;
		dbOperation.closeDBProcess();
		// return attributeWord;
	}

}
