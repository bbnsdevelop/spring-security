package com.security.service;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.security.model.Role;
import com.security.model.User;
import com.security.repository.RoleRepository;
import com.security.repository.UserRepository;

@Service("userService")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	@Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@SuppressWarnings("null")
	@Override
	public void saveUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(1);
        Role userRole = roleRepository.findByRole("ADMIN");
        if(userRole != null) {
        	user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));        	
        }
        else {
        	userRole.setRole("ADMIN");
        	userRole = roleRepository.save(userRole);
        	user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        }
		userRepository.save(user);
	}

}