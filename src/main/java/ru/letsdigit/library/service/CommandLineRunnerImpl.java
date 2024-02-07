package ru.letsdigit.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.letsdigit.library.entity.Book;
import ru.letsdigit.library.entity.CustomUser;
import ru.letsdigit.library.entity.Reader;
import ru.letsdigit.library.entity.Role;

import java.util.*;

@Service
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final LibraryService<Book> bookService;
    private final LibraryService<Reader> readerService;
    private final RoleService roleService;
    private final LibraryService<CustomUser> userService;

    @Autowired
    public CommandLineRunnerImpl(LibraryService<Book> bookService,
                                 LibraryService<Reader> readerService,
                                 RoleService roleService,
                                 LibraryService<CustomUser> userService) {
        this.bookService = bookService;
        this.readerService = readerService;
        this.roleService = roleService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {

        List<Book> books = new ArrayList<>(Arrays.asList(
                new Book("Spring в действии"),
                new Book("Микросервисы Spring в действии"),
                new Book("Pro Spring Security"),
                new Book("Java Persistence with Hibernate")
        ));

        List<Reader> readers = new ArrayList<>(Arrays.asList(
                new Reader("Иван", "Иванов"),
                new Reader("Петр", "Петров"),
                new Reader("Василий", "Васильев")
        ));

        List<Role> roles = new ArrayList<>(Arrays.asList(
                new Role("admin"),
                new Role("reader")
        ));

        books.forEach(bookService::save);
        readers.forEach(readerService::save);
        roles.forEach(roleService::save);

        /* Читатель */
        CustomUser ivanov = new CustomUser();
        ivanov.setUserLogin("ivanov");
        ivanov.setPassword(getPasswordEncoder().encode("readerPass"));
        ivanov.getRoles().add(roleService.findFirstByRoleTitle("reader").get());
        userService.save(ivanov);

        /* Админ */
        CustomUser sidorov = new CustomUser();
        sidorov.setUserLogin("sidorov");
        sidorov.setPassword(getPasswordEncoder().encode("adminPass"));
        sidorov.getRoles().add(roleService.findFirstByRoleTitle("admin").get());
        userService.save(sidorov);
    }

    private PasswordEncoder getPasswordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
