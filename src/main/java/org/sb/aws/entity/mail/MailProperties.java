package org.sb.aws.entity.mail;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "mail")
@Getter
@Setter
public class MailProperties {

    @Getter
    @Setter
    public static class SMTP {
        String host;
        String port;
        String username;
        String password;
    }

    private SMTP smtp;
    private String from;
    private String fromName;
    private String verificationapi;
}
