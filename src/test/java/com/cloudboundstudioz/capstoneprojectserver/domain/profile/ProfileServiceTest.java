package com.cloudboundstudioz.capstoneprojectserver.domain.profile;

import com.cloudboundstudioz.capstoneprojectserver.CapstoneProjectServerApplication;
import com.cloudboundstudioz.capstoneprojectserver.domain.profile.models.Profile;
import com.cloudboundstudioz.capstoneprojectserver.domain.profile.repositories.ProfileRepo;
import com.cloudboundstudioz.capstoneprojectserver.domain.profile.services.ProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ProfileServiceTest {

    @Autowired
    private ProfileService profileService;

    @MockBean
    private ProfileRepo profileRepo;

    private Profile testProfile;
    private Profile returnedProfile;
    @BeforeEach
    public void setup() {
        testProfile = new Profile("Lonny Love", 23, 68.5, 145.2);
        returnedProfile = new Profile();
    }

    @Test
    public void createProfileTest(){
//        BDDMockito.doReturn(null).when(profileRepo).findByName(ArgumentMatchers.any());
        BDDMockito.doReturn(returnedProfile).when(profileRepo).save(ArgumentMatchers.any());
        Profile newProfile = profileService.create(new Profile("Miles Morales", 17, 58.4, 130.2));
        newProfile.setId(1L);
        assertNotNull(newProfile);
        assertEquals(1, newProfile.getId());
    }


}
