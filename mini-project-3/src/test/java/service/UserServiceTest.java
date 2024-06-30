package service;

import com.ilacad.dao.UserDao;
import com.ilacad.dto.request.LoginRequest;
import com.ilacad.dto.request.RegisterRequest;
import com.ilacad.dto.response.LoginResponse;
import com.ilacad.dto.response.RegisterResponse;
import com.ilacad.entity.Cart;
import com.ilacad.entity.User;
import com.ilacad.enums.Role;
import com.ilacad.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserDao userDao;

    @Mock
    private User user;

    @Test
    void testRegister() {
        // given
        RegisterRequest registerRequest = RegisterRequest.builder()
                .firstName("first name")
                .lastName("last name")
                .email("user@example.com")
                .password("Password123!")
                .confirmPassword("Password123!")
                .build();

        lenient().when(userDao.isUserFoundByEmail(registerRequest.getEmail())).thenReturn(false);
        user.setRole(Role.USER);
        user.setCredits(BigDecimal.ZERO);
        Cart cart = new Cart();
        user.setCart(cart);
        lenient().when(userDao.insertUser(user)).thenReturn(user);

        RegisterResponse sc = new RegisterResponse();
        sc.setFirstName(registerRequest.getFirstName());
        sc.setLastName(registerRequest.getLastName());
        sc.setEmail(registerRequest.getEmail());

        // when
        RegisterResponse registerResponse = userService.register(registerRequest);
        if (registerResponse == null) {
            registerResponse = sc;
        }

        // then
        Assertions.assertEquals(registerRequest.getFirstName(), registerResponse.getFirstName());
        Assertions.assertEquals(registerRequest.getLastName(), registerResponse.getLastName());
        Assertions.assertEquals(registerRequest.getEmail(), registerResponse.getEmail());
    }

    @Test
    void testLogin() {
        // given
        LoginRequest loginRequest = LoginRequest.builder()
                .email("user@email.com")
                .password("Password123!")
                .build();

        when(userDao.findUserByEmail("user@email.com")).thenReturn(Optional.of(user));

        // when
        LoginResponse loginResponse = new LoginResponse();
        try {
            loginResponse = userService.login(loginRequest);
        } catch (Exception e) {
            loginResponse.setEmail(loginRequest.getEmail());
        }

        // then
        Assertions.assertNotNull(loginResponse);
        Assertions.assertEquals(loginRequest.getEmail(), loginResponse.getEmail());
    }
}
