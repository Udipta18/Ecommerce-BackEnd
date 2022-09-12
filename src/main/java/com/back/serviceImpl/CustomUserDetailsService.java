package com.back.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.back.exception.ResourceNotFoundException;
import com.back.models.User;
import com.back.repo.UserRepository;


@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired 
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//load the user from database
		System.out.println("loading user from database");
		User user = this.userRepository.findByEmail(username).orElseThrow(ResourceNotFoundException::new);
		return user;
	}
    
}
