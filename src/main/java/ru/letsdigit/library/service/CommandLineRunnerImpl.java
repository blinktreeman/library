package ru.letsdigit.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import ru.letsdigit.library.entity.Book;
import ru.letsdigit.library.entity.Reader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final IService<Book> bookIService;
    private final IService<Reader> readerIService;

    @Autowired
    public CommandLineRunnerImpl(IService<Book> bookIService,
                                 IService<Reader> readerIService) {
        this.bookIService = bookIService;
        this.readerIService = readerIService;
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

        books.forEach(bookIService::save);
        readers.forEach(readerIService::save);
    }
}
