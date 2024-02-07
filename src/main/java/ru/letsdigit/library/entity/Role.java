package ru.letsdigit.library.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "role")
public class Role implements Serializable {
    @Id
    @GeneratedValue
    private UUID uuid;

    @Column(name = "role_title")
    private String roleTitle;

    public Role(String title) {
        this.roleTitle = title;
    }
}
