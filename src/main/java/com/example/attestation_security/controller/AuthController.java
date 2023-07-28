package com.example.attestation_security.controller;

import com.example.attestation_security.domain.JwtRequest;
import com.example.attestation_security.domain.JwtResponse;
import com.example.attestation_security.domain.RefreshJwtRequest;
import com.example.attestation_security.domain.Start;
import com.example.attestation_security.service.AuthServiceDB;
import com.example.attestation_security.service.interfaces.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * В примере авторизации используется Bearer Token
 */
@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceDB authServiceDB;
    private final PersonService personService;

    /**
     * вход автоматически создает юзера или админа выдает токен при запросе Post
     * (для создания новой записи необходимо передать user или admin в формате запроса text)
     * @return
     */
    @PostMapping("start")
    public ResponseEntity<JwtResponse> start(@RequestBody String name) {
        Start start = new Start();
        final JwtResponse token = authServiceDB.login(start.create(name, personService));
        return ResponseEntity.ok(token);
    }

    @PostMapping("login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest authRequest) {
        final JwtResponse token = authServiceDB.login(authRequest);
        return ResponseEntity.ok(token);
    }

    @PostMapping("token")
    public ResponseEntity<JwtResponse> getNewAccessToken(@RequestBody RefreshJwtRequest request) {
        final JwtResponse token = authServiceDB.getAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    @PostMapping("refresh")
    public ResponseEntity<JwtResponse> getNewRefreshToken(@RequestBody RefreshJwtRequest request) {
        final JwtResponse token = authServiceDB.refresh(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }

}
