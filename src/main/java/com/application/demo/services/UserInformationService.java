package com.application.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.application.demo.entity.postgres.UserInformation;
import com.application.demo.respository.postgres.UserInformationDao;

@Service("userInformationService")
public class UserInformationService {

	@Autowired
	private UserInformationDao userInformationDao;

    public UserInformation getUserDetailByLogin(String username, String password) {
    	return userInformationDao.getUserDetailByLogin(username, password);
    }
    
}
