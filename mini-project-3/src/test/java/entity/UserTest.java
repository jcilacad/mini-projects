package entity;

import com.ilacad.entity.Cart;
import com.ilacad.entity.User;
import com.ilacad.enums.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
public class UserTest {

    @InjectMocks
    private Cart cart;

    @Test
    void testAllArgsConstructor() {
        User user = new User(1L,
                "first name",
                "last name",
                "email",
                "password",
                BigDecimal.ZERO,
                Role.DEFAULT,
                cart);
        assertEquals(user);
    }

    @Test
    void testNoArgsConstructor() {
        User user = new User();
        Assertions.assertInstanceOf(User.class, user);
    }

    @Test
    void testSetters() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("first name");
        user.setLastName("last name");
        user.setEmail("email");
        user.setPassword("password");
        user.setCredits(BigDecimal.ZERO);
        user.setRole(Role.DEFAULT);
        user.setCart(cart);
        assertEquals(user);
    }

    void assertEquals(User user) {
        Assertions.assertEquals(1L, user.getId());
        Assertions.assertEquals("first name", user.getFirstName());
        Assertions.assertEquals("last name", user.getLastName());
        Assertions.assertEquals("email", user.getEmail());
        Assertions.assertEquals("password", user.getPassword());
        Assertions.assertEquals(BigDecimal.ZERO, user.getCredits());
        Assertions.assertEquals(Role.DEFAULT, user.getRole());
        Assertions.assertInstanceOf(Cart.class, user.getCart());
    }
}
