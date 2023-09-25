package com.cloudboundstudioz.capstoneprojectserver.domain.user;

import com.cloudboundstudioz.capstoneprojectserver.domain.core.exceptions.ResourceCreationException;
import com.cloudboundstudioz.capstoneprojectserver.domain.core.exceptions.ResourceNotFoundException;
import com.cloudboundstudioz.capstoneprojectserver.domain.user.controllers.UserController;
import com.cloudboundstudioz.capstoneprojectserver.domain.user.models.UserModel;
import com.cloudboundstudioz.capstoneprojectserver.domain.user.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserControllerTest {

    @MockBean
    private UserService userService;

    private UserController testController;
    private UserModel testUser;

    @BeforeEach
    public void setup(){
        testController = new UserController(userService);
        UserModel testUser = new UserModel();
    }

    @Test
    @DisplayName("User Controller: Create User - Success")
    public void createUserTest01() {

        when(userService.create(any())).thenReturn(testUser);

        ResponseEntity<UserModel> response = testController.create(testUser);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

    }

    @Test
    @DisplayName("User Controller: Create User - Fail")
    public void testCreateFails() {

        when(userService.create(any()))
                .thenThrow(new ResourceCreationException("Create failed"));

        assertThrows(RuntimeException.class, () -> {
            testController.create(new UserModel());
        });

    }

    @Test
    @DisplayName("User Controller: Get User By Id - Success")
    public void getUserByIdTest01() {

        UserModel mockUser = new UserModel();

        when(userService.getById(1L)).thenReturn(mockUser);

        ResponseEntity<UserModel> response = testController.getById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    @DisplayName("User Controller: Get User By Id - Fail")
    public void getUserByIdTest02() {

        when(userService.getById(1L))
                .thenThrow(new ResourceNotFoundException("User not found!"));

        assertThrows(ResourceNotFoundException.class, () -> {
            testController.getById(1L);
        });

    }
}
