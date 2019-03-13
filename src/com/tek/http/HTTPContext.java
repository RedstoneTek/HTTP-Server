package com.tek.http;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class HTTPContext {
	
	private Socket socket;
	private StringBuilder buffer;
	
	public HTTPContext(Socket socket) {
		this.socket = socket;
		this.buffer = new StringBuilder();
	}
	
	public void appendResponse(String text) {
		buffer.append(text);
	}
	
	public void appendResponse(HTTPResponse response) {
		buffer.append(response.toString());
	}
	
	public void respond() throws IOException {
		PrintStream ps = new PrintStream(socket.getOutputStream());
		ps.print(buffer.toString());
		ps.flush();
		ps.close();
	}
	
	public void close() throws IOException {
		socket.close();
	}
	
}
