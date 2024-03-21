package com.nps.coco.domain.user.service;


import com.nps.coco.domain.user.dto.SignUpDto;
import com.nps.coco.domain.user.entity.User;
import com.nps.coco.domain.user.exception.DuplicateEmailException;
import com.nps.coco.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public SignUpDto signUp(SignUpDto signUpDto) {
        validateDuplicateEmail(signUpDto.getEmail());
        User user = new User(signUpDto);
        userRepository.save(user);
        return new SignUpDto(user.getEmail());
    }

    public void validateDuplicateEmail(String email) {
        userRepository.findByEmail(email)
                .ifPresent(user -> {
                    throw new DuplicateEmailException("이미 사용 중인 이메일입니다.");
                });
    }
}