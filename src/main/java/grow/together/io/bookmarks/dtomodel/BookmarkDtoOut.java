package grow.together.io.bookmarks.dtomodel;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

}
