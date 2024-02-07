package ru.letsdigit.library.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.letsdigit.library.entity.CustomUser;
import ru.letsdigit.library.service.UserService;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        /* Находим пользователя в репозитории */
        CustomUser user = userService
                .findByUserLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        /* Возвращаем UserDetails для пользователя */
        return User.builder()
                .username(user.getUserLogin())
                .password(user.getPassword())
                .authorities(user
                        .getRoles()
                        .stream()
                        .map(role -> new SimpleGrantedAuthority(role.getRoleTitle()))
                        .toList())
                .build();
    }
}
