package com.daeta.board.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.constraints.Pattern;

@Getter
@Setter
public class SignupRequestDto {

    //아이디 유효성 검사
    @Pattern(regexp = "^[a-z0-9]{4,10}$", message = "아이디는 4~10자리 영문 소문자(a~z), 숫자(0~9)를 사용하세요.")
    private String username;

    @Pattern(regexp = "^[a-zA-Z0-9~!@#$%^&*()_+=?,./<>{}\\[\\]\\-]{8,15}$", message = "비밀번호는 8~15자리 영문 대소문자(a~z, A~Z), 숫자(0~9), 특수문자를 사용하세요")
    private String password;

    private String email;

    private Boolean admin;


}
