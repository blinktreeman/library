package ru.letsdigit.library.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
public class Book implements Serializable {
    @Id
    @GeneratedValue
    private UUID uuid;

    private String bookTitle;

    public Book(String bookTitle) {
        this.bookTitle = bookTitle;
    }
}
