package de.nikolauspflege.bbw.fiae.http;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.sun.net.httpserver.HttpServer;

import de.nikolauspflege.bbw.fiae.http.server.mini.EchoHandler;
import de.nikolauspflege.bbw.fiae.http.server.mini.HeaderHandler;
import de.nikolauspflege.bbw.fiae.http.server.mini.JsonHandler;
import de.nikolauspflege.bbw.fiae.http.server.mini.RedirectHandler;
import de.nikolauspflege.bbw.fiae.http.server.mini.RedirectTargetHandler;
import de.nikolauspflege.bbw.fiae.http.server.mini.RootHandler;
import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpResponse;

class ManagedHttpConnectionTest {

	private static HttpServer server;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		server = HttpServer.create(new InetSocketAddress(InetAddress.getLoopbackAddress(), 7080),0);
		server.createContext("/", new RootHandler());
		server.createContext("/header", new HeaderHandler());
		server.createContext("/echo", new EchoHandler());
		server.createContext("/redirect",new RedirectHandler());
		server.createContext("/redirect/target",new RedirectTargetHandler());
		server.createContext("/json",new JsonHandler());
		server.setExecutor(null);
		server.start();
		System.out.println("started MiniServer");
/*
		pb= new ProcessBuilder("java", "-cp", "target/test-classes/",  "de.nikolauspflege.bbw.fiae.http.server.mini.MiniServer");
		File log = new File("log");
		pb.redirectErrorStream(true);
		pb.redirectOutput(Redirect.appendTo(log));
	
		process = pb.start();
		System.out.println("started MiniServer");
		System.out.println(process.toString());
		TimeUnit.SECONDS.sleep(1); */
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		System.out.println("stopping MiniServer");
		server.stop(3);
		/*System.out.println(process.toString());
		process.destroy();
		System.out.println(process.toString());*/
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testSendHttpsGet() throws URISyntaxException, IOException, InterruptedException {
	ManagedHTTPConnection con = new ManagedHTTPConnection();
	HttpResponse<String> resp = con.sendHttpsGet(new URI( "http://localhost:7080"));
	System.out.println(resp.body());
	assertTrue(resp.body().contains("MiniServer"), "Word MiniServer not found in response");
//	fail("Not yet implemented");
}
	@Test
	void testSetHeaders() throws URISyntaxException, IOException, InterruptedException {
	ManagedHTTPConnection con = new ManagedHTTPConnection();
	Map<String, String> headers=new HashMap<String, String>();
	headers.put("HeaderKey", "HeaderValue");
	con.setHeaders(headers);
	HttpResponse<String> resp = con.sendHttpsGet(new URI( "http://localhost:7080/headers/"));
	System.out.println(resp.body());
	assertTrue((resp.body().contains("HeaderKey")||resp.body().contains("Headerkey")), "Word HeaderKey not found in response");
//	fail("Not yet implemented");
}	
	@Test
	void testRunRedirect1() throws URISyntaxException, IOException, InterruptedException {
		ManagedHTTPConnection con = new ManagedHTTPConnection();
		HttpResponse<String> resp = con.sendHttpsGet(new URI( "http://localhost:7080/redirect/"));
		System.out.println(resp.body());
		assertTrue(resp.body().contains("You have been redirected to here"), "correct redirect response not found");

	//	fail("Not yet implemented");
	}
	@Test
	void testRunRedirect2() throws URISyntaxException, IOException, InterruptedException {
		ManagedHTTPConnection con = new ManagedHTTPConnection();
		assertTrue (con.getRedirect() == HttpClient.Redirect.ALWAYS, "initial status of redirect wrong" ) ;
		con.setRedirect(HttpClient.Redirect.NEVER);
		assertTrue (con.getRedirect() == HttpClient.Redirect.NEVER, "status of redirect not changed" ) ;
	}
	@Test
	void testRunRedirect3() throws URISyntaxException, IOException, InterruptedException {
		ManagedHTTPConnection con = new ManagedHTTPConnection();
		con.setRedirect(HttpClient.Redirect.NEVER);
		HttpResponse<String> resp = con.sendHttpsGet(new URI( "http://localhost:7080/redirect/"));
		System.out.println(resp.body());
		System.out.println(resp.statusCode());
		assertTrue(resp.statusCode() == 301 , "Status 301 not found, redirect not blocked");
	//	fail("Not yet implemented");
	}
	@Test
	void testDefaultHeaders() throws URISyntaxException, IOException, InterruptedException {
		ManagedHTTPConnection con = new ManagedHTTPConnection();
		HttpResponse<String> resp = con.sendHttpsGet(new URI( "http://localhost:7080/headers"));
		System.out.println(resp.body());
		System.out.println(resp.statusCode());
		assertTrue(resp.body().contains("text/html,application/xhtml+xml,aplication/xml;q=0.9,*\\/*;q=0.8")
				&&resp.body().contains("de,en-US;q=0.8,en;q=0.5")
				&&resp.body().contains("keep-alive") , "default headers not found");
	}
	@Test
	void testSetHeader() throws URISyntaxException, IOException, InterruptedException {
		ManagedHTTPConnection con = new ManagedHTTPConnection();
		con.setHeader("Accept","application/json");
		HttpResponse<String> resp = con.sendHttpsGet(new URI( "http://localhost:7080/headers"));
		System.out.println(resp.body());
		System.out.println(resp.statusCode());
		assertTrue(resp.body().contains("application/json"), "header value for accept not correct");
	}
	@Test
	void testgetJson() throws URISyntaxException, IOException, InterruptedException {
		ManagedHTTPConnection con = new ManagedHTTPConnection();
		con.setHeader("Accept","application/json");
		HttpResponse<String> resp = con.sendHttpsGet(new URI( "http://localhost:7080/json"));
		assertEquals(200, resp.statusCode(), "Status code not 200");
		System.out.println(resp.statusCode());
		assertNotNull(resp.body(), "no response delivered");
		System.out.println(resp.body());
		JSONObject jo = new JSONObject(resp.body());
		JSONArray jarr = jo.getJSONArray("locations");
		String stationName = jarr.getJSONObject(0).getString("name");
		String stationId = jarr.getJSONObject(0).getString("id");
		System.out.println( " Station Name is: " + stationName);
		System.out.println( " Station ID is: " + stationId);

		assertEquals("de:08111:6118", stationId, "Station ID not de:08111:6118:1:102");
		
//		assertTrue(resp.body().contains("application/json"), "header value for accept not correct");
	}

}
