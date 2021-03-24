package grow.together.io.bookmarks.validator.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RoleNameValidator.class)
public @interface UniqueRoleName {
    String message() default "Name Already Use";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
