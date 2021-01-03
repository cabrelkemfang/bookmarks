package grow.together.io.bookmarks.dtomodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDtaoOut {
    private String gmail;

    private String github;

    private String name;

    private boolean active;

    private long privatePost;

    private long publicPost;

    private String createdAt;

    private boolean isDelete;

}
