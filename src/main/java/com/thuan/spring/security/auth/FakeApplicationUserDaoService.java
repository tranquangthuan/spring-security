package com.thuan.spring.security.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.thuan.spring.security.config.UserRole.ADMIN;
import static com.thuan.spring.security.config.UserRole.ADMIN_TRAINEE;
import static com.thuan.spring.security.config.UserRole.STUDENT;

@Repository("fake")
public class FakeApplicationUserDaoService implements ApplicationUserDao {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
        return getApplicationUsers().stream().filter(appUser -> appUser.getUsername().equals(username)).findFirst();
    }

    private List<ApplicationUser> getApplicationUsers() {
        List<ApplicationUser> applicationUsers = List.of(
                new ApplicationUser("anna", passwordEncoder.encode("123456"),
                        STUDENT.grantedAuthorities(),
                        true, true, true, true),
                new ApplicationUser("tom", passwordEncoder.encode("123456"),
                        ADMIN_TRAINEE.grantedAuthorities(),
                        true, true, true, true),
                new ApplicationUser("linda", passwordEncoder.encode("123456"),
                        ADMIN.grantedAuthorities(), true, true,
                        true, true));

        return applicationUsers;
    }
}
