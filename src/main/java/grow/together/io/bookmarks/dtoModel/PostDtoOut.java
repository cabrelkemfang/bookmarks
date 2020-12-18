package grow.together.io.bookmarks.dtoModel;

import grow.together.io.bookmarks.domain.Posts;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDtoOut {

    private String link;
    private List<String> category;
    private int like;
    private String title;
    private int vue;
    private String status;
    private String created_at;
    private String author;
    private String imageLink;
    private String readTime;

    public PostDtoOut(Posts posts) {
        this.link = posts.getLink();
        this.category = posts.getCategories().stream().map(categ -> categ.getName()).collect(Collectors.toList());
        this.like = posts.getLike();
        this.vue = posts.getVue();
        this.author = posts.getAuthor();
        this.imageLink = posts.getImageLink();
        this.readTime = posts.getReadTime();
        this.title = posts.getTitle();
        this.status = posts.getStatus().name();
        this.created_at = this.getCreated_at();
    }
}
