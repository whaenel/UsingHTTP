/**
 * 
 */
package de.nikolauspflege.bbw.fiae.http;

import java.io.IOException;
import java.net.URI;

import javax.net.ssl.SSLParameters;

import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpClient.Redirect;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;
import jdk.incubator.http.HttpResponse.BodyHandler;

/**
 * @author walter
 *
 */
public class ManagedHTTPConnection {
	

	private Redirect redirect = HttpClient.Redirect.ALWAYS;

	/**
	 * 
	 */
	public ManagedHTTPConnection() {
		// TODO Auto-generated constructor stub
	}

	public HttpResponse<String> sendHttpsGet(URI anUri) throws IOException, InterruptedException{
		HttpResponse<String> strResponse=null;
	        HttpClient client = HttpClient.newBuilder()
	        		  .followRedirects(redirect )
	        		  .build();
	        HttpRequest request = HttpRequest.newBuilder()
	                .uri(anUri)
	                .GET()
	                .build();
	        //String body handler
	        strResponse = client.send(request, HttpResponse.BodyHandler.asString());
	    return strResponse;
		
	}

}
