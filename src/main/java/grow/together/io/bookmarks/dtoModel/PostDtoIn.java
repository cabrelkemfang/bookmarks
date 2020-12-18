package grow.together.io.bookmarks.dtoModel;

import grow.together.io.bookmarks.validator.annotation.UniquePostLink;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDtoIn {
    @NotEmpty(message = "Please provide the link")
    @UniquePostLink
    private String link;
    @NotEmpty(message = "Please Provide a Tile")
    private String title;

    private  String author;

    private String imageLink;

    private String readTime;
    @NotEmpty(message = "Please provide the category")
    private List<String> category;
    @NotEmpty(message = "Please provide the Status")
    private String status;
}
