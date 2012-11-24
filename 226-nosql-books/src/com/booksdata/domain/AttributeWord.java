package com.booksdata.domain;

import java.util.List;

/**
 * @author renis
 * 
 */
public class AttributeWord {

	private String wordName;
	private List<FileDetails> fileDetails;

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
