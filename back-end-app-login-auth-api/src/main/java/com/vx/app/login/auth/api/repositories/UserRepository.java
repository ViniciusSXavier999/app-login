package com.vx.app.login.auth.api.repositories;

import com.vx.app.login.auth.api.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {


}
