package de.nikolauspflege.bbw.fiae.http;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;

public class HttpConnect {

	public static void run(String[] args) {
		ManagedHTTPConnection conn = new ManagedHTTPConnection();
		HttpResponse<String> response =null;
		URI myUri;
		try {
			myUri = new URI("http://httpbin.org/get");
			if (args.length == 1) {
				myUri = new URI(args[0]);
			}

			response = conn.sendHttpsGet(myUri);
		} catch (IOException | InterruptedException | URISyntaxException e) {
			System.out.println("request failed  " );
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("headers = " + response.headers().toString());
		System.out.println("length =" + response.body().length());
		if (response.body().length() > 0 ) {
			System.out.println(response.body().toString());
		}
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < args.length; i++) {
			System.out.println(args[i]) ;
		}
		System.out.println("-----------------------") ;
		run(args);

        
	}

}
