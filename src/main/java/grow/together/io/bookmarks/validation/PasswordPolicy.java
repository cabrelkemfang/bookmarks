package grow.together.io.bookmarks.validation;

import grow.together.io.bookmarks.validation.constraints.PasswordPolicyValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordPolicyValidator.class)
public @interface PasswordPolicy {
    String message() default "Password is not Valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
