package com.example.attestation_security.models.answers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class AnswerModel {

    public static ResponseEntity<?> createEntity(String entity){
        return new ResponseEntity<>(new ApiAnswer(HttpStatus.CREATED.value(),
                entity + " created"),
                HttpStatus.CREATED);
    }

    public static ResponseEntity<?> noCreateEntity(String entity){
        return new ResponseEntity<>(new ApiAnswer(HttpStatus.BAD_REQUEST.value(),
                entity + " not created. Duplicate data to create"),
                HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<?> noAccess(){
        return new ResponseEntity<>(new ApiAnswer(HttpStatus.FORBIDDEN.value(),
                "No access right"),
                HttpStatus.FORBIDDEN);
    }

    public static ResponseEntity<?> noAuthorized(){
        return new ResponseEntity<>(new ApiAnswer(HttpStatus.UNAUTHORIZED.value(),
                "This token not authorized"),
                HttpStatus.UNAUTHORIZED);
    }

    public static ResponseEntity<?> badRequestBody(){
        return new ResponseEntity<>(new ApiAnswer(HttpStatus.NOT_ACCEPTABLE.value(),
                "RequestBody invalid"),
                HttpStatus.NOT_ACCEPTABLE);
    }

    public static ResponseEntity<?> updateEntity(String entity){
        return new ResponseEntity<>(new ApiAnswer(HttpStatus.CREATED.value(),
                entity + " updated"),
                HttpStatus.CREATED);
    }

    public static ResponseEntity<?> noUpdateEntity(String entity){
        return new ResponseEntity<>(new ApiAnswer(HttpStatus.BAD_REQUEST.value(),
                entity + " not updated. Duplicate data for update"),
                HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<?> deleteEntity(String entity){
        return new ResponseEntity<>(new ApiAnswer(HttpStatus.OK.value(),
                entity + " removed"),
                HttpStatus.OK);
    }

    public static ResponseEntity<?> noDeleteEntity(String entity){
        return new ResponseEntity<>(new ApiAnswer(HttpStatus.BAD_REQUEST.value(),
                entity + " not deleted. Object missing"),
                HttpStatus.BAD_REQUEST);
    }
    public static ResponseEntity<?> canNotDeleteEntity(String entity){
        return new ResponseEntity<>(new ApiAnswer(HttpStatus.METHOD_NOT_ALLOWED.value(),
                entity + " can't deleted."),
                HttpStatus.METHOD_NOT_ALLOWED);
    }

    public static ResponseEntity<?> noFindEntity(String entity) {
        return new ResponseEntity<>(new ApiAnswer(HttpStatus.NOT_FOUND.value(),
                entity + " not find."),
                HttpStatus.NOT_FOUND);
    }
    public static ResponseEntity<?> noInfo() {
        return new ResponseEntity<>(new ApiAnswer(HttpStatus.BAD_REQUEST.value(),
                "Invalid info."),
                HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<?> signinOk(String token) {
        return new ResponseEntity<>(new ApiAnswer(HttpStatus.OK.value(),
                "Success authorization. Token: " + token),
                HttpStatus.OK);
    }

    public static ResponseEntity<?> logout() {
        return new ResponseEntity<>(new ApiAnswer(HttpStatus.OK.value(),
                "Success logout."),
                HttpStatus.OK);
    }
}
