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
	@JsonProperty
	private String wordCount;

	//@RiakIndex(name = "uri")
	//@JsonProperty
	private List<FileDetails> fileDetails;

	public String getWordCount() {
		return wordCount;
	}

	public void setWordCount(String wordCount) {
		this.wordCount = wordCount;
	}

	public String getWordName() {
		return wordName;
	}

	public void setWordName(String wordName) {
		this.wordName = wordName;
	}

	public List<FileDetails> getFileDetails() {
		return fileDetails;
	}

	public void setFileDetails(List<FileDetails> fileDetails) {
		this.fileDetails = fileDetails;
	}

}
