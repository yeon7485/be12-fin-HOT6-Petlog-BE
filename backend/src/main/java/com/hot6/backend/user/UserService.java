package com.hot6.backend.user;

import com.hot6.backend.user.model.User;
import com.hot6.backend.user.model.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDto.CreateResponse signup(UserDto.CreateRequest dto) {
        User user = userRepository.save(dto.toEntity(passwordEncoder.encode(dto.getPassword())));
        return UserDto.CreateResponse.from(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> result = userRepository.findByEmail(email);

        if (result.isPresent()) {
            User user = result.get();
            return user;
        }

        return null;
    }

    @Transactional
    public UserDto.LoginCheckResponse checkLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null &&
                authentication.isAuthenticated() &&
                !(authentication instanceof AnonymousAuthenticationToken)) {

            User user = (User) authentication.getPrincipal();

            return UserDto.LoginCheckResponse.from(user);
        }

        return UserDto.LoginCheckResponse.builder()
                .isLogin(false)
                .nickname(null)
                .build();
    }

}
