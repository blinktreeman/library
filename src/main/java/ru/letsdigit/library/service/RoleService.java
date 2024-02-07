package ru.letsdigit.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.letsdigit.library.entity.Role;
import ru.letsdigit.library.repository.RoleRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class RoleService implements LibraryService<Role> {
    private final RoleRepository repository;

    @Autowired
    public RoleService(RoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public Role save(Role entity) {
        return repository.save(entity);
    }

    @Override
    public Optional<Role> findById(UUID uuid) {
        return repository.findById(uuid);
    }

    public Optional<Role> findFirstByRoleTitle(String title) {
        return repository.findFirstByRoleTitle(title);
    }

    @Override
    public Iterable<Role> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteById(UUID uuid) {
        repository.deleteById(uuid);
    }
}
