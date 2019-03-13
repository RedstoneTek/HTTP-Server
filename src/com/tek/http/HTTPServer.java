package com.tek.http;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.BiConsumer;

public class HTTPServer {
	
	public static final String HTTP_VER = "HTTP/1.1";
	
	private int port;
	
	public HTTPServer(int port) {
		this.port = port;
	}
	
	@SuppressWarnings("resource")
	public void bind(BiConsumer<HTTPRequest, HTTPContext> callback) throws IOException {
		ServerSocket serverSocket = new ServerSocket(port);
		
		while(true) {
			Socket socket = serverSocket.accept();
			
			StringBuilder buffer = new StringBuilder();
			while(socket.getInputStream().available() > 0 || buffer.length() == 0) {
				int length = Math.min(1024, socket.getInputStream().available());
				byte[] b = new byte[length];
				int read = socket.getInputStream().read(b, 0, length);
				if(read < 0) break;
				buffer.append(new String(b));
			}
			
			String requestContent = buffer.toString();
			
			HTTPRequest request = readRequest(requestContent);
			HTTPContext ctx = new HTTPContext(socket);
			callback.accept(request, ctx);
		}
	}
	
	private HTTPRequest readRequest(String request) {
		Scanner scanner = new Scanner(request);
		
		String method = scanner.next();
		
		String pathFull = scanner.next();
		List<HTTPParameter> params = new ArrayList<HTTPParameter>();
		Scanner pathScanner = new Scanner(pathFull);
		pathScanner.useDelimiter("");
		String path = "";
		while(pathScanner.hasNext()) {
			String n = pathScanner.next();
			if(n.equals("?")) {
				break;
			} else {
				path += n;
			}
		}
		pathScanner.close();
		
		if(pathFull.length() != path.length()) {
			String pathParams = pathFull.substring(path.length() + 1);
			String[] paramsStr = pathParams.split("&");
			
			if(paramsStr.length != 0 && !paramsStr[0].isEmpty()) {
				for(String paramStr : paramsStr) {
					String[] parts = paramStr.split("=");
					if(parts.length != 2) continue;
					params.add(new HTTPParameter(parts[0], parts[1]));
				}
			}
		}
		
		String http = scanner.next();
		scanner.nextLine();
		
		List<HTTPHeader> headers = new ArrayList<HTTPHeader>();
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if(line.isEmpty()) break;
			Scanner headerScanner = new Scanner(line);
			String header = headerScanner.next();
			header = header.substring(0, header.length() - 1);
			String value = headerScanner.nextLine();
			value = value.substring(1, value.length());
			headers.add(new HTTPHeader(header, value));
			headerScanner.close();
		}
		
		StringBuilder buffer = new StringBuilder();
		if(scanner.hasNextLine()) {
			while(scanner.hasNextLine()) {
				buffer.append(scanner.nextLine() + "\n");
			}
		}
		if(buffer.length() > 0) buffer.setLength(buffer.length() - 1);
		String body = buffer.length() == 0 ? null : buffer.toString();
		
		scanner.close();
		
		return new HTTPRequest(method, path, params, http, headers, body);
	}
	
}
