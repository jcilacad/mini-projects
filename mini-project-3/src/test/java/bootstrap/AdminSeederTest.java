package bootstrap;

import com.ilacad.bootstrap.AdminSeeder;
import com.ilacad.dao.UserDao;
import com.ilacad.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminSeederTest {

    @InjectMocks
    private AdminSeeder adminSeeder;

    @Mock
    private UserDao userDao;

    @BeforeEach
    void setUp() {
        adminSeeder = new AdminSeeder(userDao);
    }

    @Test
    void testCreateAdminUserNotFound() {
        // given
        String adminEmail = "john@admin.com";
        when(userDao.isUserFoundByEmail(adminEmail)).thenReturn(false);

        // when
        adminSeeder.createAdmin();

        // then
        verify(userDao).insertUser(any(User.class));
    }

    @Test
    void testCreateAdminUserFound() {
        // given
        String adminEmail = "john@admin.com";
        when(userDao.isUserFoundByEmail(adminEmail)).thenReturn(true);

        // when
        adminSeeder.createAdmin();

        // then
        verify(userDao, never()).insertUser(any(User.class));
    }
}
