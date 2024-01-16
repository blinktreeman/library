package ru.letsdigit.library.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
public class Issue implements Serializable {
    @Id
    @GeneratedValue
    private UUID uuid;

    @ManyToOne
    @JoinColumn(name = "book_uuid", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "reader_uuid", nullable = false)
    private Reader reader;

    /* 3.1* В Issue поле timestamp разбить на 2: issued_at, returned_at - дата выдачи и дата возврата */
    // Дата выдачи книги
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    @org.hibernate.annotations.CreationTimestamp
    private Date issuedAt;

    // Дата возврата книги
    private Date returnedAt;
}
