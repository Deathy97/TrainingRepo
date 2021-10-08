package es.rafa.trainingRepo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ès.rafa.trainingRepo.userService.UserService;
import ès.rafa.trainingRepo.userService.UserServiceImpl;

@Configuration
public class Config {
	
    @Bean
    public UserService getUserService() {
        return new UserServiceImpl();
    }

}
