package org.sb.aws.service;

import lombok.RequiredArgsConstructor;
import org.sb.aws.entity.mail.VerificationToken;
import org.sb.aws.entity.mail.VerificationTokenRepository;
import org.sb.aws.entity.user.Role;
import org.sb.aws.entity.user.User;
import org.sb.aws.entity.user.UserRepository;
import org.sb.aws.rest.dto.EmailDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VerificationTokenService {

    private final VerificationTokenRepository verificationTokenRepository;
    private final UserRepository userRepository;

    public void createVerification(EmailDto dto) {
        Optional<User> users = userRepository.findByEmail(dto.getTo());
        User user;

        if (users.isPresent()) {
            user = User.builder()
                    .email(dto.getTo())
                    .name(dto.getTo())
                    .role(Role.USER)
                    .build();
            userRepository.save(user);
        } else {
            user = users.orElse(User.builder().email(dto.getTo()).build());
        }

        List<VerificationToken> verificationTokens = verificationTokenRepository.findByUserEmail(dto.getTo());
        VerificationToken verificationToken;
        if (verificationTokens.isEmpty()) {
            verificationToken = new VerificationToken();
            verificationToken.setUser(user);
            verificationTokenRepository.save(verificationToken);
        } else {
//            verificationToken = verificationTokens.get(0);
                throw new IllegalStateException("ERROR");
        }
    }

    public ResponseEntity<String> verifyEmail(String token) {
        List<VerificationToken> verificationTokens = verificationTokenRepository.findByToken(token);
        if (verificationTokens.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid token.");
        }

        VerificationToken verificationToken = verificationTokens.get(0);
        if (verificationToken.getExpiredDateTime().isBefore(LocalDateTime.now())) {
            return ResponseEntity.unprocessableEntity().body("Expired token.");
        }

        verificationToken.setConfirmedDateTime(LocalDateTime.now());
        verificationToken.setStatus(VerificationToken.STATUS_VERIFIED);
        verificationToken.getUser().setEnabled(true);
        verificationTokenRepository.save(verificationToken);

        return ResponseEntity.ok("You have successfully verified your email address.");
    }
}
