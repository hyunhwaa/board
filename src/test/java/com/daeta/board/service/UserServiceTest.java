//package com.daeta.board.service;
//
//import com.daeta.board.common.ApiResponseDto;
//import com.daeta.board.common.SuccessResponse;
//import com.daeta.board.dto.LoginRequestDto;
//import com.daeta.board.dto.SignupRequestDto;
//import com.daeta.board.entity.User;
//import com.daeta.board.entity.enumSet.ErrorType;
//import com.daeta.board.entity.enumSet.UserRole;
//import com.daeta.board.exception.RestApiException;
//import com.daeta.board.repository.UserRepository;
//import org.junit.Test;
//import org.junit.jupiter.api.Assertions;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpStatus;
//import org.springframework.mock.web.MockHttpServletResponse;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.servlet.http.HttpServletResponse;
//import java.util.Optional;
//
//import static org.junit.Assert.*;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@Transactional
//public class UserServiceTest {
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//
//    @Test
//    public void 회원가입_성공(){
//        //given
//        SignupRequestDto signupRequestDto = new SignupRequestDto();
//        signupRequestDto.setUsername("test");
//        signupRequestDto.setPassword("1234");
//        signupRequestDto.setEmail("email");
//        signupRequestDto.setAdmin(false);
//
//        //when
//        ApiResponseDto<SuccessResponse> response = userService.signup(signupRequestDto);
//
//        //then
//        assertEquals(HttpStatus.OK.value(), response.getResponse().getStatus());
//        assertEquals("회원가입 성공", response.getResponse().getMessage());
//
//        //회원이 실제로 저장되었는지 확인
//        //조회 결과 없으면 null 반환.
//        User savedUser = userRepository.findByUsername("test").orElse(null);
//        //savedUser가 null 아닌지 확인.
//        System.out.println("====aa===="+savedUser.getRole());
//        assertNotNull(savedUser);
//        assertEquals("test", savedUser.getUsername());
//        assertTrue(passwordEncoder.matches("1234", savedUser.getPassword()));
//        assertEquals(UserRole.USER, savedUser.getRole());
//    }
//
//    @Test(expected = RestApiException.class)
//    public void 중복회원_가입_실패(){
//        //given
//        SignupRequestDto signupRequestDto = new SignupRequestDto();
//        signupRequestDto.setUsername("test");
//        signupRequestDto.setPassword("1234");
//        signupRequestDto.setAdmin(false);
//
//        SignupRequestDto duplicated = new SignupRequestDto();
//        duplicated.setUsername("test");
//        duplicated.setPassword("1234");
//        duplicated.setAdmin(false);
//
//        //when
////        ApiResponseDto<SuccessResponse> response = userService.signup(signupRequestDto);
////        ApiResponseDto<SuccessResponse> exception = userService.signup(duplicated);
//
//        userService.signup(signupRequestDto);
//        userService.signup(duplicated);
//        //then
//
//    }
//
//    @Test
//    public void 로그인(){
//        //given
//        String username = "test";
//        String password = "1234";
//        String encoderPassword = passwordEncoder.encode(password);
//
//        User testUser = new User();
//        testUser.setUsername(username);
//        testUser.setPassword(encoderPassword);
//        testUser.setEmail("12@qw");
//        testUser.setRole(UserRole.USER);
//        userRepository.save(testUser);
//
//        LoginRequestDto loginRequestDto = new LoginRequestDto();
//        loginRequestDto.setUsername(username);
//        loginRequestDto.setPassword(password);
//
//
//        //when
//        ApiResponseDto<SuccessResponse> responseDto = userService.login(loginRequestDto);
//
//        //then
//        assertEquals("로그인 성공", responseDto.getResponse().getMessage());
//
//
//    }
//
//}