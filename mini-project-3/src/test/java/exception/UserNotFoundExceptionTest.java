package exception;

import com.ilacad.exception.UserNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserNotFoundExceptionTest {

    @Test
    void testUserNotFoundException() {
        UserNotFoundException userNotFoundException = new UserNotFoundException("email");
        Assertions.assertEquals("User not found with email: email", userNotFoundException.getMessage());
    }
}
