package de.nikolauspflege.bbw.fia.http.server.mini;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class VVSPostDisplayHandler implements HttpHandler {

	
	@Override
	public void handle(HttpExchange he) throws IOException {
		String response = null;
		String message = null;
		int rc = 200;
		if (he.getRequestURI().toString().equals(he.getHttpContext().getPath())) {
			if (he.getRequestMethod().equals("POST")) {
				InputStream in = he.getRequestBody();
                //List<String> parameters = new LinkedList<String>();
                Map<String,String> parms = new HashMap<String,String>();

                Iterator<String> line = new BufferedReader(new InputStreamReader(in)).lines().iterator();
                while (line.hasNext()) {
					String[] parts = ((String) line.next()).split("=",2);
					parms.put(parts[0].trim(),URLDecoder.decode(parts[1].trim(),"UTF-8"));
				}
                

                if (parms.containsKey("id")) {
                	String id = parms.get("id");
					VVSBackend backend = VVSBackend.getInstance();
					JSONObject station = backend.getStationById(id);
					if (station != null) {
						response = station.toString();
					} else {
						rc = 404; // not found 
					}
				} else {
					rc = 400; // bad request - syntax not correct, id missing
				}
			} else {
				// set method not supported 
				rc = 405;
			}
		} else {
			message = "URI: " + he.getRequestURI().toString() + " not supported " ;
		}
		
		//Now send it
		if (message != null) {
			Headers responseHeaders = he.getResponseHeaders();
		    responseHeaders.add("Location", "/vvs/error");
	        he.sendResponseHeaders(301, -1);
	        he.close();
		} else if (response == null) {
	        he.sendResponseHeaders(rc, 0);
	        he.close();

		} else {
		    he.sendResponseHeaders(200, response.length());
		    OutputStream os = he.getResponseBody();
		    os.write(response.getBytes());
		    os.close();
		}

	}

}
