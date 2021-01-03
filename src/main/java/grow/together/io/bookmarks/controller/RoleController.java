package grow.together.io.bookmarks.controller;

import grow.together.io.bookmarks.dtoModel.DataResponse;
import grow.together.io.bookmarks.dtoModel.PageableResult;
import grow.together.io.bookmarks.dtoModel.RoleDtoIn;
import grow.together.io.bookmarks.dtoModel.RoleDtoOut;
import grow.together.io.bookmarks.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping(path = "/{role_id}")
    public DataResponse<RoleDtoOut> getRoleByid(@PathVariable Long role_id) {
        return this.roleService.getRoleById(role_id);
    }

    @PostMapping
    public DataResponse<Void> createRole(@Valid @RequestBody RoleDtoIn roleDtoIn) {
        return this.roleService.createRole(roleDtoIn);
    }

    @GetMapping
    public PageableResult<RoleDtoOut> getAllRole(@RequestParam(required = false, defaultValue = "1") int page,
                                                 @RequestParam(required = false, defaultValue = "10") int size) {
        return this.roleService.getAllRole(page, size);
    }

    @DeleteMapping(path = "/{role_id}")
    public DataResponse<Void> deleteRole(@PathVariable Long role_id) {
        return this.roleService.deleteRole(role_id);
    }

    @PutMapping(path = "/{role_id}")
    public DataResponse<Void> updateRole(@Valid @RequestBody RoleDtoIn roleDtoIn, @PathVariable Long role_id) {
        return this.roleService.updateRole(role_id, roleDtoIn);
    }
}
