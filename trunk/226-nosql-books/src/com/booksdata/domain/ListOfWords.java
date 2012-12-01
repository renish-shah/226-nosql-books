/**
 * 
 */
package com.booksdata.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Darsheet
 * 
 */
public class ListOfWords {

	/**
	 * @param args
	 */

	/*private static ListOfWords instance;
	public static ListOfWords getInstance()
	{
		if(instance==null)
		instance=new ListOfWords();
		return instance;
	}
	*/
	
	
	private static List<AttributeWord> listOfWords;

	public static List<AttributeWord> getListOfWords() {
		if(listOfWords==null)
		listOfWords = new ArrayList<AttributeWord>();
		return listOfWords;
	}

	public void setListOfWords(List<AttributeWord> listOfWords) {
		this.listOfWords = listOfWords;
	}

	
}
