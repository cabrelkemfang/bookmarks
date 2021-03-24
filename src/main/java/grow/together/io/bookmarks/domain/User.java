package grow.together.io.bookmarks.domain;

import grow.together.io.bookmarks.dtomodel.AuditableModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
@EqualsAndHashCode(callSuper = false)
public class User extends AuditableModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String email;

    private String github;

    private String password;

    private String name;

    private boolean active = false;

    private boolean isDelete = false;

    @Column(name = "account_non_locked")
    private boolean accountNonLocked = true;

    @Column(name = "failed_attempt")
    private int failedAttempt = 0;

    @Column(name = "lock_time")
    private LocalDateTime lockTime;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;


}
