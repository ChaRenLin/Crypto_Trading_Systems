package com.example.cryptotradingsystem.service;

import com.example.cryptotradingsystem.model.UserPO;
import com.example.cryptotradingsystem.Constants;
import com.example.cryptotradingsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class UserService {

	private UserRepository userRepository;
	private Constants constants;
	
    @Autowired
    public void setUserRespostory(UserRepository userRes) {
    	this.userRepository = userRes;
    }
    @Autowired
    public void setConstants(Constants constants) {
    	this.constants = constants;
    }
    
    @PostConstruct
    public void initUser() {
        // Initialize the user's wallet balance
        UserPO userPO = new UserPO();
        userPO.setUsername(constants.CONSTANTS_DEFAULT_USER_NAME);
        userPO.setBalance(50000.0); // 50,000 USDT
        userRepository.save(userPO);
    }

    public UserPO getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // More methods...
}
