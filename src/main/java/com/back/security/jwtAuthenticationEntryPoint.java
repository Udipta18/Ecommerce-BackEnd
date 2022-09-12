 package com.back.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

//adding component annotation so that we can use it in other classes as well
@Component
public class jwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		//to set status in response when some unauthorized person trying to  access 
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		//to write some message
		PrintWriter writer=response.getWriter();
		writer.println("Unauthorized Access");
		
	}

}
