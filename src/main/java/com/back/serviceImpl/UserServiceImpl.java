package com.back.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.back.exception.ResourceNotFoundException;
import com.back.models.User;
import com.back.payload.UserDto;
import com.back.repo.UserRepository;
import com.back.service.UserService;


@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDto create(UserDto userDto) {
		// TODO Auto-generated method stub
		User u = this.toEntity(userDto);
		User createdUser = userRepository.save(u);
		return this.toDto(createdUser);
	}

	@Override
	public UserDto update(UserDto userDto, int userId) {
		// TODO Auto-generated method stub
		return null;
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
		UserDto dto = new UserDto();
		dto.setUserId(user.getUserId());
		dto.setName(user.getName());
		dto.setEmail(user.getEmail());
		dto.setPassword(user.getPassword());
		dto.setAbout(user.getAbout());
		dto.setAddress(user.getAddress());
		dto.setActive(user.isActive());
		dto.setGender(user.getGender());
		dto.setCreateAt(user.getCreateAt());
		dto.setPhone(user.getPhone());

		return dto;
	}

	public User toEntity(UserDto t) {
		User u = new User();
		u.setUserId(t.getUserId());
		u.setName(t.getName());
		u.setEmail(t.getEmail());
		u.setPassword(t.getPassword());
		u.setAbout(t.getAbout());
		u.setAddress(t.getAddress());
		u.setActive(t.isActive());
		u.setGender(t.getGender());
		u.setCreateAt(t.getCreateAt());
		u.setPhone(t.getPhone());

		return u;
	}

}
