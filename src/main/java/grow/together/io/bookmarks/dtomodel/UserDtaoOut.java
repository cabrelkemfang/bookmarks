package grow.together.io.bookmarks.dtomodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDtaoOut {
    private Long id;
    private String email;
    private String github;
    private String name;
    private boolean active;
    private long privateBookmarks;
    private long publicBookmarks;
    private String createdAt;
    private String roleName;
}
