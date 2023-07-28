package com.example.attestation_security.controller;

import com.example.attestation_security.models.Person;
import com.example.attestation_security.service.AuthServiceDB;
import com.example.attestation_security.service.interfaces.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class PersonController {
    private final PersonService personService;

    /**
     * вывод всех клиентов
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')") //аннотация выполняющая аутентификацию и авторизацию пользователя
    @GetMapping("/persons")
    public ResponseEntity<Iterable<Person>> read(){
        Iterable<Person> persons = personService.readAll();
        return ResponseEntity.ok(persons);
    }

    /**
     * создание нового клиента с проверкой на дубликат
     * @param person
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/persons")
    public ResponseEntity<?> create(@RequestBody Person person){
        if(personService.findByLoginAndPass(person.getLogin(), person.getPassword()) == null){
            try{
                personService.create(person);
                return new ResponseEntity<String>("Новая запись клиента создана", HttpStatus.CREATED);
            }catch (Exception e){
                return new ResponseEntity<String>("Ошибка создания записи",HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<String>("Запись не создана, дубликат",HttpStatus.BAD_REQUEST);
    }

    /**
     * Удаление пользователя
     * @param id
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping(value = "/persons/{id}")
    public ResponseEntity<?> delete(@PathVariable(name="id") Long id){
        boolean isRemoved = personService.delete(id);
        return isRemoved
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}