package es.rafa.trainingRepo.userService;

import java.util.List;

import es.rafa.trainingRepo.bean.UserBean;

public interface UserService {
	
	public void loadDataFromApi();
	
	public int getNumberOfPages();
	
	public List<UserBean> getPaginationUsers(int page);

}
