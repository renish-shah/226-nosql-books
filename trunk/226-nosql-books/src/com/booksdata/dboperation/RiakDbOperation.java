package com.booksdata.dboperation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.basho.riak.client.IRiakClient;
import com.basho.riak.client.RiakException;
import com.basho.riak.client.RiakFactory;
import com.basho.riak.client.bucket.Bucket;
import com.basho.riak.client.cap.UnresolvedConflictException;
import com.basho.riak.client.convert.ConversionException;
import com.booksdata.domain.AttributeWord;
import com.booksdata.domain.FileDetails;

public class RiakDbOperation {

	private static final String HTTP_168_62_201_239_8098_RIAK = "http://168.62.201.239:8098/riak";

	public static void main(String[] args) throws RiakException {

		RiakDbOperation dbOperation = new RiakDbOperation();
		dbOperation.retrievePojo();
		// dbOperation.storePojo();

		/*
		 * // RiakClient riak = new
		 * RiakClient("http://168.62.201.239:8098/riak");
		 * com.basho.riak.client.http.RiakClient riakClient = new
		 * com.basho.riak.client.http.RiakClient(
		 * HTTP_168_62_201_239_8098_RIAK); // Build an object: // RiakObject o =
		 * new RiakObject("bucket", "key", data.getBytes());
		 * com.basho.riak.client.http.RiakObject riakObject = new
		 * com.basho.riak.client.http.RiakObject( "TestBucket", "name",
		 * "anuj".getBytes());
		 * 
		 * StoreResponse response = riakClient.store(riakObject);
		 * System.out.println("Response:" + response);
		 * System.out.println("Status: " + response.isSuccess());
		 */
		// IRiakClient riakClient;
		// // com.basho.riak.client.http.RiakClient client = new
		// com.basho.riak.client.http.RiakClient(
		// // "http://168.62.201.239:8098");
		// String myData = "data";
		// riakClient = RiakFactory.httpClient("http://168.62.201.239:8098");
		// Bucket myBucket = riakClient.fetchBucket("TestBucket").execute();
		// myBucket.store("TestKey", myData);
		// riakClient.shutdown();

	}

	public void storePojo() throws UnresolvedConflictException,
			ConversionException, RiakException {

		AttributeWord word = new AttributeWord();
		word.setWordName("christmas");

		FileDetails fileDetails1 = new FileDetails();
		fileDetails1.setFileName("514.txt");
		fileDetails1.setMean(214.5);
		fileDetails1.setNoOfOccurance(4);

		ArrayList<Integer> positions = new ArrayList<Integer>();
		positions.add(23);
		positions.add(28);
		positions.add(233);

		fileDetails1.setPosition(positions);
		fileDetails1.setStdev(126.5);

		ArrayList<FileDetails> allFileDetails = new ArrayList<FileDetails>();
		allFileDetails.add(fileDetails1);

		word.setFileDetails(allFileDetails);

		IRiakClient riakClient = RiakFactory
				.httpClient(HTTP_168_62_201_239_8098_RIAK);
		Bucket myBucket = riakClient.fetchBucket("TestBucket").execute();
		myBucket.store(word.getWordName(), word).execute();

		riakClient.shutdown();
	}

	public FileDetails addFileDetails() {

		FileDetails fileDetails = new FileDetails();
		fileDetails.setFileName("317.txt");
		fileDetails.setMean(234.5);
		fileDetails.setNoOfOccurance(8);

		ArrayList<Integer> positions = new ArrayList<Integer>();
		positions.add(24);
		positions.add(67);
		positions.add(654);

		fileDetails.setPosition(positions);
		fileDetails.setStdev(657.7);

		return fileDetails;

	}

	public void appendDetailsToWord() {

		try {
			IRiakClient riakClient = RiakFactory
					.httpClient(HTTP_168_62_201_239_8098_RIAK);
			Bucket myBucket = riakClient.fetchBucket("TestBucket").execute();
			AttributeWord word = myBucket.fetch("christmas",
					AttributeWord.class).execute();

			// for(String k : bucket.keys()) {
			// // do your key thing
			// }

			if (word != null) {
				List<FileDetails> files = word.getFileDetails();
				files.myBucket.store(word.getWordName(), word).execute();
			}

			System.out.println("" + word.getWordName());

		} catch (RiakException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void retrievePojo() throws UnresolvedConflictException,
			ConversionException, RiakException {

		IRiakClient riakClient = RiakFactory
				.httpClient(HTTP_168_62_201_239_8098_RIAK);
		Bucket myBucket = riakClient.fetchBucket("TestBucket").execute();
		AttributeWord word = myBucket.fetch("christmas", AttributeWord.class)
				.execute();

		System.out.println("" + word.getWordName());
		// http://renish.cloudapp.net:8098/riak/TestBucket?keys=true

		// Query: http://renish.cloudapp.net:8098/riak/TestBucket/TestKeyBar/
		// Data: {"foo":"TestFoo1","bar":"testBar1","foobar":23}

		// AttributeWord word =
		// myBucket.fetch("foo:testFoo1",AttributeWord.class).execute();

		// MapReduceResult result = riakClient.mapReduce("TestBucket",
		// "foo:(TestFoo1 OR TestFoo2)").addMapPhase(new
		// NamedJSFunction("Riak.mapValuesJson"), true).execute();
		// System.out.println(""+result.toString());

		// System.out.println("bar :" + word.bar);
		// System.out.println("foo :" + word.foo);
		// System.out.println("foobar :" + word.foobar);

		riakClient.shutdown();
	}

}