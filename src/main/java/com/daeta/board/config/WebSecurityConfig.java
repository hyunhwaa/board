package com.daeta.board.config;

import com.daeta.board.BoardApplication;
import com.daeta.board.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.filters.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    // 비밀번호 인코더 빈 설정
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 웹 보안 커스터마이징을 위한 빈 설정
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // h2-console 사용 및 정적 리소스 접근 허용을 위한 설정
        return (web -> web.ignoring()
                .requestMatchers(PathRequest.toH2Console())
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()));
    }

    // 웹 보안 구성, 특정 경로는 무시
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**");
    }

    // HTTP 보안 구성
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .authorizeRequests()
                .antMatchers("/api/comment/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/likes/**").authenticated()
                .antMatchers(HttpMethod.DELETE, "/board/**").permitAll()
                .antMatchers("/members/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/members/login")
                .defaultSuccessUrl("/board/index")
                .permitAll()
                .and()
                .csrf()
                .requireCsrfProtectionMatcher(new AntPathRequestMatcher("/members/new"))
                .disable(); // CSRF 비활성화
    }
}