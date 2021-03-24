package grow.together.io.bookmarks.validator.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PostLinkValidator.class)
@Documented
public @interface UniquePostLink {
    String message() default "The Link Already Been Register";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
