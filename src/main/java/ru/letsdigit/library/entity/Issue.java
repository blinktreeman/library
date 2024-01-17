package ru.letsdigit.library.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
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
    @JsonBackReference
    private Reader reader;

    /* 3.1* В Issue поле timestamp разбить на 2: issued_at, returned_at - дата выдачи и дата возврата */
    // Дата выдачи книги
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    @org.hibernate.annotations.CreationTimestamp
    private Date issuedAt;

    // Дата возврата книги
    private Date returnedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Issue r = (Issue) o;
        return Objects.equals(uuid, r.getUuid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
