package com.hot6.backend.user;

import com.hot6.backend.common.BaseResponseStatus;
import com.hot6.backend.common.exception.BaseException;
import com.hot6.backend.user.model.EmailVerify;
import com.hot6.backend.user.model.User;
import com.hot6.backend.user.model.UserDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;
    private final EmailVerifyRepository emailVerifyRepository;

    public void sendEmail(String uuid, String email) {
        System.out.println(email);
        String authUrl = "http://localhost:8080/user/verify-email?uuid=" + uuid;
        String htmlContent = """
        <div style="font-family: 'Apple SD Gothic Neo', 'sans-serif' !important; width: 540px; height: 600px; border-top: 4px solid #6A0104; margin: 100px auto; padding: 30px 0; box-sizing: border-box;">
            <h1 style="margin: 0; padding: 0 5px; font-size: 28px; font-weight: 400;">
                <span style="font-size: 15px; margin: 0 0 10px 3px;">펫로그</span><br />
                <span style="color: #6A0104;">이메일 인증</span> 안내입니다.
            </h1>
            <p style="font-size: 16px; line-height: 26px; margin-top: 50px; padding: 0 5px;">
                안녕하세요.<br />
                펫로그에 가입해 주셔서 진심으로 감사드립니다.<br />
                아래 <b style="color: #6A0104;">'메일 인증'</b> 버튼을 클릭하여 회원가입을 완료해 주세요.<br />
                감사합니다.
            </p>

            <a style="color: #FFF; text-decoration: none; text-align: center;" href="%s" target="_blank">
                <p style="display: inline-block; width: 210px; height: 45px; margin: 30px 5px 40px; background: #6A0104; line-height: 45px; vertical-align: middle; font-size: 16px;">메일 인증</p>
            </a>

            <div style="border-top: 1px solid #DDD; padding: 5px;">
                <p style="font-size: 13px; line-height: 21px; color: #555;">
                    만약 버튼이 정상적으로 클릭되지 않는다면, 아래 링크를 복사하여 접속해 주세요.<br />
                    %s
                </p>
            </div>
        </div>
        """.formatted(authUrl, authUrl);
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(email);
            helper.setSubject("[펫로그] 이메일 인증");
            helper.setText(htmlContent, true); // true: HTML 형식

            mailSender.send(message);
            System.out.printf("메일 전송 완료");
        } catch (MessagingException e) {
            // 예외 처리 로직
            e.printStackTrace();
        }
    }

    @Transactional
    public void verify(String uuid, HttpServletResponse response) {
        System.out.println(uuid);
        EmailVerify emailVerify = emailVerifyRepository.findByUuid(uuid).orElseThrow(
                () ->  new BaseException(BaseResponseStatus.INVALID_USER_INFO));

        User user = emailVerify.getUser();
        user.userVerify();

        userRepository.save(user);
        try {
            response.sendRedirect("http://localhost:5173/");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public UserDto.CreateResponse signup(UserDto.CreateRequest dto) {
        String uuid = UUID.randomUUID().toString();
        User user = userRepository.save(dto.toEntity(passwordEncoder.encode(dto.getPassword())));

        if(!user.getEnabled()) {
            emailVerifyRepository.save(EmailVerify.builder()
                    .user(user)
                    .uuid(uuid)
                    .build());
            sendEmail(uuid, dto.getEmail());
        }
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
                .enabled(false)
                .nickname(null)
                .build();
    }

    public void logout(HttpServletResponse response) {
        ResponseCookie deleteCookie = ResponseCookie.from("ATOKEN", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .build();

        response.setHeader("Set-Cookie", deleteCookie.toString());
    }
    public User findUserByIdx(Long idx) {
        return userRepository.findByIdx(idx).orElseThrow(() -> new BaseException(BaseResponseStatus.USER_NOT_FOUND));
    }

}
