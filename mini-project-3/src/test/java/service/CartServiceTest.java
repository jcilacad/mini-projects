package service;

import com.ilacad.dao.CartDao;
import com.ilacad.dao.ProductDao;
import com.ilacad.dao.UserDao;
import com.ilacad.entity.Cart;
import com.ilacad.entity.Product;
import com.ilacad.entity.User;
import com.ilacad.enums.Role;
import com.ilacad.exception.CartNotFoundException;
import com.ilacad.exception.InsufficientCreditsException;
import com.ilacad.exception.ProductNotFoundException;
import com.ilacad.exception.UserNotFoundException;
import com.ilacad.service.impl.CartServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @InjectMocks
    private CartServiceImpl cartService;

    @Mock
    private UserDao userDao;

    @Mock
    private ProductDao productDao;

    @Mock
    private CartDao cartDao;

    @Test
    void testFindAllProductsFromCartValid() {
        // given
        Product product = new Product(
                1L, "name", "description", BigDecimal.ZERO, 1);
        User user = new User(1L,
                "first name",
                "last name",
                "email",
                "password",
                BigDecimal.ZERO,
                Role.DEFAULT,
                new Cart());
        Cart cart = new Cart(1L, user, List.of(product));
        user.setCart(cart);
        when(userDao.findUserByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(cartDao.existsById(cart.getId())).thenReturn(true);
        when(cartDao.findAllProductsFromCart(cart.getId())).thenReturn(List.of(product));

        // when
        List<Product> products = cartService.findAllProductsFromCart(user.getEmail());

        // then
        Assertions.assertEquals(1, products.size());
    }

    @Test
    void testFindAllProductsFromCartUserNotFound() {
        // given
        String invalidEmail = "invalid email";
        when(userDao.findUserByEmail(invalidEmail)).thenThrow(UserNotFoundException.class);

        // when
        Executable executable = () -> cartService.findAllProductsFromCart(invalidEmail);

        // then
        Assertions.assertThrows(UserNotFoundException.class, executable);
    }

    @Test
    void testFindAllProductsFromCartCartNotFound() {
        // given
        Product product = new Product(
                1L, "name", "description", BigDecimal.ZERO, 1);
        User user = new User(1L,
                "first name",
                "last name",
                "email",
                "password",
                BigDecimal.ZERO,
                Role.DEFAULT,
                new Cart());
        Cart cart = new Cart(1L, user, List.of(product));
        user.setCart(cart);
        when(userDao.findUserByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(cartDao.existsById(cart.getId())).thenReturn(false);

        // when
        Executable executable = () -> cartService.findAllProductsFromCart(user.getEmail());

        // then
        Assertions.assertThrows(CartNotFoundException.class, executable);
    }

    @Test
    void testAddToCartValid() {
        // given
        User user = new User(1L,
                "first name",
                "last name",
                "email",
                "password",
                BigDecimal.ZERO,
                Role.DEFAULT,
                new Cart());
        Product product = new Product(
                1L, "name", "description", BigDecimal.ZERO, 1);
        Cart cart = user.getCart();
        int numberOfItems = 1;

        lenient().when(userDao.findUserByEmail(user.getEmail())).thenReturn(Optional.of(user));
        lenient().when(productDao.findProductById(product.getId())).thenReturn(Optional.of(product));
        lenient().when(cartDao.existsById(cart.getId())).thenReturn(true);
        lenient().when(cartDao.findAllProductsFromCart(cart.getId())).thenReturn(List.of(product));

        // when
        cartService.addToCart(user.getEmail(), product.getId(), numberOfItems);

        // then
        verify(cartDao).addToCart(cart, product, numberOfItems);
    }

    @Test
    void testAddToCartUserNotFound() {
        // given
        String invalidEmail = "invalid email";
        when(userDao.findUserByEmail(invalidEmail)).thenThrow(UserNotFoundException.class);

        // when
        Executable executable = () -> cartService.addToCart(invalidEmail, 1L, 1);

        // then
        Assertions.assertThrows(UserNotFoundException.class, executable);
    }

    @Test
    void testAddToCartProductNotFound() {
        // given
        Long invalidProductId = 100L;
        User user = new User(1L,
                "first name",
                "last name",
                "email",
                "password",
                BigDecimal.ZERO,
                Role.DEFAULT,
                new Cart());
        when(userDao.findUserByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(productDao.findProductById(invalidProductId)).thenThrow(ProductNotFoundException.class);

        // when
        Executable executable = () -> cartService.addToCart("email", invalidProductId, 1);

        // then
        Assertions.assertThrows(ProductNotFoundException.class, executable);
    }

    @Test
    void testRemoveProductFromCartValid() {
        // given
        User user = new User(1L,
                "first name",
                "last name",
                "email",
                "password",
                BigDecimal.ZERO,
                Role.DEFAULT,
                new Cart());
        Product product = new Product(
                1L, "name", "description", BigDecimal.ZERO, 1);
        Cart cart = user.getCart();
        int numberOfItems = 1;

        lenient().when(userDao.findUserByEmail(user.getEmail())).thenReturn(Optional.of(user));
        lenient().when(productDao.findProductById(product.getId())).thenReturn(Optional.of(product));
        lenient().when(cartDao.existsById(cart.getId())).thenReturn(true);
        lenient().when(cartDao.findAllProductsFromCart(cart.getId())).thenReturn(List.of(product));

        // when
        cartService.removeProductFromCart(user.getEmail(), product.getId(), numberOfItems);

        // then
        verify(cartDao).removeProductFromCart(cart, product.getId(), numberOfItems);
    }

    @Test
    void testRemoveProductFromCartUserNotFound() {
        // given
        String invalidEmail = "invalid email";
        when(userDao.findUserByEmail(invalidEmail)).thenThrow(UserNotFoundException.class);

        // when
        Executable executable = () -> cartService.removeProductFromCart(invalidEmail, 1L, 1);

        // then
        Assertions.assertThrows(UserNotFoundException.class, executable);
    }

    @Test
    void testRemoveProductFromCartProductNotFound() {
        // given
        Long invalidProductId = 100L;
        User user = new User(1L,
                "first name",
                "last name",
                "email",
                "password",
                BigDecimal.ZERO,
                Role.DEFAULT,
                new Cart());
        when(userDao.findUserByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(productDao.findProductById(invalidProductId)).thenThrow(ProductNotFoundException.class);

        // when
        Executable executable = () -> cartService.removeProductFromCart("email", invalidProductId, 1);

        // then
        Assertions.assertThrows(ProductNotFoundException.class, executable);
    }

    @Test
    void testUpdateCreditsValid() {
        // given
        User user = new User(1L,
                "first name",
                "last name",
                "email",
                "password",
                BigDecimal.ZERO,
                Role.DEFAULT,
                new Cart());
        when(userDao.findUserByEmail(user.getEmail())).thenReturn(Optional.of(user));

        // when
        cartService.updateCredits(user.getEmail(), BigDecimal.TEN);

        // then
        verify(userDao).updateCredits(user.getId(), BigDecimal.TEN);
    }

    @Test
    void testUpdateCreditsUserNotFound() {
        // given
        String invalidEmail = "invalid email";
        when(userDao.findUserByEmail(invalidEmail)).thenThrow(UserNotFoundException.class);

        // when
        Executable executable = () -> cartService.updateCredits(invalidEmail, BigDecimal.TEN);

        // then
        Assertions.assertThrows(UserNotFoundException.class, executable);
    }

    @Test
    void testCheckoutValid() {
        // given
        User user = new User(1L,
                "first name",
                "last name",
                "email",
                "password",
                BigDecimal.TEN,
                Role.DEFAULT,
                new Cart());
        when(userDao.findUserByEmail(user.getEmail())).thenReturn(Optional.of(user));

        // when
        cartService.checkout(user.getEmail(), new BigDecimal[]{BigDecimal.TEN});

        // then
        verify(userDao).updateCredits(user.getId(), BigDecimal.ZERO);
        verify(cartDao).removeAllProductsFromCart(user.getCart().getId());
    }

    @Test
    void testCheckoutUserNotFound() {
        // given
        String invalidEmail = "invalid email";
        when(userDao.findUserByEmail(invalidEmail)).thenThrow(UserNotFoundException.class);

        // when
        Executable executable = () -> cartService.checkout(invalidEmail, new BigDecimal[]{BigDecimal.ZERO});

        // then
        Assertions.assertThrows(UserNotFoundException.class, executable);
    }

    @Test
    void testCheckoutInsufficientCreditsException() {
        // given
        User user = new User(1L,
                "first name",
                "last name",
                "email",
                "password",
                BigDecimal.ZERO,
                Role.DEFAULT,
                new Cart());
        when(userDao.findUserByEmail(user.getEmail())).thenReturn(Optional.of(user));

        // when
        Executable executable = () -> cartService.checkout(user.getEmail(), new BigDecimal[]{BigDecimal.TEN});

        // then
        Assertions.assertThrows(InsufficientCreditsException.class, executable);
    }
}
