package com.ilacad.bootstrap;

import com.ilacad.dao.UserDao;
import com.ilacad.entity.User;
import com.ilacad.enums.Role;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AdminSeeder {

    private final UserDao userDao;

    public void createAdmin() {
        String adminEmail = "john@admin.com";
        if (!userDao.isUserFoundByEmail(adminEmail)) {
            User user = new User();
            user.setFirstName("John Christopher");
            user.setLastName("Ilacad");
            user.setEmail(adminEmail);
            user.setPassword("Password123!");
            user.setRole(Role.ADMIN);
            userDao.insertUser(user);
        }
    }
}
