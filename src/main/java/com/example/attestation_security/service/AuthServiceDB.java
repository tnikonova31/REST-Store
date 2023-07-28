package com.example.attestation_security.service;

import com.example.attestation_security.domain.JwtAuthentication;
import com.example.attestation_security.domain.JwtRequest;
import com.example.attestation_security.domain.JwtResponse;
import com.example.attestation_security.exception.AuthException;
import com.example.attestation_security.models.Person;
import com.example.attestation_security.service.interfaces.PersonService;
import io.jsonwebtoken.Claims;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceDB {

    private final PersonService personService;
    private final Map<String, String> refreshStorage = new HashMap<>();
    private final JwtProvider jwtProvider;

    public JwtResponse login(@NonNull JwtRequest authRequest) {

        final Person person = personService.findByLogin(authRequest.getLogin()); //сначала поиск по логину
        if (person == null) {
            throw new AuthException("Пользователь не найден");
        }

        //если логин найден, то проверка пароля
        if (person.getPassword().equals(authRequest.getPassword())) { //используем геттер и сравниваем пароль со списком
            final String accessToken = jwtProvider.generateAccessToken(person); //если все нормально - то генерация токена
            final String refreshToken = jwtProvider.generateRefreshToken(person); //генерация рефреш токена
            refreshStorage.put(person.getLogin(), refreshToken); // помещаем рефреш токен в Hashmap с привязкой к логину
            return new JwtResponse(accessToken, refreshToken); //возвращаем аксесс и рефреш токены в объект jwtResponse
        } else {
            throw new AuthException("Неправильный пароль");
        }
    }

    public JwtResponse getAccessToken(@NonNull String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final Person person = personService.findByLogin(login);
                if (person == null) {
                    throw new AuthException("Пользователь не найден");
                }

                final String accessToken = jwtProvider.generateAccessToken(person);
                return new JwtResponse(accessToken, null);
            }
        }
        return new JwtResponse(null, null);
    }

    public JwtResponse refresh(@NonNull String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final Person person = personService.findByLogin(login);
                if (person == null) {
                    throw new AuthException("Пользователь не найден");
                }

                final String accessToken = jwtProvider.generateAccessToken(person);
                final String newRefreshToken = jwtProvider.generateRefreshToken(person);
                refreshStorage.put(person.getLogin(), newRefreshToken);
                return new JwtResponse(accessToken, newRefreshToken);
            }
        }
        throw new AuthException("Невалидный JWT токен");
    }

    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }

}
