package hu.vizicsaba.restapidemo;

import hu.vizicsaba.restapidemo.service.component.UserService;
import hu.vizicsaba.restapidemo.service.model.UserResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.authentication.JdbcUserServiceBeanDefinitionParser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class RestApiDemoApplication {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Autowired
	private UserService userService;

	@Component("userSecurity")
	public class UserSecurity {
		public boolean hasUserId(Authentication authentication, Long userId) {
			UserResponse userById = userService.getUserById(userId);
			User userPrincipal = (User) authentication.getPrincipal();

			return userPrincipal.getUsername().equals(userById.getUserName());
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(RestApiDemoApplication.class, args);
	}

}
