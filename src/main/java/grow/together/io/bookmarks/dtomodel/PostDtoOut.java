package grow.together.io.bookmarks.dtomodel;

import grow.together.io.bookmarks.domain.Category;
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

    private long id;
    private String link;
    private List<String> category;
    private int like;
    private String title;
    private int vue;
    private String status;
    private String createdAt;
    private String author;
    private String imageLink;
    private String readTime;

    public PostDtoOut(Posts posts) {
        this.id = posts.getId();
        this.link = posts.getLink();
        this.category = posts.getCategories().stream().map(Category::getName).collect(Collectors.toList());
        this.like = posts.getLike();
        this.vue = posts.getVue();
        this.author = posts.getAuthor();
        this.imageLink = posts.getImageLink();
        this.readTime = posts.getReadTime();
        this.title = posts.getTitle();
        this.status = posts.getStatus().name();
    }
}
