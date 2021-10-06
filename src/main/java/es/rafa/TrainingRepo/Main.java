package es.rafa.TrainingRepo;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

import es.rafa.TrainingRepo.Handler.MyHomeHandler;
import es.rafa.TrainingRepo.Handler.MyUsersHandler;

public class Main {

	public static void main(String[] args) throws IOException {
		System.out.println("Starting....");
		HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
		server.createContext("/", new MyHomeHandler());
		server.createContext("/getAllUsers", new MyUsersHandler());
		server.setExecutor(null);
		server.start();
	}
}
