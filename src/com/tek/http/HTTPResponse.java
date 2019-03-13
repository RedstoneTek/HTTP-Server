package com.tek.http;

import java.util.ArrayList;
import java.util.List;

public class HTTPResponse {
	
	private int code;
	private List<HTTPHeader> headers;
	private String content;
	
	public HTTPResponse() {
		headers = new ArrayList<HTTPHeader>();
	}
	
	public void setCode(int code) {
		this.code = code;
	}
	
	public void setHeader(String header, String value) {
		headers.add(new HTTPHeader(header, value));
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder();
		buffer.append(HTTPServer.HTTP_VER + " ");
		buffer.append(code + " ");
		
		if(code >= 100 && code < 200) buffer.append("Informational");
		if(code >= 200 && code < 300) buffer.append("Successful");
		if(code >= 300 && code < 400) buffer.append("Redirection");
		if(code >= 400 && code < 500) buffer.append("Client Error");
		if(code >= 500 && code < 600) buffer.append("Server Error");
		buffer.append("\n");
		
		for(HTTPHeader header : headers) {
			buffer.append(header.getHeader() + ": " + header.getValue() + "\n");
		}
		
		if(content != null) {
			buffer.append("\n" + content);
		} else {
			buffer.setLength(buffer.length() - 1);
		}
		
		return buffer.toString();
	}
	
}
