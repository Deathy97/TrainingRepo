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
		List<UserBean> userList = null;
		String response = null;

		try {
			userList = getUsers();
			response = buildResponse(userList);
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

	private String buildResponse(List<UserBean> userList) {
		// NOT best way
		String header = "<html><body>";
		String footer = "</body></html>";
		String tableHead = " <table><tr><th>ID</th><th>Login</th><th>Image</th>";
		String tableFooter = "</tr></table>";

		StringBuilder body = new StringBuilder();
		body.append(tableHead);
		userList.stream()
				.forEach(user -> body.append("<tr><td style=' border: 1px solid black;'>" + user.getId()
						+ "</td><td style=' border: 1px solid black;'>" + user.getLogin()
						+ "</td><td style=' border: 1px solid black;'><img src='" + user.getAvatar_url()
						+ "' width='80'></td></tr>"));
		body.append(tableFooter);

		StringBuilder result = new StringBuilder();
		result.append(header);
		result.append(body);
		result.append(footer);

		return result.toString();
	}

	private List<UserBean> getUsers() throws IOException, InterruptedException, ExecutionException {
		ObjectMapper mapper = new ObjectMapper();
		HttpClient client = HttpClient.newBuilder().version(Version.HTTP_2).build();

		HttpRequest request = HttpRequest.newBuilder(URI.create("https://api.github.com/users")).GET().build();

		CompletableFuture<HttpResponse<String>> response = client.sendAsync(request,
				HttpResponse.BodyHandlers.ofString());
		List<UserBean> userList = mapper.readValue(response.get().body(), new TypeReference<List<UserBean>>() {
		});

		return userList;
	}

}
