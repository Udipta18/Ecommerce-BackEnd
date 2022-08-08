package com.back.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.back.exception.BadUserLoginDetailsException;
import com.back.payload.JwtRequest;
import com.back.payload.JwtResponse;
import com.back.payload.UserDto;
import com.back.security.JwtHelper;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtHelper jwtHelper;
	
	@Autowired
	private ModelMapper mapper;
	
	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) throws Exception{
		
		
		this.authenticateUser(request.getUsername(),request.getPassword());
		
		
		   UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
		     String token = this.jwtHelper.generateToken(userDetails);
		     
		     JwtResponse jwtResponse=new JwtResponse();
		     jwtResponse.setToken(token);
		     jwtResponse.setUser(this.mapper.map(userDetails, UserDto.class));
		     return new ResponseEntity<>(jwtResponse,HttpStatus.OK);
		   
		
	}
	
	private void authenticateUser(String username,String password) throws Exception {
		
		try {
	     authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		}
		catch(BadCredentialsException e) {
			throw new BadUserLoginDetailsException("Invalid Username and Password");
		}
		catch(DisabledException e)
		{
			throw new BadUserLoginDetailsException("User is not active");
		}
		
		}
		
}
