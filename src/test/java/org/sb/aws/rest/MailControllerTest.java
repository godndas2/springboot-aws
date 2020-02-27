package org.sb.aws.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.sb.aws.entity.user.UserRepository;
import org.sb.aws.rest.dto.EmailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest
public class MailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @MockBean
    JavaMailSender javaMailSender;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MailController mailController;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(mailController).build();
    }

    private EmailDto getEmailInfo() {
        String email = "wearegang369@gmail.com";
        String subject = "TITLE";
        EmailDto dto = new EmailDto();
        dto.setTo(email);
        dto.setSubject(subject);
        return dto;
    }
}
