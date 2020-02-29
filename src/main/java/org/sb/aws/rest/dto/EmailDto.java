package org.sb.aws.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class EmailDto {

    @NotEmpty
    @Email
    public String to;

    public String subject;

    @JsonIgnore
    public List<String> tasks;

    private String verificationapi;

    private String verificationCode;

    public EmailDto() {
        tasks = new ArrayList<String>();
        tasks.add("");
    }

    /**
     * Gets tasks.
     * @return the tasks
     */
    public List<String> getTasks() {
        if (tasks == null) {
            tasks = new ArrayList<String>();
        }
        return tasks;
    }

}
