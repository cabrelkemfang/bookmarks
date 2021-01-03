package grow.together.io.bookmarks.validator.annotation;

import grow.together.io.bookmarks.dtomodel.UserDtaoIn;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordConfirmValidator implements ConstraintValidator<PasswordConfirmed, Object> {
    @Override
    public boolean isValid(Object user, ConstraintValidatorContext constraintValidatorContext) {

        String password = ((UserDtaoIn) user).getPassword();
        String confirmPassword = ((UserDtaoIn) user).getConfirmPassword();
        return password.equals(confirmPassword);
    }
}
