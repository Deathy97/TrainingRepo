package es.rafa.TrainingRepo.Handler;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.log4j.Logger;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class MyHomeHandler implements HttpHandler {
	final static Logger logger = Logger.getLogger(MyHomeHandler.class);

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		String response = "Hola usuario";
		exchange.sendResponseHeaders(200, response.getBytes().length);

		OutputStream os = exchange.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}

}
