package org.sb.aws.entity.mail;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.sb.aws.entity.user.User;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
* @author halfdev
* @since 2020-02-22
* It must link back to the User (via a unidirectional relation)
 * It will be created right after registration
 * It will expire within 24 hours following its creation
 * Has a unique, randomly generated value
*/
@Entity
@Getter
@Setter
public class VerificationToken {
    public static final String STATUS_PENDING = "PENDING";
    public static final String STATUS_VERIFIED = "VERIFIED";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;
    private String status;
    private LocalDateTime expiredDateTime;
    private LocalDateTime issuedDateTime;
    private LocalDateTime confirmedDateTime;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    public VerificationToken(){
        this.token = UUID.randomUUID().toString();
        this.issuedDateTime = LocalDateTime.now();
        this.expiredDateTime = this.issuedDateTime.plusDays(1);
        this.status = STATUS_PENDING;
    }

}
