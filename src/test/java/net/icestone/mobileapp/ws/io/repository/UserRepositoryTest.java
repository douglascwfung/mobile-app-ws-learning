package net.icestone.mobileapp.ws.io.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import net.icestone.mobileapp.ws.io.entity.UserEntity;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
class UserRepositoryTest {

	@Autowired
	UserRepository userRepository;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Order(1)
	@Test
	final void testGetVerifiedUsers() {
		Pageable pageableRequest = PageRequest.of(0, 1);
		Page<UserEntity> page = userRepository.findAllUsersWithConfirmedEmailAddress(pageableRequest);
		assertNotNull(page);
		
        List<UserEntity> userEntities = page.getContent();
        assertNotNull(userEntities);
        System.out.println("userEntities.size(): "+ userEntities.size() );
        
        assertTrue(userEntities.size() == 1);
	}

	@Order(2)
	@Test 
	final void testFindUserByFirstName()
	{
		String firstName="Douglas";
		List<UserEntity> users = userRepository.findUserByFirstName(firstName);
		assertNotNull(users);
		assertTrue(users.size() == 1);
		
		UserEntity user = users.get(0);
		assertTrue(user.getFirstName().equals(firstName));
	}
	
	@Order(3)
	@Test 
	final void testFindUserByLastName()
	{
		String lastName="Fung";
		List<UserEntity> users = userRepository.findUserByLastName(lastName);
		assertNotNull(users);
		assertTrue(users.size() == 1);
		
		UserEntity user = users.get(0);
		assertTrue(user.getLastName().equals(lastName));
	}	
	

	@Order(4)
	@Test 
	final void testFindUsersByKeyword()
	{
		String keyword="ou";
		List<UserEntity> users = userRepository.findUsersByKeyword(keyword);
		assertNotNull(users);
		assertTrue(users.size() == 1);
		
		UserEntity user = users.get(0);
		assertTrue(
				user.getLastName().contains(keyword) ||
				user.getFirstName().contains(keyword)
				);
	}

	@Order(5)
	@Test 
	final void testFindUserFirstNameAndLastNameByKeyword()
	{
		String keyword="ou";
		List<Object[]> users = userRepository.findUserFirstNameAndLastNameByKeyword(keyword);
		assertNotNull(users);
		assertTrue(users.size() == 1);
		
//		System.out.println(users);
		
		Object[] user = users.get(0);
		
//		System.out.println(user);
//		
//		System.out.println(String.valueOf(user[0]));
//		
//		System.out.println(user.length);
		
		assertTrue(user.length == 2);
	
		String userFirstName = String.valueOf(user[0]);
		String userLastName = String.valueOf(user[1]);
		
		assertNotNull(userFirstName);
		assertNotNull(userLastName);
		
		System.out.println("First name = " + userFirstName);
		System.out.println("Last name = " + userLastName);
		
	}
	
	@Order(6)
	@Test 
	final void testUpdateUserEmailVerificationStatus()
	{
		boolean newEmailVerificationStatus = true;
		userRepository.updateUserEmailVerificationStatus(newEmailVerificationStatus, "67f9fe25-67a7-4e30-99f3-a3fb388aaa5d");
		
		UserEntity storedUserDetails = userRepository.findByUserId("67f9fe25-67a7-4e30-99f3-a3fb388aaa5d");
		
		boolean storedEmailVerificationStatus = storedUserDetails.getEmailVerificationStatus();
		
		assertTrue(storedEmailVerificationStatus == newEmailVerificationStatus);

	}	
	
	@Order(7)
	@Test 
	final void testFindUserEntityByUserId()
	{
		String userId = "67f9fe25-67a7-4e30-99f3-a3fb388aaa5d";
		UserEntity userEntity = userRepository.findUserEntityByUserId(userId);
		
		assertNotNull(userEntity);
		assertTrue(userEntity.getUserId().equals(userId));
	}	

	@Order(8)
	@Test
	final void testGetUserEntityFullNameById()
	{
		String userId = "67f9fe25-67a7-4e30-99f3-a3fb388aaa5d";
		List<Object[]> records =  userRepository.getUserEntityFullNameById(userId);
		
        assertNotNull(records);
        assertTrue(records.size() == 1);
        
        Object[] userDetails = records.get(0);
      
        String firstName = String.valueOf(userDetails[0]);
        String lastName = String.valueOf(userDetails[1]);

        assertNotNull(firstName);
        assertNotNull(lastName);
	}
	
	@Order(9)
	@Test 
	final void testUpdateUserEntityEmailVerificationStatus()
	{
		boolean newEmailVerificationStatus = true;
		userRepository.updateUserEntityEmailVerificationStatus(newEmailVerificationStatus, "67f9fe25-67a7-4e30-99f3-a3fb388aaa5d");
		
		UserEntity storedUserDetails = userRepository.findByUserId("67f9fe25-67a7-4e30-99f3-a3fb388aaa5d");
		
		boolean storedEmailVerificationStatus = storedUserDetails.getEmailVerificationStatus();
		
		assertTrue(storedEmailVerificationStatus == newEmailVerificationStatus);

	}
	
}
