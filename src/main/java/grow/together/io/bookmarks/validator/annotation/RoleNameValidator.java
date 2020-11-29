package grow.together.io.bookmarks.validator.annotation;

import grow.together.io.bookmarks.domain.Role;
import grow.together.io.bookmarks.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class RoleNameValidator implements ConstraintValidator<UniqueRoleName, String> {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleNameValidator(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        Optional<Role> role = this.roleRepository.findByName(s);
        return !role.isPresent();
    }
}
