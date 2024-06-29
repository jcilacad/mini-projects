package exception;

import com.ilacad.exception.InsufficientCreditsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
public class InsufficientCreditsExceptionTest {

    @Test
    void testInsufficientCreditsException() {
        InsufficientCreditsException insufficientCreditsException = new InsufficientCreditsException(BigDecimal.ZERO);
        Assertions.assertEquals("Insufficient credits: 0", insufficientCreditsException.getMessage());
    }
}
