package com.daeta.board.service;

import com.daeta.board.common.ApiResponseDto;
import com.daeta.board.common.ResponseUtils;
import com.daeta.board.common.SuccessResponse;
import com.daeta.board.dto.LoginRequestDto;
import com.daeta.board.dto.SignupRequestDto;
import com.daeta.board.entity.User;
import com.daeta.board.entity.enumSet.ErrorType;
import com.daeta.board.entity.enumSet.UserRole;
import com.daeta.board.exception.RestApiException;
import com.daeta.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.event.spi.SaveOrUpdateEvent;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입
     */
    public ApiResponseDto<SuccessResponse> signup(SignupRequestDto requestDto){
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String email = requestDto.getEmail();

        //회원 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if(found.isPresent()){
            throw new RestApiException(ErrorType.DUPLICATED_USERNAME);
        }
        //입력한 username, password, email user 객체 만들어 repository에 저장.
        UserRole role = requestDto.getAdmin() ? UserRole.ADMIN : UserRole.USER;
        userRepository.save(User.of(username, password, email, role));

        return ResponseUtils.ok(SuccessResponse.of(HttpStatus.OK, "회원가입 성공"));
    }

    /**
     * 로그인
     */
    @Transactional(readOnly = true)
    public ApiResponseDto<SuccessResponse> login(LoginRequestDto requestDto){
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        //사용자 확인 & 비밀번호 확인
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isEmpty() || !passwordEncoder.matches(password, user.get().getPassword())){
            throw new RestApiException(ErrorType.NOT_MATCHING_INFO);
        }
        return ResponseUtils.ok(SuccessResponse.of(HttpStatus.OK,"로그인 성공"));
    }
}
