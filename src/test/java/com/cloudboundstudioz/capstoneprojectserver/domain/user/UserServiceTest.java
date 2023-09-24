package com.cloudboundstudioz.capstoneprojectserver.domain.user;

import com.cloudboundstudioz.capstoneprojectserver.domain.core.exceptions.ResourceCreationException;
import com.cloudboundstudioz.capstoneprojectserver.domain.profile.models.Profile;
import com.cloudboundstudioz.capstoneprojectserver.domain.user.models.UserModel;
import com.cloudboundstudioz.capstoneprojectserver.domain.user.repositories.UserRepo;
import com.cloudboundstudioz.capstoneprojectserver.domain.user.services.UserService;
import org.apache.catalina.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.BDDAssertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
    private UserModel testUser1;
    private UserModel testUserExpected;
    private UserModel testUserActual;

    // test profile
    private Profile testProfile;

    @BeforeEach
    public void setup() {
        testProfile = new Profile("Lonny Love", 23, 68.5, 145.2);
        testUser1 = new UserModel("CloudBound", "cloud@bound.com", "clbd", testProfile);
        testUserExpected = testUser1;
        testUserActual = new UserModel();
    }

    @Test
    @DisplayName("User Service: Create User - Success")
    public void createUserTest01(){ // When I try to create a user, that exact user is successfully created

        BDDMockito.doReturn(testUserExpected).when(testRepo).save(ArgumentMatchers.any());
        testUserActual = new UserModel("CloudBound", "cloud@bound.com", "clbd", testProfile);
        assertNotNull(testUserActual);
        assertEquals(testUserExpected, testUserActual);
    }

    @Test
    @DisplayName("User Service: Create User - Fail")
    public void createUserTest02(){
        // Arrange
        BDDMockito.doThrow(new ResourceCreationException("User Creation Failed!"))
                .when(testRepo).save(ArgumentMatchers.any());

        // Act & Assert
        try {
            testService.create(testUser1);
            fail("Expected ResourceCreationException but it wasn't thrown");
        } catch (ResourceCreationException e) {
            assertEquals("User Creation Failed!", e.getMessage());
        }
    }
}