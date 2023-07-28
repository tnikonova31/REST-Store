package com.example.attestation_security.domain;

import com.example.attestation_security.service.UserServiceList;
import com.example.attestation_security.service.interfaces.PersonService;

public class Start {

    public JwtRequest create(String name, PersonService personService){
        UserServiceList person = new UserServiceList();
        JwtRequest authRequest = new JwtRequest();
        if(person.getByLogin(name).isPresent() && personService.findByLogin(name) == null) {
            personService.create(person.getByLogin(name).get());
        }
        if(person.getByLogin(name).isPresent()) {
            authRequest.setLogin(person.getByLogin(name).get().getLogin());
            authRequest.setPassword(person.getByLogin(name).get().getPassword());
        }

        return authRequest;
    }

}
