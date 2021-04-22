package grow.together.io.bookmarks.validation.constraints;

import grow.together.io.bookmarks.domain.Category;
import grow.together.io.bookmarks.repository.CategoryRepository;
import grow.together.io.bookmarks.validation.UniqueCategoryName;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class CategoryNameValidator implements ConstraintValidator<UniqueCategoryName, String> {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryNameValidator(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        Optional<Category> category = this.categoryRepository.findByName(name);
        return !category.isPresent();
    }
}
