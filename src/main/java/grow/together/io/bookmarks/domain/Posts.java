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
@Table(name = "post")
@EqualsAndHashCode(callSuper = false)
public class Posts extends AuditableModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_id")
    private Long postId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "meta_data_id", referencedColumnName = "id")
    private MetaData metaData;

    @Column(name = "nbre_like")
    private int like;

    @Column(name = "nbre_vue")
    private int vue;

    @Version
    private Long version;

    private boolean isDeleted = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "post_status")
    private GroupStatus status;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "post_category",
            joinColumns = {@JoinColumn(name = "post_id")},
            inverseJoinColumns = {@JoinColumn(name = "categ_id")}
    )
    private List<Category> categories;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
