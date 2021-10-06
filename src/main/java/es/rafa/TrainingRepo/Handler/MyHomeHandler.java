package es.rafa.TrainingRepo.Handler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import es.rafa.TrainingRepo.bean.UserBean;

public class MyHomeHandler implements HttpHandler {

	final static Logger logger = Logger.getLogger(MyHomeHandler.class);

	public void handle(HttpExchange exchange) throws IOException {
		List<UserBean> response = null;
		String temporalResponse = "Hola Mundo";
		
		try {
			response = getUsers();
		} catch (IOException e) {
			logger.error("Error while geting response", e);
		} catch (InterruptedException e) {
			logger.error("Error in the connection", e);
		} catch (ExecutionException e) {
			logger.error("Error while making the request", e);
		}
		exchange.sendResponseHeaders(200, temporalResponse.length());

		OutputStream os = exchange.getResponseBody();
		os.write(temporalResponse.getBytes());
		os.close();
	}

	private List<UserBean> getUsers() throws IOException, InterruptedException, ExecutionException {
		ObjectMapper mapper = new ObjectMapper();
		HttpClient client = HttpClient.newBuilder().version(Version.HTTP_2).build();

		HttpRequest request = HttpRequest.newBuilder(URI.create("https://api.github.com/users")).GET().build();

		CompletableFuture<HttpResponse<String>> response = client.sendAsync(request,
				HttpResponse.BodyHandlers.ofString());
		List<UserBean> userList = mapper.readValue(response.get().body(), new TypeReference<List<UserBean>>(){});

		return userList;
	}

}
