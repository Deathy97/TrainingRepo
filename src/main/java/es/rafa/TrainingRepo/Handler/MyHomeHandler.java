package es.rafa.TrainingRepo.Handler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.apache.log4j.Logger;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class MyHomeHandler implements HttpHandler {

	final static Logger logger = Logger.getLogger(MyHomeHandler.class);

	public void handle(HttpExchange exchange) throws IOException {
		String response = null;
		try {
			response = getUsers();
		} catch (IOException e) {
			logger.error("Error while geting response", e);
		} catch (InterruptedException e) {
			logger.error("Error in the connection", e);
		} catch (ExecutionException e) {
			logger.error("Error while making the request", e);
		}
		exchange.sendResponseHeaders(200, response.length());

		OutputStream os = exchange.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}

	private String getUsers() throws IOException, InterruptedException, ExecutionException {
		HttpClient client = HttpClient.newBuilder().version(Version.HTTP_2).build();

		HttpRequest request = HttpRequest.newBuilder(URI.create("https://api.github.com/users")).GET().build();

		CompletableFuture<HttpResponse<String>> response = client.sendAsync(request,
				HttpResponse.BodyHandlers.ofString());

		return response.get().body().toString();
	}

}
