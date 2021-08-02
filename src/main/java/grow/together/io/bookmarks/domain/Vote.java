package grow.together.io.bookmarks.domain;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "qp_vote")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Vote {

    @Id
    @Column(name = "VOTE_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "VOTE_ID_OPTION_ID")
    private Option option;
}
