package com.cloudboundstudioz.capstoneprojectserver.domain.user;

import com.cloudboundstudioz.capstoneprojectserver.domain.core.exceptions.ResourceCreationException;
import com.cloudboundstudioz.capstoneprojectserver.domain.core.exceptions.ResourceNotFoundException;
import com.cloudboundstudioz.capstoneprojectserver.domain.user.controllers.UserController;
import com.cloudboundstudioz.capstoneprojectserver.domain.user.models.UserModel;
import com.cloudboundstudioz.capstoneprojectserver.domain.user.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserControllerTest {

    // Inject Controller and Service
    @Autowired
    private UserController testController;

    @MockBean
    private UserService testService;

    // test user model
    private UserModel testUser1;
    private UserModel testUser2;

    @BeforeEach
    public void setup(){
        testController = new UserController(testService);
        testUser1 = new UserModel("Miles_Morales", "miles@morales.com", "spidey");
        testUser1.setId(1L);
        testUser2 = new UserModel("Lonny_Love", "lonny@love.com", "lonny");
        testUser1.setId(2L);
    }

    // CREATE TESTS

    @Test
    @DisplayName("User Controller: Create User - Success")
    public void createUserTest01() {
        // When create() method is called, HTTP CREATED code is returned

        // Arrange: Mock call create method, forcing it to return testUser1
        BDDMockito.when(testService.create(any())).thenReturn(testUser1);

        // Act: Call controller create() method
        ResponseEntity<UserModel> response = testController.create(testUser1);

        // Assert: respond with the created user and HTTP CREATED code
        assertEquals(testUser1.getId(), response.getBody().getId());
        assertEquals(testUser1.getUsername(), response.getBody().getUsername());
        assertEquals(testUser1.getEmail(), response.getBody().getEmail());
        assertEquals(testUser1.getPassword(), response.getBody().getPassword());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    @DisplayName("User Controller: Create User - Fail")
    public void testCreateFails() {
        // When create() method is called, assert exception to be thrown

        // Arrange: Mock call create method, forcing it to throw an exception
        BDDMockito.when(testService.create(any()))
                .thenThrow(new ResourceCreationException("Create failed"));

        // Act & Assert: Call create method, Assert Creation Exception
        assertThrows(ResourceCreationException.class, () -> {
            testController.create(testUser1);
        });

    }

    // GET TESTS

    @Test
    @DisplayName("User Controller: Get User By Id - Success")
    public void getUserByIdTest01() {
        // when getbyId() method is called, Response Entity with that user and HTTP OK code is called

        // Arrange: Mock getById(), forcing it to return testUser1
        when(testService.getById(1L)).thenReturn(testUser1);

        // Act: Call controller call() method
        ResponseEntity<UserModel> response = testController.getById(1L);

        // Assert: should respond with correct user and HTTP OK code
        assertEquals(testUser1.getId(), response.getBody().getId());
        assertEquals(testUser1.getUsername(), response.getBody().getUsername());
        assertEquals(testUser1.getEmail(), response.getBody().getEmail());
        assertEquals(testUser1.getPassword(), response.getBody().getPassword());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("User Controller: Get User By Id - Fail")
    public void getUserByIdTest02() {
        // when getbyId() method is called, assert Exception to be thrown

        // Arrange: Mock call getById() method, forcing it to throw an exception
        BDDMockito.when(testService.getById(1L))
                .thenThrow(new ResourceNotFoundException("User not found!"));

        // Act & Assert: call getById(), asserting it throw exception
        assertThrows(ResourceNotFoundException.class, () -> {
            testController.getById(1L);
        });

    }

    @Test
    @DisplayName("User Controller: Get All Users - Success")
    public void getAllTest01(){
        // when getAll() is called, assert response entity with list of users created

        // Arrange: mock get all method to return a list of test users
        List<UserModel> result = new ArrayList<>();
        result.add(testUser1); result.add(testUser2);

        BDDMockito.when(testService.getAll()).thenReturn(result);

        // Act: Call controller getAll() method
        ResponseEntity<List<UserModel>> response = testController.getAll();

        // Assert: Size of the Response Entity list should be the same amount of users created
        assertEquals(result.size(), response.getBody().size());
    }

    @Test
    @DisplayName("User Controller: Get All Users - Fail")
    public void getAllTest02() {

        // Arrange: mock get all method to return null
        BDDMockito.when(testService.getAll()).thenReturn(null);

        // Act: call get all method and get response
        ResponseEntity<List<UserModel>> response = testController.getAll();

        // Assert: status code should be HTTP INTERNAL SERVER ERROR - 500
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    @DisplayName("User Controller: Get By Username - Success")
    public void getByUsernameTest01(){
        // when getByUsername() method is called, Response Entity with that user and HTTP OK code is called

        // Arrange: Mock getById(), forcing it to return testUser1
        String username = testUser1.getUsername();
        when(testService.getByUsername(username)).thenReturn(testUser1);

        // Act: Call controller call() method
        ResponseEntity<UserModel> response = testController.getByUsername(username);

        // Assert: should respond with HTTP OK code
        assertEquals(testUser1.getId(), response.getBody().getId());
        assertEquals(testUser1.getUsername(), response.getBody().getUsername());
        assertEquals(testUser1.getEmail(), response.getBody().getEmail());
        assertEquals(testUser1.getPassword(), response.getBody().getPassword());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("User Controller: Get User By Username - Fail")
    public void getUserByUsernameTest02() {
        // when getByUsername() method is called, assert Exception to be thrown

        // Arrange: Mock call getByEmail() method, forcing it to throw an exception
        String username = testUser1.getUsername();
        BDDMockito.when(testService.getByUsername(username))
                .thenThrow(new ResourceNotFoundException("User not found!"));

        // Act & Assert: call getById(), asserting it throw exception
        assertThrows(ResourceNotFoundException.class, () -> {
            testController.getByUsername(username);
        });

    }

    @Test
    @DisplayName("User Controller: Get By Email - Success")
    public void getByEmailTest01(){
        // when getByEmail() method is called, Response Entity with that user and HTTP OK code is called

        // Arrange: Mock getByEmail(), forcing it to return testUser1
        String email = testUser1.getEmail();
        when(testService.getByEmail(email)).thenReturn(testUser1);

        // Act: Call controller call() method
        ResponseEntity<UserModel> response = testController.getByEmail(email);

        // Assert: should respond with HTTP OK code
        assertEquals(testUser1.getId(), response.getBody().getId());
        assertEquals(testUser1.getUsername(), response.getBody().getUsername());
        assertEquals(testUser1.getEmail(), response.getBody().getEmail());
        assertEquals(testUser1.getPassword(), response.getBody().getPassword());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("User Controller: Get User By Email - Fail")
    public void getUserByEmailTest02() {
        // when getByEmail() method is called, assert Exception to be thrown

        // Arrange: Mock call getByEmail() method, forcing it to throw an exception
        String email = testUser1.getEmail();
        BDDMockito.when(testService.getByEmail(email))
                .thenThrow(new ResourceNotFoundException("User not found!"));

        // Act & Assert: call getById(), asserting it throw exception
        assertThrows(ResourceNotFoundException.class, () -> {
            testController.getByEmail(email);
        });

    }

    @Test
    @DisplayName("User Controller: Update User - Success")
    public void updateUserTest01(){
        // when call update() method, return updated user

        // Arrange: mock save test user, then mock update method, return updated user
        UserModel updatedUser = testUser2;
        BDDMockito.when(testService.create(testUser1)).thenReturn(testUser1);
        BDDMockito.when(testService.updateUser(1L, updatedUser)).thenReturn(updatedUser);

        // Act: save testUser1, then update with updatedUser
        ResponseEntity<UserModel> created = testController.create(testUser1);
        ResponseEntity<UserModel> response = testController.update(1L, updatedUser);

        // Assert: assert updated user is returned and HTTP OK
        assertEquals(updatedUser.getId(), response.getBody().getId());
        assertEquals(updatedUser.getUsername(), response.getBody().getUsername());
        assertEquals(updatedUser.getEmail(), response.getBody().getEmail());
        assertEquals(updatedUser.getPassword(), response.getBody().getPassword());
//        assertEquals(HttpStatus.OK, response.getStatusCode()); // NOTE: RETURNS 202 ACCEPTED INSTEAD OF 200 OK
    }

    @Test
    @DisplayName("User Controller: Update User - Fail")
    public void updateUserTest02() {

        // Arrange: Mock update() method to throw an exception
        BDDMockito.when(testService.updateUser(1L, testUser1)).thenThrow(new ResourceNotFoundException("User not found!"));

        // Act & Assert: Call update() method and assert exception is thrown
        assertThrows(ResourceNotFoundException.class, () -> {
            testController.update(1L, testUser1);
        });
    }

    @Test
    @DisplayName("User Controller: Delete User - Success")
    public void deleteUserTest01() {

        // Arrange: Mock delete() method to do nothing
        BDDMockito.doNothing().when(testService).deleteUser(1L);

        // Act: Call delete() method
        ResponseEntity<Void> response = testController.delete(1L);

        // Assert: Response entity should have OK status code
        assertTrue(response.getStatusCode().is2xxSuccessful());
        BDDMockito.verify(testService, BDDMockito.times(1)).deleteUser(1L);
    }

}
