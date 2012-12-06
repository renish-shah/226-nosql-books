/**
 * 
 */
package com.booksdata.application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.basho.riak.client.RiakException;
import com.basho.riak.client.cap.UnresolvedConflictException;
import com.basho.riak.client.convert.ConversionException;
import com.booksdata.dboperation.RiakDbOperation;
import com.booksdata.domain.AttributeWord;
import com.booksdata.domain.FileDetails;

/**
 * @author renis
 * 
 */
public class QueringBooksData {

	public static void main(String[] args) {

		QueringBooksData booksData = new QueringBooksData();
		System.out
				.println("Relevant Files in order of Most Relevant to Less Relevant");
		List<String> relevantFiles = booksData.getReleventFiles(args[0]);
		if (relevantFiles != null && relevantFiles.size() > 0) {
			for (String fileName : relevantFiles) {
				System.out.println("\n" + fileName);
			}
		} else {
			System.out.println("Sorry! Not any relevant files found");
		}
	}

	public List<String> getRankedListOfFiles(AttributeWord attributeWord) {
		List<FileDetails> listOfFiles = attributeWord.getListOfFileDetails();
		// double stdev=0;
		HashMap<Double, String> stdev = new HashMap<Double, String>();

		List<Double> stdevValues = new ArrayList<Double>();
		for (int i = 0; i < listOfFiles.size(); i++) {
			stdev.put(listOfFiles.get(i).getStdev(), listOfFiles.get(i)
					.getFileName());
			stdevValues.add(listOfFiles.get(i).getStdev());
		}
		Collections.sort(stdevValues);
		Collections.reverse(stdevValues);
		List<String> rankedFileNames = new ArrayList<String>();
		for (int i = 0; i < stdev.size(); i++) {
			rankedFileNames.add(stdev.get(stdevValues.get(i)));
		}
		stdev = null;
		stdevValues = null;
		return rankedFileNames;
	}

	public List<String> getReleventFiles(String wordName) {
		RiakDbOperation dbOperation = RiakDbOperation.getDBInstance();
		try {
			for (String alphabet : dbOperation.alphabetA2I) {
				if (wordName.startsWith(alphabet)) {
					AttributeWord attributeWord = dbOperation
							.retrievePojo(
									RiakDbOperation.TEST_BUCKET_A_I/* "TestBucket-A-I-Final" */,
									wordName);
					return getRankedListOfFiles(attributeWord);
				}
			}
			for (String alphabet : dbOperation.alphabetsJ2R) {
				if (wordName.startsWith(alphabet)) {
					AttributeWord attributeWord = dbOperation
							.retrievePojo(
									RiakDbOperation.TEST_BUCKET_J_R/* "TestBucket-A-I-Final" */,
									wordName);
					return getRankedListOfFiles(attributeWord);
				}
			}
			for (String alphabet : dbOperation.alphabetS2Z) {
				if (wordName.startsWith(alphabet)) {
					AttributeWord attributeWord = dbOperation
							.retrievePojo(
									RiakDbOperation.TEST_BUCKET_S_Z/* "TestBucket-A-I-Final" */,
									wordName);
					return getRankedListOfFiles(attributeWord);
				}
			}
		} catch (UnresolvedConflictException e) {
			e.printStackTrace();
		} catch (ConversionException e) {
			e.printStackTrace();
		} catch (RiakException e) {
			e.printStackTrace();
		} finally{
			dbOperation.closeDBProcess();
		}
		return null;
	}
}
