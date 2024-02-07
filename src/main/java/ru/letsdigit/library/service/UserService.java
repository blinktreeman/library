package ru.letsdigit.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.letsdigit.library.entity.CustomUser;
import ru.letsdigit.library.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements LibraryService<CustomUser> {

    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public CustomUser save(CustomUser entity) {
        return repository.save(entity);
    }

    @Override
    public Optional<CustomUser> findById(UUID uuid) {
        return repository.findById(uuid);
    }

    public Optional<CustomUser> findByUserLogin(String userLogin) {
        return repository.findByUserLogin(userLogin);
    }

    @Override
    public Iterable<CustomUser> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(UUID uuid) {
        repository.deleteById(uuid);
    }
}
