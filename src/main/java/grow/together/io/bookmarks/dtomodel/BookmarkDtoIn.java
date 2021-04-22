package grow.together.io.bookmarks.dtomodel;

import grow.together.io.bookmarks.validation.UniqueBookmarksLink;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookmarkDtoIn {
    @NotEmpty(message = "Please provide the link")
    @UniqueBookmarksLink
    private String link;

    @NotEmpty(message = "Please provide the category")
    private String category;

    @NotEmpty(message = "Please provide the Status")
    private String status;
}
