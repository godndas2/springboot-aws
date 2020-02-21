package org.sb.aws.service;

import lombok.RequiredArgsConstructor;
import org.sb.aws.entity.user.User;
import org.sb.aws.entity.user.UserRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    private final UserRepository userRepository;

    @Async
    public void sendMail(SimpleMailMessage message) {
        javaMailSender.send(message);
    }

    public boolean emailExist(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent();
    }
}
