package de.nikolauspflege.bbw.fiae.http.server.mini;

import java.security.InvalidParameterException;

import org.json.JSONArray;
import org.json.JSONObject;

public class VVSBackend {
	
	// implement singleton pattern
	static VVSBackend theBackend = null;
	JSONArray stations = null;
	JSONObject locations = null;
	
	public static VVSBackend getInstance() {
		if (theBackend == null) {
			theBackend = new VVSBackend();
		}
		return theBackend;
	}
	public JSONArray getStations() {
		return stations;
	}
	public JSONObject getLocations() {
		return locations;
	}
	public JSONObject getStationById(String id) {
		JSONObject response = null;
		for (int i = 0; i < stations.length() ; i++) {
			if (((JSONObject)stations.get(i)).getString("id").equals(id)){
				response = (JSONObject)stations.get(i);
				break;
			}
		}
		return response;
	}
	
	public JSONObject getStationByName(String name) {
		JSONObject response = null;
		for (int i = 0; i < stations.length() ; i++) {
			if (((JSONObject)stations.get(i)).getString("name").contains(name) ){
				response = (JSONObject)stations.get(i);
				break;
			}
		}
		return response;
	}
	
	public void addStation(JSONObject station) throws InvalidParameterException{
		if (station.has("name")&&station.has("id")) {
			stations.put(station);
		} else {
			throw new InvalidParameterException("Station must contain at least name and id");
		}
	}

	public VVSBackend() {
		super();
		reset();
	}
	public void reset() {
		// TODO Auto-generated method stub
		stations= new JSONArray();
		locations = new JSONObject();
		stations.put(new JSONObject("{\"id\":\"de:08111:6118\",\"isGlobalId\":true,\"name\":\"Stuttgart, Hauptbahnhof (tief)\",\"disassembledName\":\"Hauptbahnhof (tief)\",\"coord\":[48.78298,9.17985],\"type\":\"stop\",\"matchQuality\":0,\"isBest\":false,\"parent\":{\"id\":\"8111000|51\",\"name\":\"Stuttgart\",\"type\":\"locality\"},\"assignedStops\":[{\"id\":\"de:08111:6118\",\"isGlobalId\":true,\"name\":\"Stuttgart Hauptbahnhof (tief)\",\"disassembledName\":\"Hauptbahnhof (tief)\",\"type\":\"stop\",\"coord\":[48.78298,9.17985],\"parent\":{\"name\":\"Stuttgart\",\"type\":\"locality\"},\"modes\":[1],\"connectingMode\":100,\"properties\":{\"stopId\":\"5006118\"}}],\"properties\":{\"stopId\":\"5006118\"},\"downloads\":[{\"type\":\"SM\",\"url\":\"vvs/me_plaene/ME_Hbf.pdf\"},{\"type\":\"SM\",\"url\":\"vvs/uhbf.pdf\",\"size\":1001}]}"));
		stations.put(new JSONObject("{\"id\":\"de:08111:6056\",\"isGlobalId\":true,\"name\":\"Stuttgart, Stadtmitte\",\"disassembledName\":\"Stadtmitte\",\"coord\":[48.77633,9.17305],\"type\":\"stop\",\"matchQuality\":100000,\"isBest\":false,\"parent\":{\"id\":\"8111000|51\",\"name\":\"Stuttgart\",\"type\":\"locality\"},\"assignedStops\":[{\"id\":\"de:08111:6056\",\"isGlobalId\":true,\"name\":\"Stadtmitte\",\"type\":\"stop\",\"coord\":[48.77633,9.17305],\"parent\":{\"name\":\"Stuttgart\",\"type\":\"locality\"},\"modes\":[1,3,5],\"connectingMode\":100,\"properties\":{\"stopId\":\"5006056\"}}],\"properties\":{\"stopId\":\"5006056\"},\"downloads\":[{\"type\":\"SM\",\"url\":\"vvs/uroteb.pdf\",\"size\":630},{\"type\":\"SM\",\"url\":\"vvs/me_plaene/ME_Roteb.pdf\",\"size\":904}]}"));
		locations.put("locations", stations);
		
	}


}
