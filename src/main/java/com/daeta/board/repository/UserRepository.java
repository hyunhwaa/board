package com.daeta.board.repository;

import com.daeta.board.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    /*
    * 같은 이름 (username) 중복 방지
    * */
    Optional<User> findByUsername(String username);
}
