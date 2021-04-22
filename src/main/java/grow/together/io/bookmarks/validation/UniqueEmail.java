package grow.together.io.bookmarks.validation;

import grow.together.io.bookmarks.validation.constraints.EmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
public @interface UniqueEmail {
    String message() default "Email Already Use";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
