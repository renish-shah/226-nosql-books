package com.booksdata.application;

import java.io.File;
import java.util.HashMap;

import com.booksdata.parsing.FileParsing;
import com.booksdata.parsing.RemoveStopWords;

public class Main {

	public static void main(String args[])
	{
		FileParsing parser = new FileParsing();
		HashMap<String,Integer> matrix = new HashMap<String,Integer>();
		matrix = parser.parse(new File("E:/books226/11.txt"));
		RemoveStopWords rm = new RemoveStopWords();
		matrix = rm.clean(matrix);
		int ret = (Integer)matrix.get("alice");
		System.out.println("Ret is: " + ret);
	}
}
