package de.nikolauspflege.bbw.fiae.http.server.mini;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class JsonHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange he) throws IOException {
		String method = he.getRequestMethod();
		String context = he.getHttpContext().getPath();
		String response = "<!DOCTYPE html>\n" + 
				"<html lang=\"de\">\n" + 
				"  <head>\n" + 
				"    <meta charset=\"utf-8\">\n" + 
				"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" + 
				"    <title>MiniServer</title>\n" + 
				"  </head>\n" + 
				"  <body>\n" + 
				"     <h2>You Reached the MiniServer</h2>\n" +
				"at the context: " + context + "</br>" +
				"using the method: " + method + "</br>" +
				"  </body> \n" + 
				"</html>";
		
		
		Class cls = this.getClass();
		URL inr =cls.getResource("hbf.json");
		long size =(new File(inr.getFile())).length();
		InputStream in = cls.getResourceAsStream("hbf.json");
		OutputStream os = he.getResponseBody();
	    he.sendResponseHeaders(200, size);
		in.transferTo(os);
	    os.close();
	    in.close();

	}

}
