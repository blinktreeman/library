package ru.letsdigit.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.letsdigit.library.entity.Book;
import ru.letsdigit.library.repository.BookRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class BookService implements LibraryService<Book> {

    private final BookRepository repository;

    @Autowired
    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public Book save(Book entity) {
        return repository.save(entity);
    }

    @Override
    public Optional<Book> findById(UUID uuid) {
        return repository.findById(uuid);
    }

    @Override
    public Iterable<Book> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(UUID uuid) {
        repository.deleteById(uuid);
    }
}
