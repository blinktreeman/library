package ru.letsdigit.library.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
public class Reader implements Serializable {
    @Id
    @GeneratedValue
    private UUID uuid;

    private String firstName;
    private String lastName;

    @OneToMany(mappedBy = "reader")
    @JsonManagedReference
    private Set<Issue> issues = new HashSet<>();

    public Reader(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
