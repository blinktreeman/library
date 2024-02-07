package ru.letsdigit.library.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

    @NotBlank
    @Size(min = 2, max = 255)
    private String bookTitle;

    public Book(String bookTitle) {
        this.bookTitle = bookTitle;
    }
}
