package ru.letsdigit.library.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "uuid"
)
public class Reader implements Serializable {
    @Id
    @GeneratedValue
    private UUID uuid;

    private String firstName;
    private String lastName;

    @OneToMany(mappedBy = "reader", cascade = CascadeType.ALL)
//    @JsonManagedReference
    private Set<Issue> issues = new HashSet<>();

    public void addIssue(Issue issue) {
        issues.add(issue);
        issue.setReader(this);
    }

    public void removeIssue(Issue issue) {
        issues.remove(issue);
        issue.setReader(null);
    }

    public Reader(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
