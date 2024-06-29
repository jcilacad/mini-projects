package dto.response;

import com.ilacad.dto.response.RegisterResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RegisterResponseTest {

    @Test
    void testAllArgsConstructor() {
        RegisterResponse registerResponse = new RegisterResponse(
                1L, "first name", "last name", "email");
        assertEquals(registerResponse);
    }

    @Test
    void testNoArgsConstructor() {
        RegisterResponse registerResponse = new RegisterResponse();
        Assertions.assertInstanceOf(RegisterResponse.class, registerResponse);
    }

    @Test
    void testSetters() {
        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setId(1L);
        registerResponse.setFirstName("first name");
        registerResponse.setLastName("last name");
        registerResponse.setEmail("email");
        assertEquals(registerResponse);
    }

    void assertEquals(RegisterResponse registerResponse) {
        Assertions.assertEquals(1L, registerResponse.getId());
        Assertions.assertEquals("first name", registerResponse.getFirstName());
        Assertions.assertEquals("last name", registerResponse.getLastName());
        Assertions.assertEquals("email", registerResponse.getEmail());
    }
}
