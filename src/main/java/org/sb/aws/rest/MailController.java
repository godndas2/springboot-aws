package org.sb.aws.rest;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import lombok.RequiredArgsConstructor;
import org.sb.aws.config.auth.dto.OAuthAttributes;
import org.sb.aws.entity.mail.VerificationToken;
import org.sb.aws.entity.user.User;
import org.sb.aws.rest.dto.EmailDto;
import org.sb.aws.service.MailService;
import org.sb.aws.service.VerificationTokenService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

@Controller
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;
    private final VerificationTokenService verificationTokenService;

    @GetMapping("/mail")
    public String displayEmailPage(EmailDto emailDto) {
        return "mail/sendEmail";
    }

    // Send Email
    @PostMapping(value = "send")
    @ResponseStatus(HttpStatus.OK)
    public String sendEmail(@Valid EmailDto emailDto) {

        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache m = mf.compile("templates/email.mustache");
        VerificationToken verificationToken = new VerificationToken();
        StringWriter writer = new StringWriter();

        try {
            m.execute(writer, emailDto).flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        verificationTokenService.createVerification(emailDto);
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(emailDto.getTo());
        message.setSubject("Welcome Springboot aws");
        // TODO Mail 에 Token 은 날아가는데 DB의 Token 값이 다르다?
        message.setText("http://localhost:8080/verify-email?code=" + verificationToken.getToken());
        mailService.sendEmail(message);
        return "mail/sendEmail";
    }

    @GetMapping("/verify-email")
    @ResponseBody
    public String verifyEmail(String code) {
        return verificationTokenService.verifyEmail(code).getBody();
    }

}