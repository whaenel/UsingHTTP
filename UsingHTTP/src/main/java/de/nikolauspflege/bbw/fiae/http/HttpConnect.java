package de.nikolauspflege.bbw.fiae.http;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.net.ssl.SSLParameters;

import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;

public class HttpConnect {

	
	public static HttpResponse<String> sendHttpsGet(URI anUri){
		HttpResponse<String> strResponse=null;
        try {
            HttpClient client = HttpClient.newBuilder()
            		  .followRedirects(HttpClient.Redirect.ALWAYS)
            		  .build();
            HttpRequest request = HttpRequest.newBuilder()
                    //.uri(new URI("https://javadeveloperzone.com/java-basic/java-9-features/java-9-module-example/"))
                    .uri(anUri)
                    .GET()
                    .build();
            //String body handler
            strResponse = client.send(request, HttpResponse.BodyHandler.asString());
            System.out.println(anUri.toString());
            System.out.println(strResponse.statusCode());
            SSLParameters sslParameters = strResponse.sslParameters();
            
            System.out.println("SSLParms : "+strResponse.sslParameters().toString());
            System.out.println("Maximum packet size : "+sslParameters.getMaximumPacketSize());
            //System.out.println(strResponse.body());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strResponse;
		
	}
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
		try {
			strResponse = sendHttpsGet(new URI("https://javadeveloperzone.com/java-basic/java-9-features/java-9-module-example/"));
			//strResponse = sendHttps(new URI("https://javadeveloperzone.com/java-9/java-9-module-example/"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

	

        
	}

}
