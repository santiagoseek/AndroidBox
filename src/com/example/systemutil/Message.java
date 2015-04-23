package com.example.systemutil;

import java.io.Serializable;

public class Message  implements Serializable  {


	/**
	 * 
	 */
	private static final long serialVersionUID = 5654144246448541067L;
	private int id;
	private String type;
	private String version;
	private short priority;
	private long expireTime;
	private String ubtData;
	
	private transient long offerTime;	
	
	public Message(){
		
	}
	
	public Message(String type,String version){
		this.type = type;
		this.version = version;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public short getPriority() {
		return priority;
	}

	public void setPriority(short priority) {
		this.priority = priority;
	}	
	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(long expireTime) {
		this.expireTime = expireTime;
	}

	public long getOfferTime() {
		return offerTime;
	}

	public void setOfferTime(long offerTime) {
		this.offerTime = offerTime;
	}

	public String getUbtData() {
		return ubtData;
	}

	public void setUbtData(String ubtData) {
		this.ubtData = ubtData;
	}


}
