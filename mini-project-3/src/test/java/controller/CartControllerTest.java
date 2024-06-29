package controller;

import com.ilacad.controller.CartController;
import com.ilacad.entity.Product;
import com.ilacad.service.CartService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CartControllerTest {

    @InjectMocks
    private CartController cartController;

    @Mock
    private CartService cartService;

    @Test
    void testConstructor() {
        CartController controller = new CartController(cartService);
        Assertions.assertInstanceOf(CartController.class, controller);
    }

    @Test
    void testFindAllProductsFromCart() {
        // given
        String userEmail = "user@example.com";
        List<Product> expectedProducts = List.of(
                new Product(1L, "name 1", "description 1", BigDecimal.ZERO, 1),
                new Product(2L, "name 2", "description 2", BigDecimal.ONE, 2));
        when(cartService.findAllProductsFromCart(userEmail)).thenReturn(expectedProducts);

        // when
        List<Product> actualProducts = cartController.findAllProductsFromCart(userEmail);

        // then
        assertEquals(expectedProducts, actualProducts);
    }

    @Test
    void testAddToCart() {
        // given
        String userEmail = "user@example.com";
        Long productId = 123L;
        int numberOfItems = 2;

        // when
        cartController.addToCart(userEmail, productId, numberOfItems);

        // then
        verify(cartService).addToCart(userEmail, productId, numberOfItems);
    }

    @Test
    void testRemoveProductFromCart() {
        // given
        String userEmail = "user@example.com";
        Long productId = 123L;
        int numberOfItems = 2;

        // when
        cartController.removeProductFromCart(userEmail, productId, numberOfItems);

        // then
        verify(cartService).removeProductFromCart(userEmail, productId, numberOfItems);
    }

    @Test
    void testAddCredits() {
        // given
        String userEmail = "user@example.com";
        BigDecimal credits = BigDecimal.ZERO;

        // when
        cartController.addCredits(userEmail, credits);

        // then
        verify(cartService).updateCredits(userEmail, credits);
    }

    @Test
    void testCheckout() {
        // given
        String email = "user@example.com";
        BigDecimal[] credits = new BigDecimal[]{BigDecimal.ZERO};

        // when
        cartController.checkout(email, credits);

        // then
        verify(cartService).checkout(email, credits);
    }
}
