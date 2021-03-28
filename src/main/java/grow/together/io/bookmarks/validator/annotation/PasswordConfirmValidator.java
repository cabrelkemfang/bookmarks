package grow.together.io.bookmarks.validator.annotation;

import grow.together.io.bookmarks.dtomodel.ResetPasswordDto;
import grow.together.io.bookmarks.dtomodel.UserDtaoIn;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordConfirmValidator implements ConstraintValidator<PasswordConfirmed, Object> {
    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {

        if (o instanceof UserDtaoIn) {
            String password = ((UserDtaoIn) o).getPassword();
            String confirmPassword = ((UserDtaoIn) o).getConfirmPassword();
            return password.equals(confirmPassword);
        }

        if (o instanceof ResetPasswordDto) {
            String password = ((ResetPasswordDto) o).getPassword();
            String confirmPassword = ((ResetPasswordDto) o).getConfirmPassword();
            return password.equals(confirmPassword);
        }
        return false;
    }
}
