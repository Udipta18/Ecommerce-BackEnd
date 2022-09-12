package com.back.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.back.security.JwtAuthenticationFilter;
import com.back.security.jwtAuthenticationEntryPoint;

import org.springframework.security.config.annotation.web.configuration.*;




@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)//this annotation is basically used for restrict any method in controller in basis of admin or normal user
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsService userDetailsService; 
	
	public String[] PUBLIC_URLS= {
			 "/users/",
	         "/auth/login",
	         "/v3/api-docs",
	         "/v2/api-docs",
	         "/swagger-resources/**",
	         "/swagger-ui/**",
	         "/webjars/**"
	};
	
	@Autowired
	private jwtAuthenticationEntryPoint entryPoint;
	
	@Autowired
	private JwtAuthenticationFilter filter;

	
	//this method for database authentication
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	auth.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder());
		
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		    .csrf()
		    .disable()
		    .authorizeRequests()
		    .antMatchers(PUBLIC_URLS).permitAll()
		    .antMatchers(HttpMethod.GET).permitAll()
		    .anyRequest()
		    .authenticated()
		    .and()//it is because if some unauthorized person trying to use the api's then exception will be handled by authentication entry point class
		    .exceptionHandling().authenticationEntryPoint(entryPoint)
		    .and()
		    .sessionManagement()
		    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);//it is because jwt works in stateless mode i.e no data stored in server
		
		
		//to add filter before every request 
		http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
		    
	}
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	
	
	
	/*
	 * this method basically used to authenticate username password that we get from
	 * requestbody from auth controller and bean tagged so we can authowired it
	 * where needed
	 */
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}
	
	    @Bean
	    public FilterRegistrationBean corsFilter() {

	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

	        CorsConfiguration configuration = new CorsConfiguration();

	        configuration.setAllowCredentials(true);
	        configuration.addAllowedOriginPattern("*");
	        configuration.addAllowedHeader("Authorization");
	        configuration.addAllowedHeader("Content-Type");
	        configuration.addAllowedHeader("Accept");
	        configuration.addAllowedMethod("POST");
	        configuration.addAllowedMethod("GET");
	        configuration.addAllowedMethod("DELETE");
	        configuration.addAllowedMethod("PUT");
	        configuration.addAllowedMethod("OPTIONS");
	        configuration.setMaxAge(3600L);


	        source.registerCorsConfiguration("/**", configuration);


	        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
	        bean.setOrder(-110);
	        return bean;
	    }

	
	
	
        
}
