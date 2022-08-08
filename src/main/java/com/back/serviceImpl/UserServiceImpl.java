package com.back.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.back.exception.ResourceNotFoundException;
import com.back.models.Role;
import com.back.models.User;
import com.back.payload.UserDto;
import com.back.repo.RoleRepository;
import com.back.repo.UserRepository;
import com.back.service.UserService;


@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public UserDto create(UserDto userDto) {
		// TODO Auto-generated method stub
		User u = this.toEntity(userDto);
		  Role role = this.roleRepository.findById(7412).get();
		  u.getRoles().add(role);
		User createdUser = userRepository.save(u);
		return this.toDto(createdUser);
	}

	@Override
	public UserDto update(UserDto t, int userId) {
		// TODO Auto-generated method stub
		User u = this.userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with userId " + userId));

		u.setName(t.getName());
		u.setEmail(t.getEmail());
		u.setPassword(t.getPassword());
		u.setAbout(t.getAbout());
		u.setAddress(t.getAddress());
		u.setActive(t.isActive());
		u.setGender(t.getGender());
		u.setCreateAt(t.getCreateAt());
		u.setPhone(t.getPhone());

		User updatedUser = this.userRepository.save(u);

		return this.toDto(updatedUser);
	}

	@Override
	public void delete(int userId) {
		// TODO Auto-generated method stub

          User user = userRepository.findById(userId).orElseThrow(() ->new ResourceNotFoundException("user not found"+userId));
		userRepository.delete(user);
	}

	@Override
	public List<UserDto> getAll() {
		// TODO Auto-generated method stub
		List<User> alluser = userRepository.findAll();
		
		List<UserDto> allDto = alluser.stream().map(user -> this.toDto(user)).collect(Collectors.toList());
		return allDto;
	}

	@Override
	public UserDto getByUserId(int userId) {
		// TODO Auto-generated method stub
		User userById = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user id not found"+userId));
		return this.toDto(userById);
	}

	@Override
	public UserDto getByEmail(String email) {
		// TODO Auto-generated method stub
		 User userByEmail = userRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("email not found"+email));
		return this.toDto(userByEmail);
	}
	
	public UserDto toDto(User user) {
		return this.mapper.map(user, UserDto.class);
	}

	public User toEntity(UserDto t) {
		  return this.mapper.map(t, User.class);
	}

}
