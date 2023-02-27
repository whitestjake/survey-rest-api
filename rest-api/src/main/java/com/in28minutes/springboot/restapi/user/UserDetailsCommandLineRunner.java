package com.in28minutes.springboot.restapi.user;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsCommandLineRunner implements CommandLineRunner {
	
	public UserDetailsCommandLineRunner(UserDetailsRepository repository) {
		super();
		this.repository = repository;
	}
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private UserDetailsRepository repository;

	@Override
	public void run(String... args) throws Exception {
		
		repository.save(new UserDetails("Jake", "Admin"));
		repository.save(new UserDetails("John", "User"));
		repository.save(new UserDetails("Josh", "Admin"));
		
		List<UserDetails> users = repository.findByRole("Admin");
		users.forEach(user -> logger.info(user.toString()));
		
		
	}

}
