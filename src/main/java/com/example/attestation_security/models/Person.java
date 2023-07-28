package com.example.attestation_security.models;

//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Table(name="persons")
@Data

@NoArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    @Column(unique = true)
    private String login;

    private String password;

    private String role;

    public Person(String name, String login, String password, String role) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.role = role;
    }
}
