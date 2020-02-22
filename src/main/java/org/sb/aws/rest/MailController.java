package org.sb.aws.rest;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.apache.catalina.core.ApplicationPushBuilder;
import org.sb.aws.rest.dto.EmailDto;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.io.IOException;
import java.io.StringWriter;

@Controller
@RequiredArgsConstructor
public class MailController {

    private final JavaMailSender mailSender;

    @RequestMapping("/mail")
    public String displayEmailPage(EmailDto emailDto) {
        return "mail/sendEmail";
    }

    @RequestMapping(value = "send", method = RequestMethod.POST)
    public String sendEmail(@Valid EmailDto emailDto) {

        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache m = mf.compile("templates/email.mustache");

        StringWriter writer = new StringWriter();
        String messageText = "";
        try {
            m.execute(writer, emailDto).flush();
            messageText = writer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailDto.getTo());
        message.setSubject(emailDto.getSubject());

        message.setText(messageText);

        mailSender.send(message);

        return "mail/sendEmail";
    }

//    private final ApplicationEventPublisher eventPublisher;
//
//    @RequestMapping(value = "/user/registration", method = RequestMethod.POST)
//    public ModelAndView registerUserAccount(
//            @ModelAttribute("user") @Valid EmailDto accountDto,
//            BindingResult result,
//            WebRequest request,
//            Errors errors) {
//
//        if (result.hasErrors()) {
//            return new ModelAndView("registration", "user", accountDto);
//        }
//
//        User registered = createUserAccount(accountDto);
//        if (registered == null) {
//            result.rejectValue("email", "message.regError");
//        }
//        try {
//            String appUrl = request.getContextPath();
//            eventPublisher.publishEvent(new OnRegistrationCompleteEvent
//                    (registered, request.getLocale(), appUrl));
//        } catch (Exception me) {
//            return new ModelAndView("emailError", "user", accountDto);
//        }
//        return new ModelAndView("successRegister", "user", accountDto);
//    }
}