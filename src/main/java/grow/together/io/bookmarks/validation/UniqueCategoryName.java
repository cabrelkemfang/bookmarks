package grow.together.io.bookmarks.validation;

import grow.together.io.bookmarks.validation.constraints.CategoryNameValidator;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CategoryNameValidator.class)
@Documented
public @interface UniqueCategoryName {
    String message() default "Category Already Exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
