package com.cloudboundstudioz.capstoneprojectserver.domain.user;

import com.cloudboundstudioz.capstoneprojectserver.domain.core.exceptions.ResourceCreationException;
import com.cloudboundstudioz.capstoneprojectserver.domain.core.exceptions.ResourceNotFoundException;
import com.cloudboundstudioz.capstoneprojectserver.domain.profile.models.Profile;
import com.cloudboundstudioz.capstoneprojectserver.domain.user.models.UserModel;
import com.cloudboundstudioz.capstoneprojectserver.domain.user.repositories.UserRepo;
import com.cloudboundstudioz.capstoneprojectserver.domain.user.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.assertj.core.api.BDDAssertions.fail;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class UserServiceTest {

    // What do I need to test the user service?
    // A test user service and test user repo and test user model

    // Inject service and repository
    @Autowired
    private UserService testService;
    @MockBean
    private UserRepo testRepo;

    // test user objects
    private UserModel mockUser1;
    private UserModel mockUserExpected;
    private UserModel mockUserActual;

    // test profile
    private Profile testProfile;

    @BeforeEach
    public void setup() {
        testProfile = new Profile("Lonny Love", 23, 68.5, 145.2);
        mockUser1 = new UserModel("CloudBound", "cloud@bound.com", "clbd");
        mockUserExpected = mockUser1;
        mockUserActual = new UserModel();
    }

    // Create Tests
    @Test
    @DisplayName("User Service: Create User - Success")
    public void createUserTest01(){ // When I try to create a user, that exact user is successfully created

        BDDMockito.doReturn(mockUserExpected).when(testRepo).save(ArgumentMatchers.any());
        mockUserActual = new UserModel("CloudBound", "cloud@bound.com", "clbd");
        assertNotNull(mockUserActual);
        assertEquals(mockUserExpected, mockUserActual);
    }

    @Test
    @DisplayName("User Service: Create User - Fail")
    public void createUserTest02(){
        // Arrange
        BDDMockito.doThrow(new ResourceCreationException("User Creation Failed!"))
                .when(testRepo).save(ArgumentMatchers.any());

        // Act & Assert
        try {
            testService.create(mockUser1);
            fail("Expected ResourceCreationException but it wasn't thrown");
        } catch (ResourceCreationException e) {
            assertEquals("User Creation Failed!", e.getMessage());
        }
    }

    // Get Tests
    @Test
    @DisplayName("User Service: Get User By Id - Success")
    public void getUserByIdTest01() {
        // Arrange: mock create user and mock repo
        mockUser1.setId(1L);
        BDDMockito.when(testRepo.findById(1L))
                .thenReturn(Optional.of(mockUser1));

        // Act: Call service method
        UserModel result = testService.getById(1L);

        // Assert: Verify result
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    @DisplayName("User Service: Get User By Id - Fail")
    public void getUserByIdTest02(){
        // Arrange: Mock findById to return empty
        BDDMockito.when(testRepo.findById(1L))
                .thenReturn(Optional.empty());

        // Act & Assert: Call service, assert exception
        assertThrows(ResourceNotFoundException.class, () -> {
            testService.getById(1L);
        });
    }

    @Test
    @DisplayName("User Service: Get User By Username - Success")
    public void getUserByUsernameTest01() {
        // Arrange: mock user
        BDDMockito.when(testRepo.findByUsername(mockUser1.getUsername()))
                .thenReturn(Optional.of(mockUser1));

        // Act: Call service method
        UserModel result = testService.getByUsername("CloudBound");

        // Assert: not null and email
        assertNotNull(result);
        assertEquals("CloudBound", result.getUsername());
    }

    @Test
    @DisplayName("User Service: Get User By Username - Fail")
    public void getUserByUsernameTest02(){
        // Arrange: Mock findByEmail to return empty
        BDDMockito.when(testRepo.findByUsername(mockUser1.getUsername()))
                .thenReturn(Optional.empty());

        // Act & Assert: Call service, assert exception
        assertThrows(ResourceNotFoundException.class, () -> {
            testService.getByUsername("CloudBound");
        });
    }

    @Test
    @DisplayName("User Service: Get User By Email - Success")
    public void getUserByEmailTest01() {
        // Arrange: mock user
        BDDMockito.when(testRepo.findByEmail(mockUser1.getEmail()))
                .thenReturn(Optional.of(mockUser1));

        // Act: Call service method
        UserModel result = testService.getByEmail("cloud@bound.com");

        // Assert: not null and email
        assertNotNull(result);
        assertEquals("cloud@bound.com", result.getEmail());
    }

    @Test
    @DisplayName("User Service: Get User By Email - Fail")
    public void getUserByEmailTest02(){
        // Arrange: Mock findByEmail to return empty
        BDDMockito.when(testRepo.findByEmail(mockUser1.getEmail()))
                .thenReturn(Optional.empty());

        // Act & Assert: Call service, assert exception
        assertThrows(ResourceNotFoundException.class, () -> {
            testService.getByEmail("cloud@bound.com");
        });
    }

    @Test
    @DisplayName("User Service: Get All Users - Success")
    public void getAllTest01(){
        // Arrange: create list of mock users and mock it in repo
        List<UserModel> mocks = new ArrayList<>();
        mocks.add(mockUser1);
        mocks.add(mockUser1);
        mocks.add(mockUser1);

        BDDMockito.when(testRepo.findAll()).thenReturn(mocks); // return mock list when repo calls findAll()

        // Act: call getAll() method
        List<UserModel> actual = testService.getAll();

        // Assert: list length is the same
        assertEquals(mocks.size(), actual.size());
    }

    @Test
    @DisplayName("User Service: Get All Users - Fail")
    public void getAllTest02(){
        // Arrange: mock throwing an error when calling findAll
        BDDMockito.when(testRepo.findAll()).thenThrow(new ResourceNotFoundException("Failed to retrieve users"));

        // Act & Assert: Calling getAll() method throws Resource Not Found Exception
        assertThrows(ResourceNotFoundException.class, () -> {
            testService.getAll();
        });
    }

    // Update Tests
    @Test
    @DisplayName("User Service: Update User - Success")
    public void updateUserTest01(){
        // Arrange: create an updated user and mock it in the db
        mockUser1.setId(1L);
        BDDMockito.when(testRepo.save(mockUser1)).thenReturn(mockUser1);

        UserModel updatedUser =
                new UserModel("CloudBound_Update",
                        "update@update.com",
                        "update");
        updatedUser.setId(1L);

        BDDMockito.when(testRepo.save(updatedUser)).thenReturn(updatedUser);

        // Act: update the user
        UserModel result = testService.updateUser(1L, updatedUser);

        // Assert every field matches
        assertEquals(updatedUser.getId(), result.getId());
        assertEquals(updatedUser.getUsername(), result.getUsername());
        assertEquals(updatedUser.getEmail(), result.getEmail());
        assertEquals(updatedUser.getPassword(), result.getPassword());
        assertEquals(updatedUser.getProfile(), result.getProfile());
    }

    // Delete Tests

    @Test
    @DisplayName("User Service: Delete User - Success")
    public void deleteUserTest01(){
        // Arrange: create user to be deleted, mock it to db
        mockUser1.setId(1L);
        BDDMockito.when(testRepo.save(mockUser1)).thenReturn(mockUser1);

        // Act: delete user
        testService.deleteUser(1L);

        // Assert
        verify(testRepo, times(1)).deleteById(1L);
    }

    @Test
    public void deleteUserTest02(){
        // Arrange
        BDDMockito.when(testRepo.findById(1L))
                .thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            testService.deleteUser(1L);
        });
    }

}