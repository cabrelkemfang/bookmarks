package grow.together.io.bookmarks.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.function.Predicate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nom;

    private boolean isDeleted = false;

    @Enumerated(EnumType.STRING)
    private GroupStatus groupStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
