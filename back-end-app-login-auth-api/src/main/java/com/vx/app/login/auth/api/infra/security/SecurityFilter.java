package com.vx.app.login.auth.api.infra.security;

import com.vx.app.login.auth.api.domain.user.User;
import com.vx.app.login.auth.api.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    TokenService tokenService;
    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request);

        /*Depois que eu executo a função abaixo (recovertoken), eu eu chamo meu tokenService
        * e chamo a função de validateToken para validar o token*/
        var login = tokenService.validateToken(token);


        // Aqui estou verificando se o retorno nao é nullo, se nao for nullo eu vou entrar na lógica abaixo.
        if(login != null){

            /*Essa lógica vai buscar o usuário no banco de dados*/

            User user = userRepository.findByEmail(login).orElseThrow(() -> new RuntimeException("User Not Found"));
            var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
            var authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);

            /*É um contexto de segurança do spring security*/
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    /* Esse método está recebendo o request que veio do usuário */
    private String recoverToken(HttpServletRequest request){

    /*Depois ele vai pegar o header "Authorization desse request que é aonde vai estar o token
    * */
        var authHeader = request.getHeader("Authorization");

        // se nao tiver nada no header Authorization eu retorno nullo
        if(authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}
