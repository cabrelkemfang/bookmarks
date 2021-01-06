package grow.together.io.bookmarks.dtomodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserStatusEventDto {
    private String email;
    private String status;
}
