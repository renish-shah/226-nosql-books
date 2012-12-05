package com.booksdata.dboperation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.basho.riak.client.IRiakClient;
import com.basho.riak.client.RiakException;
import com.basho.riak.client.RiakFactory;
import com.basho.riak.client.RiakRetryFailedException;
import com.basho.riak.client.bucket.Bucket;
import com.basho.riak.client.cap.UnresolvedConflictException;
import com.basho.riak.client.convert.ConversionException;
import com.basho.riak.client.query.MapReduceResult;
import com.basho.riak.client.query.functions.NamedJSFunction;
import com.basho.riak.client.query.indexes.BinIndex;
import com.basho.riak.client.query.indexes.IntIndex;
import com.booksdata.domain.AttributeWord;
import com.booksdata.domain.FileDetails;
import com.booksdata.domain.JsonObject;

public class RiakDbOperation {

//	private static final int PORT = 8098;
//	private static final String HOST = "168.62.211.85";
	public static final String TEST_BUCKET_A_I = "TestBucket-A-I";
	public static final String TEST_BUCKET_J_R = "TestBucket-J-R";
	public static final String TEST_BUCKET_S_Z = "TestBucket-S-Z";
	public static final String HTTP_168_62_211_85_8098_RIAK = "http://168.62.211.85:8098/riak";
	public List<String> alphabetA2I = new ArrayList<String>();
	public List<String> alphabetsJ2R = new ArrayList<String>();
	public List<String> alphabetS2Z = new ArrayList<String>();
	Bucket bucket_A2I;
	Bucket bucket_J2R;
	Bucket bucket_S2Z;
	IRiakClient riakClient;

	public RiakDbOperation() {
		initDBProcess();
	}

	public void storeAtoI(AttributeWord attributeWord, String testBucket) {

	}

	public void initDBProcess() {

		alphabetA2I.add("a");
		alphabetA2I.add("b");
		alphabetA2I.add("c");
		alphabetA2I.add("d");
		alphabetA2I.add("e");
		alphabetA2I.add("f");
		alphabetA2I.add("g");
		alphabetA2I.add("h");
		alphabetA2I.add("i");

		alphabetsJ2R.add("j");
		alphabetsJ2R.add("k");
		alphabetsJ2R.add("l");
		alphabetsJ2R.add("m");
		alphabetsJ2R.add("n");
		alphabetsJ2R.add("o");
		alphabetsJ2R.add("p");
		alphabetsJ2R.add("q");
		alphabetsJ2R.add("r");

		alphabetS2Z.add("s");
		alphabetS2Z.add("t");
		alphabetS2Z.add("u");
		alphabetS2Z.add("v");
		alphabetS2Z.add("w");
		alphabetS2Z.add("x");
		alphabetS2Z.add("y");
		alphabetS2Z.add("z");

		try {
			riakClient = RiakFactory.httpClient(HTTP_168_62_211_85_8098_RIAK);

			bucket_A2I = riakClient.fetchBucket(TEST_BUCKET_A_I).execute();
			bucket_J2R = riakClient.fetchBucket(TEST_BUCKET_J_R).execute();
			bucket_S2Z = riakClient.fetchBucket(TEST_BUCKET_S_Z).execute();

		} catch (RiakException e) {
			System.out.println("RiakException while init:" + e);
			e.printStackTrace();
		}
	}

	public void closeDBProcess() {
		riakClient.shutdown();
	}

	public void storeUsingProtoBuff(AttributeWord attributeWord) {
		try {
			// RiakClient riakClient2 = new RiakClient(HOST, PORT);
			// riakClient.createBucket(TEST_BUCKET);
			boolean loopDontEnter = false;
			for (String alphabet : alphabetA2I) {
				if (attributeWord.getWordName().startsWith(alphabet)) {
					bucket_A2I
							.store(attributeWord.getWordName(), attributeWord)
							.execute();
					loopDontEnter = true;
				}
			}

			if (loopDontEnter == false) {
				for (String alphabet : alphabetsJ2R) {
					if (attributeWord.getWordName().startsWith(alphabet)) {
						bucket_J2R.store(attributeWord.getWordName(),
								attributeWord).execute();
						loopDontEnter = true;
					}
				}
			}
			if (loopDontEnter == false) {

				for (String alphabet : alphabetS2Z) {
					if (attributeWord.getWordName().startsWith(alphabet)) {
						bucket_S2Z.store(attributeWord.getWordName(),
								attributeWord).execute();
						loopDontEnter = true;
					}
				}
			}
			// AttributeWord word=fillPojo("mother", "123.txt", 9,"34");
			// myBucket.store(word.getWordName(), word).execute();
			// word=fillPojo("father", "345.txt", 10,"35");
			// myBucket.store(word.getWordName(), word).execute();
			// word=fillPojo("sister", "678.txt", 11,"36");
			// myBucket.store(word.getWordName(), word).execute();

		} catch (Exception e) {
			System.out.println("Exception: while inserting into RiakDB :" + e);
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

	public AttributeWord fetchIndex(final String bucket, final String indexName,
			final String indexValue) {
		try {
			Bucket myBucket = riakClient.fetchBucket(bucket).execute();
			return (AttributeWord) myBucket.fetchIndex(BinIndex.named(indexName))
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
		dbOperation.emptyBucket(TEST_BUCKET_A_I);
		dbOperation.emptyBucket(TEST_BUCKET_J_R);
		dbOperation.emptyBucket(TEST_BUCKET_S_Z);
		
		
		AttributeWord attributeWord = new AttributeWord();
		attributeWord.setWordName("iRenish");
		
		FileDetails fileDetails=new FileDetails();
		fileDetails.setNoOfOccurance(20);
		
		List<FileDetails> details=new ArrayList<FileDetails>();
		details.add(fileDetails);
		
		attributeWord.setListOfFileDetails(details);

		//dbOperation.storeUsingPB(attributeWord);
		//dbOperation.retrievePojo(TEST_BUCKET_A_I, "iRenish");
		AttributeWord abc = dbOperation.fetchIndex(TEST_BUCKET_A_I, "uri",
				"20");
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
		// word.setWordCount(wordCount);

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
		Bucket myBucket = riakClient.fetchBucket(TEST_BUCKET_A_I).execute();
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
			Bucket myBucket = riakClient.fetchBucket(TEST_BUCKET_A_I).execute();
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
			Bucket myBucket = riakClient.fetchBucket(TEST_BUCKET_A_I).execute();

			myBucket.fetchIndex(IntIndex.named("noOfOccurance"));

			AttributeWord word = myBucket.fetch("christmas",
					AttributeWord.class).execute();

			System.out.println("" + word.getWordName());

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public AttributeWord checkWordIntoDB(String bucketName, String wordName) {
		Bucket myBucket;
		try {
			myBucket = riakClient.fetchBucket(bucketName).execute();
			for (String key : myBucket.keys()) {
				if (key.equalsIgnoreCase(wordName)) {
					return myBucket.fetch(wordName, AttributeWord.class)
							.execute();
				}
			}
		} catch (RiakRetryFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RiakException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void emptyBucket(String bucketName) {
		try {
			Bucket myBucket = riakClient.fetchBucket(bucketName).execute();
			for (String key : myBucket.keys()) {
				myBucket.delete(key).execute();
			}
		} catch (RiakRetryFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RiakException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public AttributeWord retrievePojo(String bucketName, String wordName)
			throws UnresolvedConflictException, ConversionException,
			RiakException {

		Bucket myBucket = riakClient.fetchBucket(bucketName).execute();

		AttributeWord word = myBucket.fetch(wordName, AttributeWord.class)
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

		// riakClient.shutdown();
		return word;
	}
}