package com.ead.authuser.adapter.configuration;

import com.ead.authuser.adapter.repository.UserRepository;
import com.ead.authuser.application.services.UserService;
import com.ead.authuser.application.services.impl.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfig {

    @Bean
    public UserService userUseCase(UserRepository userRepository){
        return new UserServiceImpl(userRepository);
    }

}
