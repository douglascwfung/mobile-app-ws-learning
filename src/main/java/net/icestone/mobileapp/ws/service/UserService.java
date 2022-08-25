package net.icestone.mobileapp.ws.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import net.icestone.mobileapp.ws.shared.dto.UserDto;

public interface UserService extends UserDetailsService {
	UserDto createUser(UserDto user);

	UserDto getUser(String email);

	List<UserDto> getAllUsers();

	UserDto getUserByUserId(String userId);

	UserDto updateUser(String userId, UserDto userDto);

	void deleteUser(String userId);

	List<UserDto> getUsers(int page, int limit);

	boolean verifyEmailToken(String token);
}
