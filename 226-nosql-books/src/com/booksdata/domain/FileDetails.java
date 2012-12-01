/**
 * 
 */
package com.booksdata.domain;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonProperty;

import com.basho.riak.client.convert.RiakIndex;

/**
 * @author renis
 * 
 */
public class FileDetails {

	@JsonProperty
	private String fileName;

	@RiakIndex(name = "uri")
	@JsonProperty
	private int noOfOccurance;

	@JsonProperty
	private ArrayList<Integer> position;

	@JsonProperty
	private double mean;

	@JsonProperty
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
