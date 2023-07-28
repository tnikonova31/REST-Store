package com.example.attestation_security.service;

import com.example.attestation_security.domain.Role;
import com.example.attestation_security.models.Person;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceList {

    private final List<Person> users;

    public UserServiceList() {
        this.users = List.of(
                new Person("User", "user", "user", Role.USER.name()),
                new Person("Admin", "admin", "admin", Role.ADMIN.name())
        );
    }


    /**
     * Метод поиска и сравнения логина с введенными данными
     * @param login
     * @return
     */

    public Optional<Person> getByLogin(@NonNull String login) {
        return users.stream()
                .filter(user -> login.equals(user.getLogin()))
                .findFirst();
    }

}