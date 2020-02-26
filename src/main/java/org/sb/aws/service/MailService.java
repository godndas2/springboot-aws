package org.sb.aws.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sb.aws.rest.dto.EmailDto;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final Configuration templates;
    private final JavaMailSender javaMailSender;

    // send to Mail, token
//    public boolean sendVerificationMail(String toEmail, String verificationCode) {
//
//        EmailDto emailDto = new EmailDto();
//        String subject = "TEST";
//
//        String body = "";
//        try {
//            Template t = templates.getTemplate("email-verification.ftl");
//            Map<String, String> map = new HashMap<>();
//            map.put("VERIFICATION_URL", emailDto.getVerificationapi() + verificationCode);
//            body = FreeMarkerTemplateUtils.processTemplateIntoString(t, map);
//        } catch (Exception ex) {
//            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
//        }
//        return sendMail(toEmail, subject, body);
//    }

    @Async
    public void sendEmail(SimpleMailMessage email) {
        javaMailSender.send(email);
    }

//    private final MustacheResourceTemplateLoader mustacheResourceTemplateLoader;
//    private final Mustache.Compiler compiler;
//    public String processTemplateIntoString(String templateName, Map model) {
//        try {
//            String content = compiler.compile(mustacheResourceTemplateLoader.getTemplate(templateName)).execute(model);
//            return content;
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.error(e.getMessage());
//            return "Unable to render " + templateName + ". Please check logs for details";
//        }
//    }
}
