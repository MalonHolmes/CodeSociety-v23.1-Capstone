package com.cloudboundstudioz.capstoneprojectserver.domain.profile;

import com.cloudboundstudioz.capstoneprojectserver.domain.core.exceptions.ResourceCreationException;
import com.cloudboundstudioz.capstoneprojectserver.domain.core.exceptions.ResourceNotFoundException;
import com.cloudboundstudioz.capstoneprojectserver.domain.profile.models.Profile;
import com.cloudboundstudioz.capstoneprojectserver.domain.profile.repositories.ProfileRepo;
import com.cloudboundstudioz.capstoneprojectserver.domain.profile.services.ProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ProfileServiceTest {

    @Autowired
    private ProfileService testService;

    @MockBean
    private ProfileRepo testRepo;

    private Profile testProfile1;
    private Profile testProfile2;
    @BeforeEach
    public void setup() {
        testProfile1 = new Profile("Lonny Love", 23, 68.5, 145.2);
        testProfile2 = new Profile();
    }

    @Test
    @DisplayName("Profile Service: Create Profile - Success")
    public void createProfileTest(){

        // Arrange: mock repo create method to return the created profile
        BDDMockito.when(testRepo.save(testProfile1)).thenReturn(testProfile1);

        // Act: Call service create method
        Profile result = testService.create(testProfile1);

        // Assert: new profile is created and matches what we passed
        assertEquals(testProfile1, result);
    }

    @Test
    @DisplayName("Profile Service: Create Profile - Fail")
    public void createUserTest02(){
        // Arrange
        BDDMockito.doThrow(new ResourceCreationException("Profile Creation Failed!"))
                .when(testRepo).save(ArgumentMatchers.any());

        // Act & Assert: call service create(), assert resource creation exception to be thrown
        assertThrows(ResourceCreationException.class, () -> {
            testService.create(testProfile1);
        });
    }

    @Test
    @DisplayName("Profile Service: Get Profile By Id - Success")
    public void getProfileByIdTest01() {
        // Arrange: mock create user and mock repo
        testProfile1.setId(1L);
        BDDMockito.when(testRepo.findById(1L))
                .thenReturn(Optional.of(testProfile1));

        // Act: Call service getbyid method
        Profile result = testService.getById(1L);

        // Assert: Verify result
        assertEquals(testProfile1.getId(), result.getId());
        assertEquals(testProfile1, result);
    }

    @Test
    @DisplayName("Profile Service: Get Profile By Id - Fail")
    public void getProfileByIdTest02(){
        // Arrange: Mock findById to throw exception
        BDDMockito.when(testRepo.findById(1L)).thenThrow(new ResourceNotFoundException("Profile Not Found!"));

        // Act & Assert: Call service, assert exception
        assertThrows(ResourceNotFoundException.class, () -> {
            testService.getById(1L);
        });
    }

    @Test
    @DisplayName("Profile Service: Get Profile By Name - Success")
    public void getProfileByNameTest01() {
        // Arrange
        // Act
        // Assert
    }


}
