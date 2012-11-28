/**
 * 
 */
package com.booksdata.domain;

import java.util.ArrayList;
import java.util.Collection;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import com.basho.riak.client.convert.RiakIndex;
import com.basho.riak.client.convert.RiakKey;
import com.basho.riak.client.convert.RiakLinks;
import com.basho.riak.pbc.RiakLink;

/**
 * @author renis
 * 
 */
public class JsonObject {

	@JsonProperty
	String bucket;

	@RiakKey
	String key;

	@JsonProperty
	String name;

	@RiakLinks
	@JsonIgnore
	Collection<RiakLink> riakLinks = new ArrayList<RiakLink>();

	@RiakIndex(name = "uri")
	@JsonProperty
	String uriIndex;

	public String getBucket() {
		return bucket;
	}

	public void setBucket(String bucket) {
		this.bucket = bucket;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<RiakLink> getRiakLinks() {
		return riakLinks;
	}

	public void setRiakLinks(Collection<RiakLink> riakLinks) {
		this.riakLinks = riakLinks;
	}

	public String getUriIndex() {
		return uriIndex;
	}

	public void setUriIndex(String uriIndex) {
		this.uriIndex = uriIndex;
	}

}
