package ru.letsdigit.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.letsdigit.library.entity.Book;
import ru.letsdigit.library.repository.IBookRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class BookServiceImpl implements IService<Book> {

    private final IBookRepository repository;

    @Autowired
    public BookServiceImpl(IBookRepository repository) {
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
