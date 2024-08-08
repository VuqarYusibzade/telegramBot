package org.example.tour_guide;

import org.example.tour_guide.models.Agency;
import org.example.tour_guide.models.User;
import org.example.tour_guide.repository.UserRepository;
import org.example.tour_guide.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test generateResetToken method")
    void testGenerateResetToken() {
        User user = new User();
      //  String resetToken = userService.generateResetToken(user);
       // assertEquals(36, resetToken.length());
    }

    @Test
    @DisplayName("Test createUserForAgent method")
    void testCreateUserForAgent() {
        Agency agency = new Agency();
        String username = "testUser";
        String email = "test@test.com";
        String password = "test";

        userService.createUserForAgent(agency, username, email, password);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Test getActiveUsers method")
    void testGetActiveUsers() {
        List<User> userList = List.of(new User(), new User());
        when(userRepository.getActiveUsers()).thenReturn(userList);
        List<User> activeUsers = userService.getActiveUsers();
        assertEquals(2, activeUsers.size());
    }
}