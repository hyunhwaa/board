package com.daeta.board.security;


import com.daeta.board.entity.User;
import com.daeta.board.entity.enumSet.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class UserDetailsImpl implements UserDetails {

    private final User user;
    private final String username;

    // 인증이 완료된 사용자 추가하기
    public UserDetailsImpl(User user, String username) {
        this.user = user;
        this.username = username;
    }

    public User getUser() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        UserRole role = user.getRole();
        String authority = role.getAuthority();

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
        Collection<GrantedAuthority> authorities = new ArrayList<>();   // 사용자 권한을 GrantedAuthority 로 추상화
        authorities.add(simpleGrantedAuthority);

        return authorities; // GrantedAuthority 로 추상화된 사용자 권한 반환
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        //계정 만료 여부, true 면 만료 안됨
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        //계정 잠금 여부, true면 잠금 안됨
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        //자격(비밀번호)만료 여부, true면 만료 안됨
        return true;
    }

    @Override
    public boolean isEnabled() {
        //계정 활성화 여부, true면 활성화
        return true;
    }
}