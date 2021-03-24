package grow.together.io.bookmarks.dtomodel;


import grow.together.io.bookmarks.domain.Bookmarks;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkDtoOut {

    private Long id;
    private String link;
    private String category;
    private String title;
    private String status;
    private String createdAt;
    private String imageLink;

    public BookmarkDtoOut(Bookmarks bookmarks) {
        this.id = bookmarks.getPostId();
        this.link = bookmarks.getMetaData().getUrl();
        this.category = bookmarks.getCategories().getName();
        this.imageLink = bookmarks.getMetaData().getImageLink();
        this.title = bookmarks.getMetaData().getTitle();
        this.status = bookmarks.getStatus().name();
        this.createdAt = bookmarks.getCreatedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }
}
