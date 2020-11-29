package grow.together.io.bookmarks.dtoModel;

import grow.together.io.bookmarks.validator.annotation.UniqueRoleName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoleDtoIn {
    @UniqueRoleName
    @NotEmpty
    private String name;
    @NotEmpty
    private List<String> permissions;

}
