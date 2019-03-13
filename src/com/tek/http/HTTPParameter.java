package com.tek.http;

public class HTTPParameter {
	
	public String parameter;
	private String value;
	
	public HTTPParameter(String parameter, String value) {
		this.parameter = parameter;
		this.value = value;
	}
	
	public String getParameter() {
		return parameter;
	}
	
	public String getValue() {
		return value;
	}
	
}
