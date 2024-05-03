package com.vx.app.login.auth.api.infra.security;

import com.vx.app.login.auth.api.domain.user.User;
import com.vx.app.login.auth.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    /* Classe que vai retornar um USER na visão do spring, não um user da minha entidade usuario */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        /* Quando retornamos um optinal temos que fazer um ORELSE THROW */
        User user = this.repository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        /* Eu busco pelo usuario, assim que eu encontrar esse usuario eu vou ter que retornar um objeto do tipo
        * userDetails  */
         return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }
}
