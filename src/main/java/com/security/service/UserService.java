package com.security.service;


import com.security.model.User;

public interface UserService {
	public User findUserByEmail(String email);
	public void saveUser(User user);
}