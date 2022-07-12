package com.back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

import com.back.payload.UserDto;
import com.back.service.UserService;

@RestController
@RequestMapping("/User")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/")
	public UserDto createUser(@RequestBody UserDto userDto ) {
		UserDto createdUser = userService.create(userDto);
		System.out.println("SUCCESSFULLY CREATED");
		return createdUser;
		
	}
	@DeleteMapping("/{userId}")
	public String deleteUser(@PathVariable int userId) {
		userService.delete(userId);
		return "successfully deleted";
	}
	
	@GetMapping("/")
	public List<UserDto> getAllUser(){
		List<UserDto> allUsers = userService.getAll();
		return allUsers;
	}
	
	@GetMapping("/{email}")
	public UserDto getUserByEmail(@PathVariable String email) {
		UserDto byEmail = userService.getByEmail(email);
		return byEmail;
	}
	
	@GetMapping("/byId/{userId}")
	public UserDto getUserById(@PathVariable int userId) {
		UserDto byUserId = userService.getByUserId(userId);
		return byUserId;
	}
	
	}

