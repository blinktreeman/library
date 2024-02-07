package ru.letsdigit.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.letsdigit.library.entity.Issue;
import ru.letsdigit.library.entity.Reader;
import ru.letsdigit.library.repository.ReaderRepository;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReaderService implements LibraryService<Reader> {

    private final ReaderRepository repository;

    @Autowired
    public ReaderService(ReaderRepository repository) {
        this.repository = repository;
    }

    @Override
    public Reader save(Reader entity) {
        return repository.save(entity);
    }

    @Override
    public Optional<Reader> findById(UUID uuid) {
        return repository.findById(uuid);
    }

    public Iterable<Issue> notReturnedBooksByReaderId(UUID uuid) {
        return repository.findById(uuid).map(value -> value
                .getIssues()
                .stream()
                .filter(issue -> issue.getReturnedAt() == null)
                .collect(Collectors.toList()))
                .orElseThrow(() -> new RuntimeException("Reader not found"));
    }

    @Override
    public Iterable<Reader> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(UUID uuid) {
        repository.deleteById(uuid);
    }
}
