package grow.together.io.bookmarks.dtoModel;

import grow.together.io.bookmarks.domain.Permission;
import grow.together.io.bookmarks.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDtoOut {
    private Long id;
    private String name;
    private List<String> permissions;

    public RoleDtoOut(Role role){
        this.id = role.getId();
        this.name=role.getName();
        this.permissions= role.getPermissions().stream().map(Permission::getName).collect(Collectors.toList());
    }
}
