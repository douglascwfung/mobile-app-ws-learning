package net.icestone.mobileapp.ws.ui.controller;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.icestone.mobileapp.ws.service.UserService;
import net.icestone.mobileapp.ws.shared.dto.UserDto;
import net.icestone.mobileapp.ws.ui.model.request.UserDetailsRequestModel;
import net.icestone.mobileapp.ws.ui.model.response.ErrorMessages;
import net.icestone.mobileapp.ws.ui.model.response.OperationStatusModel;
import net.icestone.mobileapp.ws.ui.model.response.RequestOperationName;
import net.icestone.mobileapp.ws.ui.model.response.RequestOperationStatus;
import net.icestone.mobileapp.ws.ui.model.response.UserRest;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserService userService;
	
//	@GetMapping
//	public String getUser() {
//		return "get user was called";
//	}

	@GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public UserRest getUser(@PathVariable String id) 
	{
		
		UserRest returnValue = new UserRest();

		UserDto userDto = userService.getUserByUserId(id);
		
		ModelMapper modelMapper = new ModelMapper();
		
		returnValue = modelMapper.map(userDto, UserRest.class);

		return returnValue;
	}
	
	
	@GetMapping
	public List<UserRest> getUsers() {
		
		List<UserRest> returnValue = new ArrayList<>();
		
		List<UserDto> users = userService.getAllUsers();
		
		Type listType = new TypeToken<List<UserRest>>() {
		}.getType();

		returnValue = new ModelMapper().map(users, listType);

		/*for (UserDto userDto : users) {
			UserRest userModel = new UserRest();
			BeanUtils.copyProperties(userDto, userModel);
			returnValue.add(userModel);
		}*/

		return returnValue;	
		
	}
	
	
	@PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public UserRest createUser( @RequestBody UserDetailsRequestModel userDetails) throws Exception {
		
		UserRest returnValue = new UserRest();
		
//		if(userDetails.getFirstName().isEmpty()) throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());		
		if(userDetails.getFirstName().isEmpty()) throw new NullPointerException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userDetails, userDto);
		
		UserDto createdUser = userService.createUser(userDto);
		BeanUtils.copyProperties(createdUser, returnValue);
		
		return returnValue;
	}

	@PutMapping(path = "/{userId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
//	public UserRest updateUsers(@PathVariable String userId, @Valid @RequestBody UpdateUserDetailsRequestModel userDetails) {
	public UserRest updateUsers(@PathVariable String userId, @RequestBody UserDetailsRequestModel userDetails) 
	{
		
		System.out.println("userDetails");
		System.out.println(userDetails.getFirstName());
		System.out.println(userDetails.getLastName());
		
		UserRest returnValue = new UserRest();
		
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userDetails, userDto);
		
		System.out.println("userDto");
		System.out.println(userDto.getFirstName());
		System.out.println(userDto.getLastName());
		
		UserDto updatedUser = userService.updateUser(userId, userDto);
		
		BeanUtils.copyProperties(updatedUser, returnValue);
		
		return returnValue;
		
	}

	@DeleteMapping(path = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE , MediaType.APPLICATION_XML_VALUE })
	public OperationStatusModel deleteUser(@PathVariable String id) 
	{
		
		OperationStatusModel returnValue = new OperationStatusModel();
		
		returnValue.setOperationName(RequestOperationName.DELETE.name());

		userService.deleteUser(id);

		returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
		
		return returnValue;
	}

	
	
}
