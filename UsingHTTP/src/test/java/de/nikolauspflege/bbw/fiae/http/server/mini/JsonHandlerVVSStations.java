package de.nikolauspflege.bbw.fiae.http.server.mini;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class JsonHandlerVVSStations implements HttpHandler {

	JSONArray stations = new JSONArray();
	JSONObject locations = new JSONObject();
	
	public JsonHandlerVVSStations() {
		super();
		stations.put(new JSONObject("{\"id\":\"de:08111:6118\",\"isGlobalId\":true,\"name\":\"Stuttgart, Hauptbahnhof (tief)\",\"disassembledName\":\"Hauptbahnhof (tief)\",\"coord\":[48.78298,9.17985],\"type\":\"stop\",\"matchQuality\":0,\"isBest\":false,\"parent\":{\"id\":\"8111000|51\",\"name\":\"Stuttgart\",\"type\":\"locality\"},\"assignedStops\":[{\"id\":\"de:08111:6118\",\"isGlobalId\":true,\"name\":\"Stuttgart Hauptbahnhof (tief)\",\"disassembledName\":\"Hauptbahnhof (tief)\",\"type\":\"stop\",\"coord\":[48.78298,9.17985],\"parent\":{\"name\":\"Stuttgart\",\"type\":\"locality\"},\"modes\":[1],\"connectingMode\":100,\"properties\":{\"stopId\":\"5006118\"}}],\"properties\":{\"stopId\":\"5006118\"},\"downloads\":[{\"type\":\"SM\",\"url\":\"vvs/me_plaene/ME_Hbf.pdf\"},{\"type\":\"SM\",\"url\":\"vvs/uhbf.pdf\",\"size\":1001}]}"));
		stations.put(new JSONObject("{\"id\":\"de:08111:6056\",\"isGlobalId\":true,\"name\":\"Stuttgart, Stadtmitte\",\"disassembledName\":\"Stadtmitte\",\"coord\":[48.77633,9.17305],\"type\":\"stop\",\"matchQuality\":100000,\"isBest\":false,\"parent\":{\"id\":\"8111000|51\",\"name\":\"Stuttgart\",\"type\":\"locality\"},\"assignedStops\":[{\"id\":\"de:08111:6056\",\"isGlobalId\":true,\"name\":\"Stadtmitte\",\"type\":\"stop\",\"coord\":[48.77633,9.17305],\"parent\":{\"name\":\"Stuttgart\",\"type\":\"locality\"},\"modes\":[1,3,5],\"connectingMode\":100,\"properties\":{\"stopId\":\"5006056\"}}],\"properties\":{\"stopId\":\"5006056\"},\"downloads\":[{\"type\":\"SM\",\"url\":\"vvs/uroteb.pdf\",\"size\":630},{\"type\":\"SM\",\"url\":\"vvs/me_plaene/ME_Roteb.pdf\",\"size\":904}]}"));
		locations.put("locations", stations);
	}
	
	@Override
	public void handle(HttpExchange he) throws IOException {
        List<String> acceptHeader = he.getRequestHeaders().get("Accept");
        boolean acceptJson = true;
        if (acceptHeader != null) {
			
	        Iterator<String> acceptIt = acceptHeader.iterator();
	        while (acceptIt.hasNext()) {
	        	String[] acceptList = acceptIt.next().split("[,;]");
	        	for (int i = 0 ; i < acceptList.length ; i++) {
	        		if (acceptList[i].equalsIgnoreCase("json")|| acceptList[i].equalsIgnoreCase("application/json") ) {
	        			acceptJson = true;
	        			break;
	        		}
		        }
	        }
		} 
        
        if (acceptJson) {
    		String method = he.getRequestMethod();
    		String context = he.getHttpContext().getPath();
    		switch (method) {
			case "GET":
				String response= null;
				URI myUri = he.getRequestURI();
				String query = myUri.getQuery();
				String path = myUri.getPath();
				if (path.endsWith("/")) {
					path = path.substring(0, path.length()-1);
				}

				if (myUri.getQuery() == null){
					if (path.equals(context)){
						response = "{\n"+
								   "\"locations\":[ \n" +
								   "    {\"id\":\"de:08111:6118\",\"isGlobalId\":true,\"name\":\"Stuttgart, Hauptbahnhof (tief)\",\"disassembledName\":\"Hauptbahnhof (tief)\",\"coord\":[48.78298,9.17985],\"type\":\"stop\",\"matchQuality\":0,\"isBest\":false,\"parent\":{\"id\":\"8111000|51\",\"name\":\"Stuttgart\",\"type\":\"locality\"},\"assignedStops\":[{\"id\":\"de:08111:6118\",\"isGlobalId\":true,\"name\":\"Stuttgart Hauptbahnhof (tief)\",\"disassembledName\":\"Hauptbahnhof (tief)\",\"type\":\"stop\",\"coord\":[48.78298,9.17985],\"parent\":{\"name\":\"Stuttgart\",\"type\":\"locality\"},\"modes\":[1],\"connectingMode\":100,\"properties\":{\"stopId\":\"5006118\"}}],\"properties\":{\"stopId\":\"5006118\"},\"downloads\":[{\"type\":\"SM\",\"url\":\"vvs/me_plaene/ME_Hbf.pdf\"},{\"type\":\"SM\",\"url\":\"vvs/uhbf.pdf\",\"size\":1001}]},\n" +
								   "    {\"id\":\"de:08111:6056\",\"isGlobalId\":true,\"name\":\"Stuttgart, Stadtmitte\",\"disassembledName\":\"Stadtmitte\",\"coord\":[48.77633,9.17305],\"type\":\"stop\",\"matchQuality\":100000,\"isBest\":false,\"parent\":{\"id\":\"8111000|51\",\"name\":\"Stuttgart\",\"type\":\"locality\"},\"assignedStops\":[{\"id\":\"de:08111:6056\",\"isGlobalId\":true,\"name\":\"Stadtmitte\",\"type\":\"stop\",\"coord\":[48.77633,9.17305],\"parent\":{\"name\":\"Stuttgart\",\"type\":\"locality\"},\"modes\":[1,3,5],\"connectingMode\":100,\"properties\":{\"stopId\":\"5006056\"}}],\"properties\":{\"stopId\":\"5006056\"},\"downloads\":[{\"type\":\"SM\",\"url\":\"vvs/uroteb.pdf\",\"size\":630},{\"type\":\"SM\",\"url\":\"vvs/me_plaene/ME_Roteb.pdf\",\"size\":904}]}\n"	+
								   "  ]\n" +
								   "}";
						response = locations.toString();
					} else {
						String locationId = path.substring(context.length()+1);
						int firstSlash = locationId.indexOf("/");
						if (firstSlash != -1) {
							locationId = locationId.substring(0, firstSlash);
						}
						
						response = "station: " + locationId + "not found";
						for (int i = 0; i < stations.length() ; i++) {
							if (((JSONObject)stations.get(i)).getString("id").equals(locationId)){
								response = ((JSONObject)stations.get(i)).toString();
								break;
							}
							
						}
					}
				} else {
					String[] queryList = query.split("&");
					for (int i = 0; i < queryList.length; i++) {
						if (queryList[i].startsWith("name=")) {
							String name = queryList[i].substring(5);
							response = "name: "+ name +" not found";
							for (int j = 0; j < stations.length() ; j++) {
								if (((JSONObject)stations.get(j)).getString("name").contains(name)){
									response = ((JSONObject)stations.get(j)).toString();
									break;
								}
							}
							
						}
						
					}
					if (response == null) {
						
						
						response = "<!DOCTYPE html>\n" + 
								"<html lang=\"de\">\n" + 
								"  <head>\n" + 
								"    <meta charset=\"utf-8\">\n" + 
								"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" + 
								"    <title>MiniServer</title>\n" + 
								"  </head>\n" + 
								"  <body>\n" + 
								"     <h2>You Reached the VVSSite</h2>\n" +
								"at the context: " + context + "</br>" +
								"using the path: " + path + "</br>" +
								"using the method: " + method + "</br>" +
								"using the query: " + query + "</br>" +
								"  </body> \n" + 
								"</html>";
					}
				}
			    he.sendResponseHeaders(200, response.length());
			    OutputStream os = he.getResponseBody();
			    os.write(response.getBytes());
			    os.close();
			    he.close();
				break;

			default:
		        // send method not supported 
		        he.sendResponseHeaders(405, 0);
		        he.close();

				break;
			}
			
		} else {
	        // send unsupported media type 
	        he.sendResponseHeaders(415, 0);
	        he.close();
		}
        
		
	/*	
		Class cls = this.getClass();
		URL inr =cls.getResource("hbf.json");
		long size =(new File(inr.getFile())).length();
		InputStream in = cls.getResourceAsStream("hbf.json");
		OutputStream os = he.getResponseBody();
	    he.sendResponseHeaders(200, size);
		in.transferTo(os);
	    os.close();
	    in.close();
*/
	}

}
