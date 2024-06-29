package controller;

import com.ilacad.controller.AuthenticationController;
import com.ilacad.dto.request.LoginRequest;
import com.ilacad.dto.request.RegisterRequest;
import com.ilacad.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AuthenticationControllerTest {

    @InjectMocks
    private AuthenticationController authenticationController;

    @Mock
    private UserService userService;

    @Test
    void testConstructor() {
        AuthenticationController controller = new AuthenticationController(userService);
        Assertions.assertInstanceOf(AuthenticationController.class, controller);
    }

    @Test
    void testRegister() {
        // given
        RegisterRequest registerRequest = RegisterRequest.builder()
                .firstName("first name")
                .lastName("last name")
                .email("sample@email.com")
                .password("Password123!")
                .confirmPassword("Password123!")
                .build();

        // when
        authenticationController.register(registerRequest);

        // then
        verify(userService).register(registerRequest);
    }

    @Test
    void testLogin() {
        // given
        LoginRequest loginRequest = LoginRequest.builder()
                .email("sample@email.com")
                .password("Password")
                .build();

        // when
        authenticationController.login(loginRequest);

        // then
        verify(userService).login(loginRequest);
    }
}
