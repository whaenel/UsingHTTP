package de.nikolauspflege.bbw.fiae.http.server.mini;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

public class MiniServer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			HttpServer server = HttpServer.create(new InetSocketAddress(InetAddress.getLoopbackAddress(), 7080),0);
			server.createContext("/", new RootHandler());
			server.createContext("/header", new HeaderHandler());
			server.createContext("/echo", new EchoHandler());
			server.setExecutor(null);
			server.start();
			System.out.println("Server stared on host " + server.getAddress().getHostString() + " and port " + server.getAddress().getPort());
		} catch (IOException e) {
			// print stack trace and exit in case of error
			e.printStackTrace();
			return;
		}
		
		
		
	}

}
