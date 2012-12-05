package com.booksdata.dboperation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.basho.riak.client.IRiakClient;
import com.basho.riak.client.RiakException;
import com.basho.riak.client.RiakFactory;
import com.basho.riak.client.RiakRetryFailedException;
import com.basho.riak.client.bucket.Bucket;
import com.basho.riak.client.bucket.FetchBucket;
import com.basho.riak.client.cap.UnresolvedConflictException;
import com.basho.riak.client.convert.ConversionException;
import com.basho.riak.client.query.MapReduceResult;
import com.basho.riak.client.query.functions.NamedJSFunction;
import com.basho.riak.client.query.indexes.BinIndex;
import com.basho.riak.client.query.indexes.IntIndex;
import com.booksdata.domain.AttributeWord;
import com.booksdata.domain.FileDetails;
import com.booksdata.domain.JsonObject;
import com.sun.org.apache.xml.internal.security.c14n.helper.AttrCompare;

public class RiakDbOperation {

	private static final String TEST_BUCKET = "TestBucket5";
	private static final String HTTP_168_62_211_85_8098_RIAK = "http://168.62.211.85:8098/riak";

	public void storeUsingPB(AttributeWord attributeWord) {
		try {

			IRiakClient riakClient = RiakFactory
					.httpClient(HTTP_168_62_211_85_8098_RIAK);
			// riakClient.createBucket(TEST_BUCKET);
			Bucket myBucket = riakClient.fetchBucket(TEST_BUCKET).execute();

			myBucket.store(attributeWord.getWordName(), attributeWord).execute();

			// AttributeWord word=fillPojo("mother", "123.txt", 9,"34");
			// myBucket.store(word.getWordName(), word).execute();
			//
			// word=fillPojo("father", "345.txt", 10,"35");
			// myBucket.store(word.getWordName(), word).execute();
			//
			// word=fillPojo("sister", "678.txt", 11,"36");
			// myBucket.store(word.getWordName(), word).execute();

			riakClient.shutdown();

		} catch (Exception e) {
			System.out.println("Exception: while inserting into RiakDB :"+e);
		}
	}

	public AttributeWord lookupObjectByKey(final String bucket,
			final String key, final Class<AttributeWord> kclass) {
		try {
			IRiakClient riakClient = RiakFactory
					.httpClient(HTTP_168_62_211_85_8098_RIAK);

			return riakClient.fetchBucket(bucket).execute().fetch(key, kclass)
					.execute();

		} catch (final RiakRetryFailedException e) {
			throw new RuntimeException(e);
		} catch (RiakException e) {
			e.printStackTrace();
		}
		return null;

	}

	public List<String> fetchIndex(final String bucket, final String indexName,
			final String indexValue) {
		try {
			IRiakClient riakClient = RiakFactory
					.httpClient(HTTP_168_62_211_85_8098_RIAK);

			Bucket abc = riakClient.fetchBucket(bucket).execute();
			return abc.fetchIndex(BinIndex.named(indexName))
					.withValue(indexValue).execute();
		} catch (RiakException e) {
			System.out.println("Exception :" + e);
			throw new RuntimeException(e);
		}

		// Collection<String> collection = results.getResult(String.class);
	}

	// To execute a Riak search on a given bucket, Where parameter bucket is the
	// bucket name and the criteria is the search criteria like "type=Folder" or
	// "(type=Folder AND name=Hello)".

	public Collection<JsonObject> search(final String bucket,
			final String criteria) {

		try {
			IRiakClient riakClient = RiakFactory
					.httpClient(HTTP_168_62_211_85_8098_RIAK);

			final MapReduceResult mapReduceResult = riakClient
					.mapReduce(bucket, criteria)
					.addMapPhase(new NamedJSFunction("Riak.mapValuesJson"))
					.execute();
			return mapReduceResult.getResult(JsonObject.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) throws RiakException {

		RiakDbOperation dbOperation = new RiakDbOperation();
		List<String> abc = dbOperation.fetchIndex(TEST_BUCKET, "uri", "11");
		// dbOperation.storeUsingPB();
		System.out.println("Success");

		// dbOperation.retrievePojo();
		// dbOperation.appendDetailsToWord();
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

	public AttributeWord fillPojo(String wordName, String fileName,
			int noOfOccurance, String wordCount) {
		AttributeWord word = new AttributeWord();
		word.setWordName(wordName);

		FileDetails fileDetails1 = new FileDetails();
		fileDetails1.setFileName(fileName);
		fileDetails1.setMean(214.5);
		fileDetails1.setNoOfOccurance(noOfOccurance);

		ArrayList<Integer> positions = new ArrayList<Integer>();
		positions.add(23);
		positions.add(28);
		positions.add(233);

		fileDetails1.setPosition(positions);
		fileDetails1.setStdev(126.5);

		ArrayList<FileDetails> allFileDetails = new ArrayList<FileDetails>();
		allFileDetails.add(fileDetails1);

		word.setListOfFileDetails(allFileDetails);
		//word.setWordCount(wordCount);

		return word;

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

		word.setListOfFileDetails(allFileDetails);

		IRiakClient riakClient = RiakFactory
				.httpClient(HTTP_168_62_211_85_8098_RIAK);
		Bucket myBucket = riakClient.fetchBucket(TEST_BUCKET).execute();
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
					.httpClient(HTTP_168_62_211_85_8098_RIAK);
			Bucket myBucket = riakClient.fetchBucket(TEST_BUCKET).execute();
			AttributeWord word = myBucket.fetch("christmas",
					AttributeWord.class).execute();

			// for(String k : bucket.keys()) {
			// // do your key thing
			// }

			if (word != null) {
				List<FileDetails> files = word.getListOfFileDetails();
				files.add(addFileDetails());
				word.setListOfFileDetails(files);
				myBucket.store(word.getWordName(), word).execute();
			}

			System.out.println("" + word.getWordName());

		} catch (RiakException e) {
			System.out.println("RiakException :" + e);
			e.printStackTrace();
		}
	}

	public void fetchIndex() {

		try {

			IRiakClient riakClient = RiakFactory
					.httpClient(HTTP_168_62_211_85_8098_RIAK);
			Bucket myBucket = riakClient.fetchBucket(TEST_BUCKET).execute();

			myBucket.fetchIndex(IntIndex.named("noOfOccurance"));

			AttributeWord word = myBucket.fetch("christmas",
					AttributeWord.class).execute();

			System.out.println("" + word.getWordName());

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void retrievePojo() throws UnresolvedConflictException,
			ConversionException, RiakException {

		IRiakClient riakClient = RiakFactory
				.httpClient(HTTP_168_62_211_85_8098_RIAK);
		Bucket myBucket = riakClient.fetchBucket(TEST_BUCKET).execute();
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