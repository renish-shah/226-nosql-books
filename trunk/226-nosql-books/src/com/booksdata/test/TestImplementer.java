/**
 * 
 */
package com.booksdata.test;

/**
 * @author renis
 *
 */
public class TestImplementer implements Test{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
		Test t=new TestImplementer();
		t.a=5;
		System.out.println(""+Test.a);

	}

}
