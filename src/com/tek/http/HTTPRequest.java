package com.tek.http;

import java.util.List;

public class HTTPRequest {
	
	private HTTPMethod method;
	private String path;
	private List<HTTPParameter> params;
	private String http;
	private List<HTTPHeader> headers;
	private String body;
	
	public HTTPRequest(String method, String path, List<HTTPParameter> params, String http, List<HTTPHeader> headers, String body) {
		this.method = HTTPMethod.valueOf(method);
		this.path = path;
		this.params = params;
		this.http = http;
		this.headers = headers;
		this.body = body;
	}
	
	public HTTPMethod getMethod() {
		return method;
	}
	
	public String getPath() {
		return path;
	}
	
	public List<HTTPParameter> getParams() {
		return params;
	}
	
	public String getHTTP() {
		return http;
	}
	
	public List<HTTPHeader> getHeaders() {
		return headers;
	}
	
	public String getBody() {
		return body;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(method.name() + " ");
		builder.append(path + " ");
		builder.append(http + "\n");
		
		for(HTTPHeader header : headers) {
			builder.append(header.getHeader() + ": " + header.getValue() + "\n");
		}
		
		if(body != null) {
			builder.append("\n" + body);
		} else {
			builder.setLength(builder.length() - 1);
		}
		
		return builder.toString();
	}
	
}
