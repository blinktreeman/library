package ru.letsdigit.library.service;

import java.util.Optional;
import java.util.UUID;

public interface LibraryService<E> {
    E save(E entity);
    Optional<E> findById(UUID uuid);
    Iterable<E> findAll();
    void deleteById(UUID uuid);
}
