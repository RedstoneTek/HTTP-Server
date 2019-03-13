package com.tek.http;

public class HTTPHeader {
	
	private String header, value;
	
	public HTTPHeader(String header, String value) {
		this.header = header;
		this.value = value;
	}
	
	public String getHeader() {
		return header;
	}
	
	public String getValue() {
		return value;
	}
	
}
