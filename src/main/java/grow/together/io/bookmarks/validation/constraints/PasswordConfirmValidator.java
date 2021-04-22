package grow.together.io.bookmarks.validation.constraints;

import grow.together.io.bookmarks.dtomodel.ResetPasswordDto;
import grow.together.io.bookmarks.dtomodel.UserDtaoIn;
import grow.together.io.bookmarks.validation.ConfirmedPassword;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordConfirmValidator implements ConstraintValidator<ConfirmedPassword, Object> {
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
