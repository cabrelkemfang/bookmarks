package grow.together.io.bookmarks.dtomodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class MetaDataDto {
    private String title;
    private String imageLink;
    private String description;
    private String url;
    private String siteName;
    private String iconLink;
}
