package com.vx.app.login.auth.api.controllers;

import com.vx.app.login.auth.api.domain.user.User;
import com.vx.app.login.auth.api.dto.LoginRequestDTO;
import com.vx.app.login.auth.api.dto.RegisterRequestDTO;
import com.vx.app.login.auth.api.dto.ResponseDTO;
import com.vx.app.login.auth.api.infra.security.TokenService;
import com.vx.app.login.auth.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO login ) {
        // vou verificar se existe um usuario com esse email
        User user = this.repository.findByEmail(login.email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // vou verificar se a senha do usuário é igual a que eu recebi por parametro
        if(passwordEncoder.matches(user.getPassword(), login.password())) {
            // se forem iguais eu vou criar um token
            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(new ResponseDTO(user.getName(), token));
        }
         else {
             return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO register ) {

        // vai ajudar na verificação se existe o usuario ou nao
        Optional<User> user = this.repository.findByEmail(register.email());

        // VERIFICAÇÃO SE O USUARIO JA EXISTE
        if (user.isEmpty()) {

            // vou criar o usuario
            User Newuser = new User();

            // salvando a senha criptografada
            Newuser.setPassword(passwordEncoder.encode(register.password()));

            Newuser.setEmail(register.email());
            Newuser.setName(register.name());

            // salvando esse novo usuario no banco de dados
            this.repository.save(Newuser);

                // se forem iguais eu vou criar um token
            String token = this.tokenService.generateToken(Newuser);
            return ResponseEntity.ok(new ResponseDTO(Newuser.getName(), token));

        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }

}
