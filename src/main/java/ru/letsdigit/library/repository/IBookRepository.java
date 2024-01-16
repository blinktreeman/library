package ru.letsdigit.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.letsdigit.library.entity.Book;

import java.util.UUID;

@Repository
public interface IBookRepository extends JpaRepository<Book, UUID> {
}
