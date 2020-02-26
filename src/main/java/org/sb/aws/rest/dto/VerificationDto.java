package org.sb.aws.entity.mail;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class VerificationForm {

    @NotEmpty
    @Email
    private String email;

}
