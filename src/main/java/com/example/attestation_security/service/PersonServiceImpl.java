package com.example.attestation_security.service;

import com.example.attestation_security.models.Person;
import com.example.attestation_security.repo.IPersonRepository;
import com.example.attestation_security.service.interfaces.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private IPersonRepository personRepository;

    @Override
    public void create(Person person) {
        personRepository.save(person);
    }

    @Override
    public Iterable<Person> readAll() {
        return personRepository.findAll();
    }

    @Override
    public Person findByLoginAndPass(String login, String password) {
        return personRepository.findUserByLoginAndPassword(login, password);
    }

    @Override
    public Person findByLogin(String login) {
        return personRepository.findUserByLogin(login);
    }


    @Override
    public Person read(Long id) {
        return personRepository.findById(id).orElse(null);
    }

    @Override
    public boolean update(Person person, Long id) {
        var userDB = personRepository.findById(id).orElse(null);

        assert userDB != null;
        if(userDB.getId().equals(id)){//если юзер, которого нужно обновить существует
            person.setId(id);
            personRepository.save(person);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        var person = personRepository.findById(id).orElse(null);
        personRepository.deleteById(id);
        assert person != null;
        return person.getId() != null;
    }
}
