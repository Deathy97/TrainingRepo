package es.rafa.trainingRepo.util;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient.Version;
import java.util.concurrent.CompletableFuture;

public class Utils {

	public CompletableFuture<HttpResponse<String>> doGetRequest(String url) {
		HttpClient client = HttpClient.newBuilder().version(Version.HTTP_2).build();

		HttpRequest request = HttpRequest.newBuilder(URI.create(url)).GET().build();

		CompletableFuture<HttpResponse<String>> response = client.sendAsync(request,
				HttpResponse.BodyHandlers.ofString());
		return response;
	}
}
