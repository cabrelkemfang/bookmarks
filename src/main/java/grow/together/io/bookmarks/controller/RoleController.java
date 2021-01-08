package grow.together.io.bookmarks.controller;

import grow.together.io.bookmarks.dtomodel.DataResponse;
import grow.together.io.bookmarks.dtomodel.PageableResult;
import grow.together.io.bookmarks.dtomodel.RoleDtoIn;
import grow.together.io.bookmarks.dtomodel.RoleDtoOut;
import grow.together.io.bookmarks.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/bookmarks/v1/admin/role")
public class RoleController {
    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping(path = "/{roleId}")
    @PreAuthorize("hasAuthority('VIEW_ROLE')")
    public DataResponse<RoleDtoOut> getRoleById(@PathVariable Long roleId) {
        return this.roleService.getRoleById(roleId);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_ROLE')")
    public DataResponse<Void> createRole(@Valid @RequestBody RoleDtoIn roleDtoIn) {
        return this.roleService.createRole(roleDtoIn);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('VIEW_ROLES')")
    public PageableResult<RoleDtoOut> getAllRole(@RequestParam(required = false, defaultValue = "1") int page,
                                                 @RequestParam(required = false, defaultValue = "10") int size) {
        return this.roleService.getAllRole(page, size);
    }

    @DeleteMapping(path = "/{roleId}")
    @PreAuthorize("hasAuthority('DELETE_ROLE')")
    public DataResponse<Void> deleteRole(@PathVariable Long roleId) {
        return this.roleService.deleteRole(roleId);
    }

    @PutMapping(path = "/{roleId}")
    @PreAuthorize("hasAuthority('UPDATE_ROLE')")
    public DataResponse<Void> updateRole(@Valid @RequestBody RoleDtoIn roleDtoIn, @PathVariable Long roleId) {
        return this.roleService.updateRole(roleId, roleDtoIn);
    }
}
