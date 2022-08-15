package net.icestone.mobileapp.ws;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.icestone.mobileapp.ws.service.impl.UserServiceImpl;
import net.icestone.mobileapp.ws.shared.dto.UserDto;
import net.icestone.mobileapp.ws.ui.controller.UserController;
import net.icestone.mobileapp.ws.ui.model.request.UserDetailsRequestModel;
import net.icestone.mobileapp.ws.ui.model.response.UserRest;

@AutoConfigureJsonTesters
@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TestUserController {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserServiceImpl userService;
	
	
	@Autowired
    private JacksonTester<UserRest> jsonUserRest;

	private String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}

	private <T> T mapFromJson(String json, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}

	@Test
	public void getAllUsersAPI() throws Exception {

		// Arrange
		UserDto user1 = new UserDto();
		user1.setFirstName("douglas");
		user1.setLastName("Fung");

		List<UserDto> userList = new ArrayList<UserDto>();

		userList.add(user1);

		when(userService.getAllUsers()).thenReturn(userList);

		// Act
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users").accept(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		
		// Assert

		Assertions.assertEquals(200, mvcResult.getResponse().getStatus());
		String content = mvcResult.getResponse().getContentAsString();
		UserRest[] returnlist = mapFromJson(content, UserRest[].class);
		assertTrue(returnlist.length > 0);
		assertEquals("douglas", returnlist[0].getFirstName());

	}
	
    @Test
    public void testgetUser_canRetrieveByIdWhenExists() throws Exception {
    	

        // Arrange
    	
    	UserDto user1 = new UserDto();
		user1.setFirstName("douglas");
		user1.setLastName("Fung");
		
		UserRest userRest = new UserRest();		
		userRest.setFirstName("douglas");
		userRest.setLastName("Fung");

		when(userService.getUserByUserId("1"))
                .thenReturn(user1);

        // act
        MockHttpServletResponse response = mockMvc.perform(
                get("/users/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // assert
        
        System.out.println(jsonUserRest.write(userRest).getJson());
        
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
        		jsonUserRest.write(userRest).getJson()
        );
    }
    
    @Test
    public void testUpdateUser() throws Exception {
    	
       // Arrange
    	
       String uri = "/users/1";
       UserDetailsRequestModel userDetails = new UserDetailsRequestModel();
       userDetails.setFirstName("Chi Wai");
       String inputJson = mapToJson(userDetails);
       
   		UserDto user1 = new UserDto();
		user1.setFirstName("Chi Wai");
		user1.setLastName("Fung");
		
		when(userService.updateUser(anyString(), any(UserDto.class) ))
        .thenReturn(user1);
       
       
       // Act
//       MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(uri)
//          .contentType(MediaType.APPLICATION_JSON_VALUE)
//          .content(inputJson)).andReturn();
       
       // Assert
//       int status = mvcResult.getResponse().getStatus();
//       
//       assertEquals(200, status);
       
		ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put(uri)
    	          .contentType(MediaType.APPLICATION_JSON_VALUE)
    	          .content(inputJson));
    	       
        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(userDetails.getFirstName())));
       
    }
    
    
    @Test
    public void deleteUser() throws Exception {
    	
       // Arrange
       String uri = "/users/1";
       
       doNothing().when(userService).deleteUser(anyString());
       
//	   when(userService.deleteUser(anyString())).thenReturn("SUCCESS");

       // Act
       MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
       int status = mvcResult.getResponse().getStatus();
       assertEquals(200, status);
//       String content = mvcResult.getResponse().getContentAsString();
//       assertEquals(content, "User is deleted successsfully");
    }
    
}
