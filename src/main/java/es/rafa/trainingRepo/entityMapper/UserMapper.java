package es.rafa.trainingRepo.entityMapper;

import es.rafa.trainingRepo.bean.UserBean;

public class UserMapper {

	public UserBean toBean(int id, String name, String avatar) {
		UserBean user = new UserBean();
		user.setId(id);
		user.setLogin(name);
		user.setAvatar_url(avatar);

		return user;

	}

}
