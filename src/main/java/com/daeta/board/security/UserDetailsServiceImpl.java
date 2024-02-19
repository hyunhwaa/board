package com.daeta.board.security;

import com.daeta.board.entity.User;
import com.daeta.board.entity.enumSet.ErrorType;
import com.daeta.board.entity.enumSet.UserRole;
import com.daeta.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service    // custom 하고 Bean 으로 등록 후 사용 가능
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(ErrorType.NOT_FOUND_USER.getMessage()));

        return new UserDetailsImpl(user, user.getUsername());
    }
}