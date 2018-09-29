package de.nikolauspflege.bbw.fia.http.server.mini;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
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

public class MethodHandler implements HttpHandler {
	String response;
	String message;
	int rc;
	Map<String,String> parms;
	String [] pathParms;
	
	@Override
	public void handle(HttpExchange he) throws IOException {
		response = null;
		message = null;
		rc = 200;
		parms = null; new HashMap<String,String>();
		pathParms = null;
		
		// get parameters of URI
		String query = he.getRequestURI().getQuery();
		if ((query != null) && (query.length()>2 ) && (query.contains("="))) {
			parms = new HashMap<String,String>();
			String[] rawParms = query.split("&");		
			for (int i = 0; i < rawParms.length; i++) {
				String[] parts = rawParms[i].split("=",2);
				parms.put(parts[0].trim(),parts[1].trim());
			}
			
		}
		
		// get path parameters
		String path = he.getRequestURI().getPath();
		String context = he.getHttpContext().getPath();
		if (!context.endsWith("/")) {
			context = context+"/";
		}
		if (path.length() > context.length()) {
			String extPath = path.substring(context.length());
			if ((extPath != null) && (extPath.length() > 0)) {
				pathParms = extPath.split("/");
			}
			
		}
		
		// now switch on method
		String method = he.getRequestMethod();
		switch (method) {
		case "GET":
			handleGet(he);
			break;
		case "POST":
			addPostParameters(he);
			handlePost(he);
			break;
		case "PUT":
			handlePut(he);
			break;
		case "PATCH":
			handlePatch(he);
			break;
		case "DELETE":
			handleDelete(he);
			break;

		default:
			break;
		}
		//Now send it
		sendResponse(he);
	}
		
		
		
		
		
	private void addPostParameters(HttpExchange he) throws UnsupportedEncodingException {
		InputStream in = he.getRequestBody();
        //List<String> parameters = new LinkedList<String>();

		Iterator<String> line = new BufferedReader(new InputStreamReader(in)).lines().iterator();
        while (line.hasNext()) {
    		if (parms == null) {
    			parms = new HashMap<String,String>();
    		}
			String[] parts = ((String) line.next()).split("=",2);
			parms.put(parts[0].trim(),URLDecoder.decode(parts[1].trim(),"UTF-8"));
		}
		
	}
		
		

	
	protected void sendResponse(HttpExchange he) throws IOException {
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

	protected void handleDelete(HttpExchange he) {
		// implementation if not explicitly handled
		// set method not supported 
		rc = 405;

	}

	protected void handlePatch(HttpExchange he) {
		// implementation if not explicitly handled
		// set method not supported 
		rc = 405;

	}

	protected void handlePut(HttpExchange he) {
		// implementation if not explicitly handled
		// set method not supported 
		rc = 405;

	}

	protected void handlePost(HttpExchange he) {
		// implementation if not explicitly handled
		// set method not supported 
		rc = 405;

	}

	protected void handleGet(HttpExchange he) {
		// implementation if not explicitly handled
		rc = 400; // bad request 
	}

}
