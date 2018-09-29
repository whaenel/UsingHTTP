/**
 * 
 */
package de.nikolauspflege.bbw.fiae.http;

import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.SSLParameters;

import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpClient.Redirect;
import jdk.incubator.http.HttpClient.Version;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpRequest.BodyPublisher;
import jdk.incubator.http.HttpRequest.Builder;
import jdk.incubator.http.HttpResponse;
import jdk.incubator.http.HttpResponse.BodyHandler;

/**
 * @author walter
 *
 */
public class ManagedHTTPConnection {
	

	private Redirect redirect = HttpClient.Redirect.ALWAYS;
	private Map<String, String> headers = new HashMap<>();
	private Version httpVersion = HttpClient.Version.HTTP_2;
	private Duration defaultConnectionTimeout = Duration.ofMinutes(5);


	private HttpClient client;
	/* sample headers
	 * Accept	text/html,application/xhtml+xml,aplication/xml;q=0.9,*\/*;q=0.8
	 * Accept-Encoding	gzip, deflate, br
	 * Accept-Language	de,en-US;q=0.8,en;q=0.5
	 * Cache-Control max-age=0
	 * Connection keep-alive
	 * DNT 1
	 * Host www.tutorialspoint.com
	 * If-Modified-Since  	Sat, 21 Jul 2018 14:03:11 GMT
	 * Referer https://www.tutorialspoint.com…unit/junit_executing_tests.htm
	 * Upgrade-Insecure-Requests 1
	 * User-Agent Mozilla/5.0 (X11; Linux x86_64…) Gecko/20100101 Firefox/61.0
	 */

	public Map<String, String> getHeaders() {
		return headers;
	}


	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public Duration getDefaultConnectionTimeout() {
		return defaultConnectionTimeout;
	}


	public void setDefaultConnectionTimeout(Duration defaultConnectionTimeout) {
		this.defaultConnectionTimeout = defaultConnectionTimeout;
	}

	/**
	 * 
	 */
	public ManagedHTTPConnection() {
		headers.put("Accept", "text/html,application/xhtml+xml,aplication/xml;q=0.9,*\\/*;q=0.8");
		headers.put("Accept-Language", "de,en-US;q=0.8,en;q=0.5");
		headers.put("Connection", "keep-alive");
		headers.put("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:61.0) Gecko/20100101 Firefox/61.0");
		client = HttpClient.newBuilder()
      		  .followRedirects(redirect )
      		  .build();
		
	}
	
	
	
	public Redirect getRedirect() {
		return redirect;
	}

	public void setRedirect(Redirect redirect) {
		this.redirect = redirect;
		client = HttpClient.newBuilder()
	      		  .followRedirects(redirect )
	      		  .build();
	}

	private Builder getBuilder(URI anUri) {
        Builder reqBuilder = HttpRequest.newBuilder().uri(anUri);
        Set<Map.Entry<String, String>> entries = headers.entrySet();
        
        for (Map.Entry<String, String> entry : entries) {
            String key = entry.getKey();
            String value = entry.getValue();
            reqBuilder = reqBuilder.setHeader(key, value);
        }
        return reqBuilder.version(httpVersion );
	}

	// GET
	public HttpResponse<String> sendHttpGet(URI anUri) throws IOException, InterruptedException{
		return sendHttpGet(anUri,defaultConnectionTimeout);
	}

	public HttpResponse<String> sendHttpGet(URI anUri, Duration connectionTimeout) throws IOException, InterruptedException{
		HttpResponse<String> strResponse=null;
			HttpRequest request = getBuilder(anUri).GET().timeout(connectionTimeout).build();
	        //String body handler
	        strResponse = client.send(request, HttpResponse.BodyHandler.asString());
	    return strResponse;
		
	}
	// POST
	public HttpResponse<String> sendHttpPost(URI anUri, String data) throws IOException, InterruptedException{
		return sendHttpPost(anUri, data, defaultConnectionTimeout);
	}

	public HttpResponse<String> sendHttpPost(URI anUri, String data, Duration connectionTimeout) throws IOException, InterruptedException{
		HttpResponse<String> strResponse=null;
	        HttpRequest request = getBuilder(anUri).POST(BodyPublisher.fromString(data)).timeout(connectionTimeout).build();
	        strResponse = client.send(request, HttpResponse.BodyHandler.asString());
	    return strResponse;
		
	}
	// PUT
	public HttpResponse<String> sendHttpPut(URI anUri, String data) throws IOException, InterruptedException{
		return sendHttpPut(anUri, data, defaultConnectionTimeout);
	}

	public HttpResponse<String> sendHttpPut(URI anUri, String data, Duration connectionTimeout) throws IOException, InterruptedException{
		HttpResponse<String> strResponse=null;
	        HttpRequest request = getBuilder(anUri).PUT(BodyPublisher.fromString(data)).timeout(connectionTimeout).build();
	        strResponse = client.send(request, HttpResponse.BodyHandler.asString());
	    return strResponse;
		
	}


	public void setHeader(String name, String value) {
		headers.put(name, value);
		
	}

}
