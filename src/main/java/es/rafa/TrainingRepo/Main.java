package es.rafa.TrainingRepo;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

import es.rafa.TrainingRepo.Handler.MyHomeHandler;

public class Main {

	public static void main(String[] args) throws IOException {
		System.out.println("Starting server....");
		HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
		server.createContext("/", new MyHomeHandler());
		server.setExecutor(null);
		server.start();
	}
}
