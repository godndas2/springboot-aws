package org.sb.aws.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sb.aws.entity.user.User;
import org.sb.aws.entity.user.UserRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    private final UserRepository userRepository;

    @Async
    public void sendEmail(SimpleMailMessage email) {
//            if (emailExist(attributes.getEmail())) {
//        throw new EmailExistsException(
//                "There is an account with that email address: "
//                + attributes.getEmail());
//    }
        javaMailSender.send(email);
    }


    private boolean emailExist(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent();
    }

}
