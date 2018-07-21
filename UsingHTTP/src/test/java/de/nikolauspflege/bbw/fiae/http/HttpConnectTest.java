package de.nikolauspflege.bbw.fiae.http;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.lang.ProcessBuilder.Redirect;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
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

class HttpConnectTest {

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
	void testSendHttpsGet() throws URISyntaxException {
		HttpResponse<String> resp = HttpConnect.sendHttpsGet(new URI( "http://localhost:7080"));
		System.out.println(resp.body());
//		fail("Not yet implemented");
	}

}
