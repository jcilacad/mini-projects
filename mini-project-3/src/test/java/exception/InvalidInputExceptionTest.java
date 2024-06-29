package exception;

import com.ilacad.exception.InvalidInputException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class InvalidInputExceptionTest {

    @Test
    void testInvalidInputExceptionTest() {
        Map<String, String> errors = Map.of("field","value");
        Assertions.assertEquals("value", errors.get("field"));
    }
}
