package grow.together.io.bookmarks.validation;

import grow.together.io.bookmarks.validation.constraints.PostLinkValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PostLinkValidator.class)
@Documented
public @interface UniqueBookmarksLink {
    String message() default "The Link Already Been Register";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
