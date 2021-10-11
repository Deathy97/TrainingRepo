package es.rafa.trainingRepo.config;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import es.rafa.trainingRepo.database.ConnectionManager;
import es.rafa.trainingRepo.database.H2Connection;
import es.rafa.trainingRepo.entityMapper.UserMapper;
import es.rafa.trainingRepo.repository.UserRepository;
import es.rafa.trainingRepo.userService.UserService;
import es.rafa.trainingRepo.userService.UserServiceImpl;
import es.rafa.trainingRepo.util.Utils;

@Configuration
public class Config {

	@Bean
	public UserService getUserService() {
		return new UserServiceImpl();
	}

	@Bean
	public UserRepository getUserRepository() {
		return new UserRepository();
	}

	@Bean
	public Utils getUtils() {
		return new Utils();
	}

	@Bean
	public ConnectionManager getConnectionManager() {
		return new H2Connection();
	}

	@Bean
	public UserMapper getUserMapper() {
		return new UserMapper();
	}

	@Bean
	public DataSource getDataSource() {
		// Deberia ir en el properties, pero no hay cojones a configurarlo
		DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
		dataSourceBuilder.driverClassName("org.h2.Driver");
		dataSourceBuilder
				.url("jdbc:h2:file:./src/main/resources/test;INIT=RUNSCRIPT FROM 'classpath:initDatabase.sql'");
		dataSourceBuilder.username("sa");
		dataSourceBuilder.password("");

		return dataSourceBuilder.build();
	}

}
