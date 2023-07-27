package com.eshoppingcart.orderservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "db_sequence")
public class DBSequence {

	@Id
	private String id;
	private long seq;

	// parameterized constructor
	public DBSequence(String id, long seqNo) {
		super();
		this.id = id;
		this.seq = seqNo;
	}

	// default constructor
	public DBSequence() {
		super();
	}

	// getter and setters
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getSeq() {
		return seq;
	}

	public void setSeq(long seqNo) {
		this.seq = seqNo;
	}

}
