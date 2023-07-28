package com.example.attestation_security.service.interfaces;

import com.example.attestation_security.models.Person;

public interface PersonService {

    /**
     * Метод создания нового клиента
     * @param person - объект, который будем добавлять
     */
    void create(Person person);

    /**
     * Получение всех клиентов в виде коллекции
     * @return
     */
    Iterable<Person> readAll();

    /**
     * Поиск пользователя по логину
     * @param login
     * @return
     */
    Person findByLogin(String login);

    /**
     * Поиск клиента по логину и паролю в базе данных
     * @param login - логин
     * @param password - пароль
     * @return
     */
    Person findByLoginAndPass(String login, String password);

    /**
     * Получаем все свойства клиента по id
     * @param id
     * @return user
     */
    Person read(Long id);

    /**
     * Изменение свойств клиента
     * @param person - тело запроса, включающее свойства, которые заменят старые свойства
     * @param id - id, по которму найдем клиента, которого обновляем
     * @return
     */
    boolean update(Person person, Long id);

    /**
     * Удаляем клиента по id
     * @param id
     * @return
     */
    boolean delete(Long id);

}