package org.sb.aws.rest;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import lombok.RequiredArgsConstructor;
import org.sb.aws.entity.mail.VerificationToken;
import org.sb.aws.rest.dto.EmailDto;
import org.sb.aws.service.MailService;
import org.sb.aws.service.VerificationTokenService;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MailController {

    private final JavaMailSender mailSender;
    private final MailService mailService;
    private final Environment environment;
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
        String messageText =
                ("To confirm your account, please click here : "
                        +"http://localhost:8080/verify-email?code=" + verificationToken.getToken());

        try {
            m.execute(writer, emailDto).flush();
            messageText = writer.toString();
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
        return "redirect:/index";
    }

    ////////////////////////////////////////////////////////////
    /**
    * @author halfdev
    * @since 2020-02-23
    * TEST
    */

    @GetMapping("/verify-email")
    @ResponseBody
    public String verifyEmail(String code) {
        return verificationTokenService.verifyEmail(code).getBody();
    }

}