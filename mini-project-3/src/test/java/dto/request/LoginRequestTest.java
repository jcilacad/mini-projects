package dto.request;

import com.ilacad.dto.request.LoginRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import org.junit.jupiter.api.Assertions;

@ExtendWith(MockitoExtension.class)
public class LoginRequestTest {

    @Test
    void testAllArgsConstructor() {
        LoginRequest loginRequest = new LoginRequest("user@exaple.com", "SamplePassword123");
        assertEquals(loginRequest);
    }

    @Test
    void testNoArgsConstructor() {
        LoginRequest loginRequest = new LoginRequest();
        Assertions.assertInstanceOf(LoginRequest.class, loginRequest);
    }

    @Test
    void testSetters() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("user@exaple.com");
        loginRequest.setPassword("SamplePassword123");
        assertEquals(loginRequest);
    }

    void assertEquals(LoginRequest loginRequest) {
        Assertions.assertEquals("user@exaple.com", loginRequest.getEmail());
        Assertions.assertEquals("SamplePassword123", loginRequest.getPassword());
    }
}
