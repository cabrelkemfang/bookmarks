package grow.together.io.bookmarks.domain;

import grow.together.io.bookmarks.dtomodel.AuditableModel;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "bookmark")
@EqualsAndHashCode(callSuper = false)
public class Bookmarks extends AuditableModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_id")
    private Long postId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "meta_data_id", referencedColumnName = "id")
    private MetaData metaData;

    @Version
    private Long version;

    @Enumerated(EnumType.STRING)
    @Column(name = "post_status")
    private GroupStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "catg_id", nullable = false)
    private Category categories;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
