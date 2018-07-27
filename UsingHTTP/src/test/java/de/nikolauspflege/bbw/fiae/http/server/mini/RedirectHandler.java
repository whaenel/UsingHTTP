package de.nikolauspflege.bbw.fiae.http.server.mini;

import java.io.IOException;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class RedirectHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange he) throws IOException {
		 Headers responseHeaders = he.getResponseHeaders();
	     responseHeaders.add("Location", "/redirect/target");
        he.sendResponseHeaders(301, -1);
        he.close();
	}

}
