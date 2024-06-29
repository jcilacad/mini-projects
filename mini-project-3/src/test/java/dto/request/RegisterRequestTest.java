package dto.request;

import com.ilacad.dto.request.RegisterRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RegisterRequestTest {

    @Test
    void testAllArgsConstructor() {
        RegisterRequest registerRequest = new RegisterRequest(
                "first name",
                "last name",
                "email",
                "password",
                "confirm password");
        assertEquals(registerRequest);
    }

    @Test
    void testNoArgsConstructor() {
        RegisterRequest registerRequest = new RegisterRequest();
        Assertions.assertInstanceOf(RegisterRequest.class, registerRequest);
    }

    @Test
    void testSetters() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setFirstName("first name");
        registerRequest.setLastName("last name");
        registerRequest.setEmail("email");
        registerRequest.setPassword("password");
        registerRequest.setConfirmPassword("confirm password");
        assertEquals(registerRequest);
    }

    void assertEquals(RegisterRequest registerRequest) {
        Assertions.assertEquals("first name", registerRequest.getFirstName());
        Assertions.assertEquals("last name", registerRequest.getLastName());
        Assertions.assertEquals("email", registerRequest.getEmail());
        Assertions.assertEquals("password", registerRequest.getPassword());
        Assertions.assertEquals("confirm password", registerRequest.getConfirmPassword());
    }
}
