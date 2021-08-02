package grow.together.io.bookmarks.domain;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "qp_poll")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Poll {
    @Id
    @Column(name = "POLL_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @Column
    private String question;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "POLL_ID_OPTION_ID", nullable = false)
    @OrderBy
    private List<Option> options;
}
