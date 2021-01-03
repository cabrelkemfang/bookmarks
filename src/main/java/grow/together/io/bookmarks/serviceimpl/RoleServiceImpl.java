package grow.together.io.bookmarks.serviceimpl;

import grow.together.io.bookmarks.common.VaraibleName;
import grow.together.io.bookmarks.domain.Role;
import grow.together.io.bookmarks.dtomodel.DataResponse;
import grow.together.io.bookmarks.dtomodel.PageableResult;
import grow.together.io.bookmarks.dtomodel.RoleDtoIn;
import grow.together.io.bookmarks.dtomodel.RoleDtoOut;
import grow.together.io.bookmarks.errorhandler.BadRequestException;
import grow.together.io.bookmarks.errorhandler.ResourceNotFoundException;
import grow.together.io.bookmarks.repository.PermissionRepository;
import grow.together.io.bookmarks.repository.RoleRepository;
import grow.together.io.bookmarks.repository.UserRepository;
import grow.together.io.bookmarks.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final UserRepository userRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, PermissionRepository permissionRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public DataResponse<RoleDtoOut> getRoleById(Long id) {

        Role role = this.findById(id);
        return new DataResponse<>("Role Load Successfully", HttpStatus.OK.value(), new RoleDtoOut(role));
    }

    @Override
    @Transactional
    public DataResponse<Void> createRole(RoleDtoIn roleDtoIn) {
        Role role = new Role();
        role.setName(roleDtoIn.getName());
        role.setPermissions(roleDtoIn.getPermissions().stream()
                .map(name -> this.permissionRepository.findByName(name)
                        .orElseThrow(() -> new ResourceNotFoundException("No Permission Found with the name :" + name)))
                .collect(Collectors.toList()));

        this.roleRepository.save(role);
        return new DataResponse<>("Role Created Successfully", HttpStatus.CREATED.value());
    }

    @Override
    public PageableResult<RoleDtoOut> getAllRole(int page, int size) {
        if (page < 0) {
            throw new BadRequestException(VaraibleName.PAGE_LESS_THAN_ZERO);
        }

        Page<Role> roles = this.roleRepository.findAll(PageRequest.of(page-1, size));
        return new PageableResult<>(page,
                size,
                roles.getTotalElements(),
                roles.getTotalPages(),
                roles.getContent().stream().map(RoleDtoOut::new).collect(Collectors.toList()));
    }

    @Override
    @Transactional
    public DataResponse<Void> deleteRole(Long roleId) {
        Role role = this.findById(roleId);
        long count = this.userRepository.countByRole(role.getName());
        if (count > 0) {
            throw new BadRequestException("The Role Can't Be Delete ,Is still be Assign to some users");
        }
        role.setDelete(true);
        this.roleRepository.save(role);
        return new DataResponse<>("Role Deleted Successfully", HttpStatus.NO_CONTENT.value());
    }

    @Override
    @Transactional
    public DataResponse<Void> updateRole(Long roleId, RoleDtoIn roleDtoIn) {
        Role role = this.findById(roleId);
        role.setName(roleDtoIn.getName());
        role.setPermissions(roleDtoIn.getPermissions().stream()
                .map(name -> this.permissionRepository.findByName(name)
                        .orElseThrow(() -> new ResourceNotFoundException("No Permission Found with the name :" + name)))
                .collect(Collectors.toList()));

        this.roleRepository.save(role);
        return new DataResponse<>("Role Successfully Updated", HttpStatus.CREATED.value());
    }

    public Role findById(Long roleId) {
        return this.roleRepository.findById(roleId).orElseThrow(() -> new ResourceNotFoundException("No Role found with id :" + roleId));
    }
}
