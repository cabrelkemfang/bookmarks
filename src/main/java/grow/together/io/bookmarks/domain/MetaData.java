package grow.together.io.bookmarks.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "meta_data")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetaData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String title;

    @Column(name = "image_link")
    private String imageLink;

    @Column
    private String description;

    @Column
    private String url;

    @Column
    private String siteName;
}
