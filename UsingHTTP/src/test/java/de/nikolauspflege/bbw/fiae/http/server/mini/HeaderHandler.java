package de.nikolauspflege.bbw.fiae.http.server.mini;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
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
        String response = null;
        List<String> acceptHeader = he.getRequestHeaders().get("Accept");
        
        String acceptType ="";
        if (acceptHeader != null) {
			
        Iterator<String> acceptIt = acceptHeader.iterator();
        while (acceptIt.hasNext()) {
        	String[] acceptList = acceptIt.next().split("[,;]");
        	for (int i = 0 ; i < acceptList.length ; i++) {
		        switch (acceptList[i]) {
					case "text/html":
					case "application/xhtml+xml":
					case "aplication/xml":
						acceptType="html";
						break;
					case"application/json":
						acceptType="json";
						break;
		        }
	        }
        }
		} else {
			acceptType="html";
		}
	       	
        
        switch (acceptType) {
		case "html":
	        for (Map.Entry<String, List<String>> entry : entries) {
	        	for (int i = 0; i < entry.getValue().size(); i++) {
	        		heList += "        <li>"+ entry.getKey() + ":" + entry.getValue().get(i) + "</li>\n";
	        	}
	        }
	        response = "<!DOCTYPE html>\n" + 
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
			
			break;
		case "json":
			String sep = "\n";
	        for (Map.Entry<String, List<String>> entry : entries) {
	        	for (int i = 0; i < entry.getValue().size(); i++) {
	        		heList += sep +"  { name: \""+ entry.getKey() + "\", value: \"" + entry.getValue().get(i) + "\" }" ;
					sep = ",\n";
	        	}
	        }
	        response = "{ headers: [" + 
				heList +
				"\n  ]}";
			break;

		default:
			response = "format not supported";
			break;
		}
        for (Map.Entry<String, List<String>> entry : entries)
                 heList += "<li>"+ entry.toString() + "</li>\n";
	    he.sendResponseHeaders(200, response.length());
	    OutputStream os = he.getResponseBody();
	    os.write(response.getBytes());
	    os.close();

	}

}
