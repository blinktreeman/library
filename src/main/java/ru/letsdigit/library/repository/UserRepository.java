package ru.letsdigit.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.letsdigit.library.entity.CustomUser;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<CustomUser, UUID> {
    Optional<CustomUser> findByUserLogin(String userLogin);
}
