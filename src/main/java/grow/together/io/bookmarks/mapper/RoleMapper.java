package grow.together.io.bookmarks.mapper;

import grow.together.io.bookmarks.domain.Permission;
import grow.together.io.bookmarks.domain.Role;
import grow.together.io.bookmarks.dtomodel.RoleDtoOut;

import java.util.stream.Collectors;

public final class RoleMapper {


    public static RoleDtoOut map(final Role role) {
        final RoleDtoOut roleDtoOut = new RoleDtoOut();
        roleDtoOut.setId(role.getId());
        roleDtoOut.setName(role.getName());
        roleDtoOut.setPermissions(role.getPermissions().stream().map(Permission::getName).collect(Collectors.toList()));

        return roleDtoOut;
    }
}
