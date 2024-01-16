package ru.letsdigit.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.letsdigit.library.entity.Reader;

import java.util.UUID;

@Repository
public interface IReaderRepository extends JpaRepository<Reader, UUID> {
}
