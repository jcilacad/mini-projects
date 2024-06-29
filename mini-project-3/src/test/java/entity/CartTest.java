package entity;

import com.ilacad.entity.Cart;
import com.ilacad.entity.Product;
import com.ilacad.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class CartTest {

    @InjectMocks
    private User user;

    @InjectMocks
    private Product product;

    @Test
    void testAllArgsConstructor() {
        Cart cart = new Cart(1L, user, List.of(product));
        assertEquals(cart);
    }

    @Test
    void testNoArgsConstructor() {
        Cart cart = new Cart();
        Assertions.assertInstanceOf(Cart.class, cart);
    }

    @Test
    void testSetters() {
        Cart cart = new Cart();
        cart.setId(1L);
        cart.setUser(user);
        cart.setProducts(List.of(product));
        assertEquals(cart);
    }

    void assertEquals(Cart cart) {
        Assertions.assertEquals(1L, cart.getId());
        Assertions.assertEquals(user, cart.getUser());
        Assertions.assertEquals(List.of(product), cart.getProducts());
    }
}
