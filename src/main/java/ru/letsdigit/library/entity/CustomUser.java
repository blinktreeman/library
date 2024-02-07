package ru.letsdigit.library.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "custom_user")
public class CustomUser implements Serializable {
    @Id
    @GeneratedValue
    private UUID uuid;

    @Column(name = "user_login")
    private String userLogin;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "custom_user_uuid"),
            inverseJoinColumns = @JoinColumn(name = "role_uuid")
    )
    private List<Role> roles = new ArrayList<>();
}
