package com.back.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

import javax.validation.Valid;

import com.back.models.Role;
import com.back.payload.ApiResponse;
import com.back.payload.RoleDto;
import com.back.payload.UserDto;
import com.back.service.UserService;

@RestController
@RequestMapping("/users")

public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private ModelMapper mapper;
	
	
	//201-created
	@PostMapping("/")
	public ResponseEntity<UserDto>  createUser(@Valid @RequestBody UserDto userDto ) {
		userDto.setCreateAt(new Date());
		userDto.setActive(true);
		/*
		 * Role role2 = new Role(); role2.setId(7412); role2.setName("ROLE_NORMAL");
		 * Set<RoleDto> roles = new HashSet<>(); roles.add(this.mapper.map(role2,
		 * RoleDto.class)); userDto.setRoles(roles);
		 */
		userDto.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
		UserDto createdUser = userService.create(userDto);
		System.out.println("SUCCESSFULLY CREATED");
		return new ResponseEntity<UserDto>(createdUser,HttpStatus.CREATED);
		
	}
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse>  deleteUser(@PathVariable int userId) {
		userService.delete(userId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("deleted successfully", true), HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<UserDto>>  getAllUser(){
		List<UserDto> allUsers = userService.getAll();
		return new ResponseEntity<List<UserDto>>(allUsers,HttpStatus.OK);
	}
	
	@GetMapping("/{email}")
	public ResponseEntity<UserDto>  getUserByEmail(@PathVariable String email) {
		UserDto byEmail = userService.getByEmail(email);
		return new ResponseEntity<UserDto>(byEmail,HttpStatus.OK);
	}
	
	@GetMapping("/byId/{userId}")
	public ResponseEntity<UserDto>  getUserById(@PathVariable int userId) {
		UserDto byUserId = userService.getByUserId(userId);
		return new ResponseEntity<UserDto>(byUserId,HttpStatus.OK);
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable int userId) {
		UserDto updatedUser = this.userService.update(userDto, userId);
		return new ResponseEntity<UserDto>(updatedUser, HttpStatus.OK);
	}
	
	}

