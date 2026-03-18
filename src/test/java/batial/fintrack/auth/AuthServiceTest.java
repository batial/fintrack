package batial.fintrack.auth;

import batial.fintrack.config.JwtService;
import batial.fintrack.user.User;
import batial.fintrack.user.UserRepository;
import batial.fintrack.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    @Test
    void register_shouldReturnTokenWhenValidRequest() {
        // Arrange
        RegisterRequest request = new RegisterRequest("Sebastian", "seba@test.com", "123456");

        when(passwordEncoder.encode("123456")).thenReturn("hashedPassword");
        when(userService.save(any(User.class))).thenReturn(new User());
        when(jwtService.generateToken("seba@test.com")).thenReturn("fake-jwt-token");

        // Act
        AuthResponse response = authService.register(request);

        // Assert
        assertNotNull(response);
        assertEquals("fake-jwt-token", response.token());
        verify(userService, times(1)).save(any(User.class));
    }

    @Test
    void register_shouldEncodePassword() {
        // Arrange
        RegisterRequest request = new RegisterRequest("Sebastian", "seba@test.com", "123456");

        when(passwordEncoder.encode("123456")).thenReturn("hashedPassword");
        when(userService.save(any(User.class))).thenReturn(new User());
        when(jwtService.generateToken(any())).thenReturn("fake-jwt-token");

        // Act
        authService.register(request);

        // Assert
        verify(passwordEncoder, times(1)).encode("123456");
    }
}