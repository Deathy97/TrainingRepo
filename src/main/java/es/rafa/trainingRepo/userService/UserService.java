package es.rafa.trainingRepo.userService;

import java.util.List;

import es.rafa.trainingRepo.bean.UserBean;

public interface UserService {
	
	public List<UserBean> getAllUsers();

	public void loadDataFromApi();

}
