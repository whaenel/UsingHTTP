package de.nikolauspflege.bbw.fia.http.server.mini;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class RootHandler extends MethodHandler implements HttpHandler {
	
	@Override
	protected  void handleGet(HttpExchange he) {
		if (this.pathParms == null) {
			response = "<!DOCTYPE html>\n" + 
					"<html lang=\"de\">\n" + 
					"  <head>\n" + 
					"    <meta charset=\"utf-8\">\n" + 
					"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" + 
					"    <title>MiniServer</title>\n" + 
					"  </head>\n" + 
					"  <body>\n" + 
					"     <h2>You Reached the MiniServer</h2>\n" +
					"  </body> \n" + 
					"</html>";
		} else {
			response = "<!DOCTYPE html>\n" + 
					"<html lang=\"de\">\n" + 
					"  <head>\n" + 
					"    <meta charset=\"utf-8\">\n" + 
					"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" + 
					"    <title>MiniServer</title>\n" + 
					"  </head>\n" + 
					"  <body>\n" + 
					"     <h2>URL " + he.getRequestURI().toString() + " not supported </h2>\n" +
					"  </body> \n" + 
					"</html>";
			
		}
	}

}
