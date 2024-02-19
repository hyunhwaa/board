package com.daeta.board.controller;

import com.daeta.board.dto.LoginRequestDto;
import com.daeta.board.dto.SignupRequestDto;
import com.daeta.board.exception.RestApiException;
import com.daeta.board.security.UserDetailsImpl;
import com.daeta.board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class UserController {
    private final UserService userService;
    @GetMapping("/new")
    public String createForm(Model model) {
        SignupRequestDto requestDto = new SignupRequestDto();
        model.addAttribute("requestDto", new SignupRequestDto());

        return "members/signForm";
    }

    @PostMapping("/new")
    public String create(@Valid @ModelAttribute("requestDto") SignupRequestDto requestDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("requestDto", requestDto);       //입력된 데이터 유지
            return "members/signForm";
        }
        try {
            userService.signup(requestDto);
            model.addAttribute("successMessage", "회원가입이 완료되었습니다.");
            return "members/loginForm";
        } catch (RestApiException e) {
            model.addAttribute("duplicatedUsername", e.getErrorType().getMessage());
            return "members/signForm";
        }
    }
    @GetMapping("/login")
    public String loginForm(Model model) {
        LoginRequestDto requestDto = new LoginRequestDto();
        model.addAttribute("requestDto", new LoginRequestDto());

        return "members/loginForm";
    }
    @PostMapping("/login")
    public String login(@ModelAttribute("requestDto") LoginRequestDto requestDto, Model model, HttpSession session) {
        try {
            userService.login(requestDto);
            return "redirect:/members/index";
        } catch (RestApiException e) {
            model.addAttribute("notMatchingInfo", e.getErrorType().getMessage());
            return "members/loginForm";
        }
    }
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();       //세션 무효화
        return "redirect:/members/login";
    }

}

