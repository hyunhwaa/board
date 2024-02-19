package com.daeta.board.entity;

import com.daeta.board.dto.SignupRequestDto;
import com.daeta.board.entity.enumSet.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Entity(name = "users")
@Getter @Setter
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 20)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 30)
    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    @Column(unique = true, nullable = false)
    private String email;


    @Builder
    public User(String username, String password, String email, UserRole role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }


    public static User of(String username, String password, String email, UserRole role) {
        return User.builder()
                .username(username)
                .password(password)
                .email(email)
                .role(role)
                .build();
    }


}
