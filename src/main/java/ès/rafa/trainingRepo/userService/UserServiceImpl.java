package ès.rafa.trainingRepo.userService;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.rafa.trainingRepo.bean.UserBean;

public class UserServiceImpl implements UserService {

	@Override
	public List<UserBean> getAllUsers() {
		ObjectMapper mapper = new ObjectMapper();
		HttpClient client = HttpClient.newBuilder().version(Version.HTTP_2).build();

		HttpRequest request = HttpRequest.newBuilder(URI.create("https://api.github.com/users")).GET().build();

		CompletableFuture<HttpResponse<String>> response = client.sendAsync(request,
				HttpResponse.BodyHandlers.ofString());
		List<UserBean> userList = null;
		try {
			userList = mapper.readValue(response.get().body(), new TypeReference<List<UserBean>>() {
			});
		} catch (JsonProcessingException | InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

		return userList;
	}

}
