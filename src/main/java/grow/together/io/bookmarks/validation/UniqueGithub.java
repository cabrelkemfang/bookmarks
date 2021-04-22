package grow.together.io.bookmarks.validation;

import grow.together.io.bookmarks.validation.constraints.GithubValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = GithubValidator.class)
@Documented
public @interface UniqueGithub {
    String message() default "The Github Account is Already Been use";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
