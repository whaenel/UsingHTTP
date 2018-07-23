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

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.sun.net.httpserver.HttpServer;

import de.nikolauspflege.bbw.fiae.http.server.mini.EchoHandler;
import de.nikolauspflege.bbw.fiae.http.server.mini.HeaderHandler;
import de.nikolauspflege.bbw.fiae.http.server.mini.RootHandler;
import jdk.incubator.http.HttpResponse;

class ManagedHttpConnectionTest {

	private static ProcessBuilder pb;
	private static Process process;
	private static HttpServer server;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		server = HttpServer.create(new InetSocketAddress(InetAddress.getLoopbackAddress(), 7080),0);
		server.createContext("/", new RootHandler());
		server.createContext("/header", new HeaderHandler());
		server.createContext("/echo", new EchoHandler());
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
	void testSetHeader() throws URISyntaxException, IOException, InterruptedException {
	ManagedHTTPConnection con = new ManagedHTTPConnection();
	Map<String, String> headers=new HashMap<String, String>();
	headers.put("HeaderKey", "HeaderValue");
	con.setHeaders(headers);
	HttpResponse<String> resp = con.sendHttpsGet(new URI( "http://localhost:7080/headers"));
	System.out.println(resp.body());
	assertTrue((resp.body().contains("HeaderKey")||resp.body().contains("Headerkey")), "Word HeaderKey not found in response");
//	fail("Not yet implemented");
}

}