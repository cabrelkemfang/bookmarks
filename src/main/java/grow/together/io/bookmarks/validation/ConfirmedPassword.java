package grow.together.io.bookmarks.validation;

import grow.together.io.bookmarks.validation.constraints.PasswordConfirmValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordConfirmValidator.class)
public @interface ConfirmedPassword {
    String message() default "Password Do Not Match";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
