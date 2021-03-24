package grow.together.io.bookmarks.dtomodel;

import grow.together.io.bookmarks.validator.annotation.UniquePostLink;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookmarkDtoIn {
    @NotEmpty(message = "Please provide the link")
    @UniquePostLink
    private String link;

    @NotEmpty(message = "Please provide the category")
    private String category;

    @NotEmpty(message = "Please provide the Status")
    private String status;
}
