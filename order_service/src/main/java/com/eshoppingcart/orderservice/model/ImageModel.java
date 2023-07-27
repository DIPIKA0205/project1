package com.eshoppingcart.orderservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection="image_model")
public class ImageModel {
	
	@Transient
	public static final String SEQUENCE_NAME="users_sequence";
	
	@Id
	private long id;
	private String name;
	private String type;
	private byte[] picByte;
	public ImageModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ImageModel(String name, String type, byte[] picByte) {
		super();
		this.name = name;
		this.type = type;
		this.picByte = picByte;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public byte[] getPicByte() {
		return picByte;
	}

	public void setPicByte(byte[] picByte) {
		this.picByte = picByte;
	}

	public static String getSequenceName() {
		return SEQUENCE_NAME;
	}
	
	
	
	
	
	
}
