package es.rafa.trainingRepo.userService;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.rafa.trainingRepo.bean.UserBean;
import es.rafa.trainingRepo.exception.HttpRequestException;
import es.rafa.trainingRepo.repository.UserRepository;
import es.rafa.trainingRepo.util.Utils;

public class UserServiceImpl implements UserService {

	private final String URL = "https://api.github.com/users";

	@Autowired
	UserRepository userRepo;

	@Autowired
	Utils utils;

	@Override
	public List<UserBean> getAllUsers() {
		return userRepo.findAll();
	}

	@Override
	public void loadDataFromApi() {
		CompletableFuture<HttpResponse<String>> response = utils.doGetRequest(URL);

		ObjectMapper mapper = new ObjectMapper();
		List<UserBean> userList = new ArrayList<>();

		try {
			userList = mapper.readValue(response.get().body(), new TypeReference<List<UserBean>>() {
			});
		} catch (JsonProcessingException | InterruptedException | ExecutionException e) {
			throw new HttpRequestException("Error parsing JSON from request", e);
		}

		userList.stream().forEach(user -> userRepo.insertUser(user));
	}

}
