package org.sb.aws.service;

import lombok.RequiredArgsConstructor;
import org.sb.aws.entity.user.User;
import org.sb.aws.entity.user.UserRepository;
import org.sb.aws.entity.user.VerificationToken;
import org.sb.aws.entity.user.VerificationTokenRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VerificationTokenService {

    private UserRepository userRepository;
    private VerificationTokenRepository verificationTokenRepository;
    private MailService mailService;

    public void createVerification(String email) {
        Optional<User> users = userRepository.findByEmail(email);
        User user;
        if (users.isPresent()) {
            user = User.builder().email(email).build();
            userRepository.save(user);
        } else {
            user = users.get(); // get(0); ?
        }

        List<VerificationToken> verificationTokens = verificationTokenRepository.findByUserEmail(email);
        VerificationToken verificationToken;
        if (verificationTokens.isEmpty()) {
            verificationToken = new VerificationToken();
            verificationToken.setUser(user);
            verificationTokenRepository.save(verificationToken);
        } else {
            verificationToken = verificationTokens.get(0);
        }

        mailService.sendVerificationMail(email, verificationToken.getToken());
    }

    // TODO verifyEmail
}
