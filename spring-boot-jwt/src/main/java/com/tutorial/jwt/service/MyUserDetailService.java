package com.tutorial.jwt.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tutorial.jwt.dao.MainDao;
import com.tutorial.jwt.model.UserDetail;

@Service
public class MyUserDetailService implements UserDetailsService{
	
	@Autowired
	private MainDao mainDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetail user = mainDao.getUsername(username);
//		UserDetail user = new UserDetail("te", "1234");
		return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
	}

	public List<UserDetail> getUsers() {
		List<UserDetail> userList = mainDao.getUsers();
		return userList;
	}

}
