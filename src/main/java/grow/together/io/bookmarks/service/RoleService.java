package grow.together.io.bookmarks.service;

import grow.together.io.bookmarks.dtomodel.*;

public interface RoleService {
    DataResponse<RoleDtoOut> findRole(Long id);

    DataResponse<Void> createRole(RoleDtoIn roleDtoIn);

    PageableResult<RoleDtoOut> fetchRoles(int page, int size);

    DataResponse<Void> deleteRole(Long roleId);

    DataResponse<Void> updateRole(Long role_id, RoleDtoIn roleDtoIn);
}
