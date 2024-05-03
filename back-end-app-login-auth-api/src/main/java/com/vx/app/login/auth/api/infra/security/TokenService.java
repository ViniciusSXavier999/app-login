package com.vx.app.login.auth.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.vx.app.login.auth.api.domain.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    // vou salvar a chave privada no meu application properties

    /* O value indica que estou recuperando um valor do application properties */
    @Value("${api.security.token.secret}")
    private String secret;

    /*Método que gera o token quando o usuário tiver fazendo login para gente
    * já retornar o token pro usuário*/

    // Estamos recebendo um User como parâmetro
    public String generateToken(User user) {
        try {
            /* 1 -> Primeiro vamos definir o algoritmo de criptografia */

            /* Quando a gente defini que vai utilizar esse algoritmo, precisamos passar
            * uma SECRET KEY que vai ser a chave privada para nossa criptografia*/
            Algorithm algorithm = Algorithm.HMAC256(secret);

            // fazendo a geração do token
            String token = JWT.create()
                    .withIssuer("back-end-app-login-auth-api")
                    // qual o sujeito que está ganhando esse token, nesse caso será o email
                    .withSubject(user.getEmail())
                    // Data que o token será expirado
                    .withExpiresAt(this.generateExpirationDate())
                    // Aqui vamos gerar o token oficialmente
                    .sign(algorithm);
            return token;

        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro enquanto estava autenticando");
        }
    }

    // FUNÇÃO PARA VALIDAR O TOKEN
    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.require(algorithm)
                    .withIssuer("back-end-app-login-auth-api")
                    // Montando o objeto para fazer a verificação
                    .build()

                    // aqui tenho que passar o token para verificar o mesmo.
                    .verify(token)
                    /*Aqui estamos pegando de volta o valor que a gente salvou
                    * quando estamos gerando o token no withSubject*/
                    .getSubject();

                    // caso dê algum erro eu vou retornar essa exceção e retornar null
        } catch (JWTVerificationException exception) {
            return null;
        }
    }

    // FUNÇÃO PARA DESIGNAR A DURAÇÃO DO TOKEN
    private Instant generateExpirationDate() {
        // o token vai durar 2 horas
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
