package org.sb.aws.validation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sb.aws.config.auth.CustomOAuth2UserService;
import org.sb.aws.config.auth.dto.OAuthAttributes;
import org.sb.aws.entity.user.Role;
import org.sb.aws.entity.user.User;
import org.sb.aws.entity.user.UserRepository;
import org.sb.aws.exception.EmailExistsException;
import org.sb.aws.rest.dto.PostsSaveRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.UUID;

import static junit.framework.TestCase.assertTrue;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserIntegrationTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @PersistenceContext
    private EntityManager entityManager;

    @MockBean
    private JavaMailSender javaMailSender;

    private Long tokenId;
    private Long userId;

    @Before
    public void givenUserAndVerificationToken() throws EmailExistsException {
        User user = User.builder()
                .email("test@gmail.com")
                .name("testname")
                .role(Role.USER)
                .build();

        entityManager.persist(user);

        entityManager.flush();
        entityManager.clear();

        userId = user.getId();
    }

    @After
    public void flushAfter() {
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    public void whenContextLoad_thenCorrect() {
        assertTrue(userRepository.count() > 0);
    }

    @Test
    public void givenNewUser_whenRegistered_thenCorrect() throws EmailExistsException {
        final String userEmail = UUID.randomUUID().toString();
        final OAuthAttributes oAuthAttributes = createUserDto(userEmail);

        final User user = customOAuth2UserService.saveOrUpdate(oAuthAttributes);

        assertNotNull(user);
        assertNotNull(user.getEmail());
        assertEquals(userEmail, user.getEmail());
        assertNotNull(user.getId());
    }

    private OAuthAttributes createUserDto(final String email) {
        return OAuthAttributes.builder()
                .email(email)
                .name("testName")
                .build();
    }


}
