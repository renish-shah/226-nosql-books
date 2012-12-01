/**
 * 
 */
package com.booksdata.domain;

import java.util.ArrayList;

/**
 * @author renis
 * 
 */
public class CopyOfFileDetails {

	private String fileName;
	private int noOfOccurance;
	private ArrayList<Integer> position;
	private double mean;
	private double stdev;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getNoOfOccurance() {
		return noOfOccurance;
	}

	public void setNoOfOccurance(int noOfOccurance) {
		this.noOfOccurance = noOfOccurance;
	}

	public ArrayList<Integer> getPosition() {
		return position;
	}

	public void setPosition(ArrayList<Integer> position) {
		this.position = position;
	}

	public double getMean() {
		return mean;
	}

	public void setMean(double mean) {
		this.mean = mean;
	}

	public double getStdev() {
		return stdev;
	}

	public void setStdev(double stdev) {
		this.stdev = stdev;
	}

}
