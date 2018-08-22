package de.nikolauspflege.bbw.fia.http.server.mini;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class VVSSelectionHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange he) throws IOException {
		String response = null;
		if (he.getRequestURI().toString().equals(he.getHttpContext().getPath())) {
			response = "<!DOCTYPE html>\n" + 
					"<html>\n" + 
					"<head>\n" + 
					"<title>Text Selection Box2</title>\n" + 
					"<meta charset=\"utf-8\">\n" + 
					"</head>\n" + 
					"<body>\n" + 
					"<form method=\"post\" action=\"vvs/station\">\n" + 
					"<label>Station \n" +
					"<select name=\"id\" size=\"4\" multiple>\n" + 
					"<option value=\"de:08111:6118\">Stuttgart, Hauptbahnhof (tief)</option>\n" + 
					"<option value=\"de:08111:6056\">Stuttgart, Stadtmitte</option>\n" + 
					"</select><br>\n" + 
					"<input type=\"submit\"></p>\n" + 
					"</form>\n" + 
					"</body>\n" + 
					"</html> "
					;
		} else {
			response = "<!DOCTYPE html>\n" + 
					"<html lang=\"de\">\n" + 
					"  <head>\n" + 
					"    <meta charset=\"utf-8\">\n" + 
					"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" + 
					"    <title>MiniServer</title>\n" + 
					"  </head>\n" + 
					"  <body>\n" + 
					"     <h2>URI: " + he.getRequestURI().toString() + " not supported </h2>\n" +
					"  </body> \n" + 
					"</html>";
			
		}
	    he.sendResponseHeaders(200, response.length());
	    OutputStream os = he.getResponseBody();
	    os.write(response.getBytes());
	    os.close();

	}

}
