package com.hot6.backend.user;

import com.hot6.backend.pet.model.PetDto;
import com.hot6.backend.user.model.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping("/register")
    public UserDto.RegisterResponse register(@RequestBody UserDto.RegisterRequest registerRequest) {
        return UserDto.RegisterResponse.builder()
                .email("user01@gmail.com")
                .build();
    }

    @GetMapping("/login")
    public UserDto.LoginResponse login(@RequestBody UserDto.LoginRequest loginRequest) {
        return UserDto.LoginResponse.builder()
                .accessToken("ok")
                .build();
    }

    @GetMapping("/{idx}/profile")
    UserDto.UserProfileResponse getProfile(@PathVariable Long idx) {
        List<PetDto.PetCard> petCards = new ArrayList<>();
        return UserDto.UserProfileResponse.builder()
                .email("user" + idx + "@gmail.com")
                .nickname("User" + idx)
                .profileImageUrl("test")
                .petCards(petCards)
                .build();
    }

    @PostMapping("/{idx}/profile")
    public ResponseEntity<String> updateProfile(@PathVariable Long idx) {
        return ResponseEntity.ok("ok");
    }

    @PostMapping("/{idx}/password")
    public ResponseEntity<String> updatePassword(@PathVariable Long idx) {
        return ResponseEntity.ok("ok");
    }

    @DeleteMapping("/{idx}")
    public ResponseEntity<String> delete(@PathVariable Long idx) {
        return ResponseEntity.ok("ok");
    }
}
