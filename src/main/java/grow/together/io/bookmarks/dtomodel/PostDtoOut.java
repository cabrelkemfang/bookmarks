package grow.together.io.bookmarks.dtomodel;

import grow.together.io.bookmarks.domain.Category;
import grow.together.io.bookmarks.domain.Posts;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;
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
    private String createdAt;
    private String imageLink;
    private String description;

    public PostDtoOut(Posts posts) {
        this.link = posts.getMetaData().getUrl();
        this.category = posts.getCategories().stream().map(Category::getName).collect(Collectors.toList());
        this.like = posts.getLike();
        this.vue = posts.getVue();
        this.imageLink = posts.getMetaData().getImageLink();
        this.title = posts.getMetaData().getTitle();
        this.description = posts.getMetaData().getDescription();
        this.status = posts.getStatus().name();
        this.createdAt= posts.getCreatedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }
}
