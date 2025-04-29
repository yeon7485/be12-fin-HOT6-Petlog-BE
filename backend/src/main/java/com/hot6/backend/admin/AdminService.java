package com.hot6.backend.admin;

import com.hot6.backend.common.BaseResponseStatus;
import com.hot6.backend.common.exception.BaseException;
import com.hot6.backend.user.UserRepository;
import com.hot6.backend.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    public List<User> getDeletedUsers() {
        return userRepository.findByIsDeletedTrue();
    }

    @Transactional(readOnly = false)
    public void restoreUser(Long userIdx) {
        Optional<User> userOptional = userRepository.findById(userIdx);
        if (!userOptional.isPresent()) {
            throw new BaseException(BaseResponseStatus.USER_NOT_FOUND);
        }

        User user = userOptional.get();
        user.setIsDeleted(false);
        userRepository.save(user);
    }
}
