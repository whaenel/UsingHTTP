package de.nikolauspflege.bbw.fiae.http.server.mini;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class HeaderHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange he) throws IOException {
		Headers headers = he.getRequestHeaders();
        Set<Map.Entry<String, List<String>>> entries = headers.entrySet();
        String heList = "";
        for (Map.Entry<String, List<String>> entry : entries)
                 heList += "<li>"+ entry.toString() + "</li>\n";
		String response = "<!DOCTYPE html>\n" + 
				"<html lang=\"de\">\n" + 
				"  <head>\n" + 
				"    <meta charset=\"utf-8\">\n" + 
				"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" + 
				"    <title>MiniServer</title>\n" + 
				"  </head>\n" + 
				"  <body>\n" + 
				"     <h2>Your header contains</h2>\n" +
				"     <ol>\n" +
				heList +
				"     </ol>\n" +
				"  </body> \n" + 
				"</html>";
	    he.sendResponseHeaders(200, response.length());
	    OutputStream os = he.getResponseBody();
	    os.write(response.getBytes());
	    os.close();

	}

}
