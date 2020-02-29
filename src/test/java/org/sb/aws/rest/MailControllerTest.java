package org.sb.aws.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sb.aws.entity.user.UserRepository;
import org.sb.aws.rest.dto.EmailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
@WebAppConfiguration
@SpringBootTest
public class MailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    JavaMailSender javaMailSender;

    @Autowired
    WebApplicationContext context;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void getVerifyEmail() throws Exception {
        String code = "1eff5e02-61d9-4629-9658-4eea2e26ab60";

        mockMvc.perform(MockMvcRequestBuilders.get("/verify-email")
        .param("code",code))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
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
