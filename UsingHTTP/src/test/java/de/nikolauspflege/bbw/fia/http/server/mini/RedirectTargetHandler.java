package de.nikolauspflege.bbw.fia.http.server.mini;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class RedirectTargetHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange he) throws IOException {
		String response = "<!DOCTYPE html>\n" + 
				"<html lang=\"de\">\n" + 
				"  <head>\n" + 
				"    <meta charset=\"utf-8\">\n" + 
				"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" + 
				"    <title>MiniServer</title>\n" + 
				"  </head>\n" + 
				"  <body>\n" + 
				"     <h2>You have been redirected to here</h2>\n" +
				"  </body> \n" + 
				"</html>";
	    he.sendResponseHeaders(200, response.length());
	    OutputStream os = he.getResponseBody();
	    os.write(response.getBytes());
	    os.close();
	}

}
