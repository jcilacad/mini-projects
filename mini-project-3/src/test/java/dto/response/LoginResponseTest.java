package dto.response;

import com.ilacad.dto.response.LoginResponse;
import com.ilacad.enums.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
public class LoginResponseTest {

    @Test
    void testAllArgsConstructor() {
        LoginResponse loginResponse = new LoginResponse(
                "first name",
                "last name",
                "email",
                BigDecimal.ZERO,
                Role.DEFAULT);
        assertEquals(loginResponse);
    }

    @Test
    void testNoArgsConstructor() {
        LoginResponse loginResponse = new LoginResponse();
        Assertions.assertInstanceOf(LoginResponse.class, loginResponse);
    }

    @Test
    void testSetters() {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setFirstName("first name");
        loginResponse.setLastName("last name");
        loginResponse.setEmail("email");
        loginResponse.setCredits(BigDecimal.ZERO);
        loginResponse.setRole(Role.DEFAULT);
        assertEquals(loginResponse);
    }

    void assertEquals(LoginResponse loginResponse) {
        Assertions.assertEquals("first name", loginResponse.getFirstName());
        Assertions.assertEquals("last name", loginResponse.getLastName());
        Assertions.assertEquals("email", loginResponse.getEmail());
        Assertions.assertEquals(BigDecimal.ZERO, loginResponse.getCredits());
        Assertions.assertEquals(Role.DEFAULT, loginResponse.getRole());
    }
}
