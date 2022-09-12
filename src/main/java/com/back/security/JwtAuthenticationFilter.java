package com.back.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

//adding component annotation so that we can use it in other classes as well
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	Logger logger2 = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

	@Autowired
	private JwtHelper jwtHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		//get the token from header
		//Authorization=Bearer 21334.343hag56.haghh4566
		String requestToken = request.getHeader("Authorization");
		logger2.info("message{} ", requestToken);

		String userName = "";
		String jwtToken = "";

		if (requestToken != null && requestToken.trim().startsWith("Bearer")) {

			jwtToken = requestToken.substring(7);

			try {
				
				//get username from token with the help of jwtHelper class
				userName = this.jwtHelper.getUsernameFromToken(jwtToken);
			} catch (ExpiredJwtException e) {
				logger2.info("Invalid token message {}", "Jwt token expired!!");
			} catch (MalformedJwtException e) {
				logger2.info("Invalid token message {} ", "Invalid Jwt Token");
			} catch (IllegalArgumentException e) {
				logger2.info("Invalid token message {} ", "Unable to get token");
			}

			
			//validate              //this is for check no authentication is already set before hand
			if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);

				if (this.jwtHelper.validateToken(jwtToken, userDetails)) {
                         //authentication need to be set in this step
					UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails,
							null, userDetails.getAuthorities());
					auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

					SecurityContextHolder.getContext().setAuthentication(auth);
				}
				else {
					logger2.info("Not Validated Message {}","Invalid Jwt token");
				}
			}
			else {
				logger2.info("username message {}", "username is null or auth is already there");
			}

		}
		else {
            logger2.info("token message {} ", "token does not starts with bearer");
        }
		
		//request all set after filtration
		filterChain.doFilter(request, response);

	}

}
