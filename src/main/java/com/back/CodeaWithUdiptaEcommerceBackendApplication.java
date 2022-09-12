
package com.back;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;

import com.back.models.Role;
import com.back.repo.RoleRepository;

import java.util.*;




@SpringBootApplication
public class CodeaWithUdiptaEcommerceBackendApplication implements CommandLineRunner {
	

	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(CodeaWithUdiptaEcommerceBackendApplication.class, args);
		
	} 
	
	
	@Bean
	public ModelMapper mapper() {
		return new ModelMapper();
	}
 
	/*
	 * CommandLineRunner an interface basically used to run non static things and it
	 * will give us method run to run things once application get started .....Here
	 * we used it basically for set the roles once when application get started
	 * under the run method
	 */
    
	@Override
	public void run(String... args) throws Exception {
		
		try {
		Role role1 = new Role();
        role1.setId(5245);
        role1.setName("ROLE_ADMIN");

        Role role2 = new Role();
        role2.setId(7412);
        role2.setName("ROLE_NORMAL");

        Role role3 = new Role();
        role3.setId(9632);
        role3.setName("ROLE_STAFF");
        
        
        
        this.roleRepository.saveAll(List.of(role1,role2,role3));
        
		}
		catch(Exception e) {
			System.out.println("username already existed");
			e.printStackTrace();
		}

		
	}

}
