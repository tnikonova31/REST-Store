package com.example.attestation_security.repo;

import com.example.attestation_security.models.Person;
import org.springframework.data.repository.CrudRepository;

public interface IPersonRepository extends CrudRepository<Person,Long> {
    Person findUserByLogin(String login);
    Person findUserByLoginAndPassword(String login, String password);
}