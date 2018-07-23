package de.nikolauspflege.bbw.fiae.http;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;

public class HttpConnect {

	
	public static void main(String[] args) {
		/*
		HttpResponse<String> strResponseS = null;
		try {
			strResponseS = sendHttps(new URI("https://javadeveloperzone.com/java-9/java-9-module-example/"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

		*/
		
		// TODO Auto-generated method stub
        try {
    		HttpClient client = HttpClient.newBuilder().build();
			HttpRequest request = HttpRequest.newBuilder(new URI("http://httpbin.org/get"))
			        .GET()
			        .version(HttpClient.Version.HTTP_1_1)
			        .build();
	        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandler.asString());
	        System.out.println("Status code: " + response.statusCode());
	        System.out.println("Response Body: " + response.body());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        System.out.println("HTTPS: ------------------------------------------------ " );
		HttpResponse<String> strResponse = null;
		ManagedHTTPConnection con = new ManagedHTTPConnection();
		try {
			strResponse = con.sendHttpsGet(new URI("https://javadeveloperzone.com/java-basic/java-9-features/java-9-module-example/"));
			//strResponse = sendHttps(new URI("https://javadeveloperzone.com/java-9/java-9-module-example/"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	

        
	}

}
