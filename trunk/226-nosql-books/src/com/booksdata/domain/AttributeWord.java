package com.booksdata.domain;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.basho.riak.client.convert.RiakIndex;

/**
 * @author renis
 * 
 */
public class AttributeWord {

	@JsonProperty
	private String wordName;

	// @RiakIndex(name = "uri")
	// @JsonProperty
	// private String wordCount;

	// @RiakIndex(name = "uri")
	// @JsonProperty
	private List<FileDetails> listOfFileDetails;

	/*
	 * public String getWordCount() { return wordCount; }
	 * 
	 * public void setWordCount(String wordCount) { this.wordCount = wordCount;
	 * }
	 */

	public String getWordName() {
		return wordName;
	}

	public List<FileDetails> getListOfFileDetails() {
		return listOfFileDetails;
	}

	public void setListOfFileDetails(List<FileDetails> listOfFileDetails) {
		this.listOfFileDetails = listOfFileDetails;
	}

	public void setWordName(String wordName) {
		this.wordName = wordName;
	}

}
