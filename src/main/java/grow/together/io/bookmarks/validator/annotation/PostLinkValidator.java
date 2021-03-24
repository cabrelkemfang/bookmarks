package grow.together.io.bookmarks.validator.annotation;

import grow.together.io.bookmarks.domain.Bookmarks;
import grow.together.io.bookmarks.repository.BookmarkRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class PostLinkValidator implements ConstraintValidator<UniquePostLink, String> {

    private final BookmarkRepository bookmarkRepository;

    @Autowired
    public PostLinkValidator(BookmarkRepository bookmarkRepository) {
        this.bookmarkRepository = bookmarkRepository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        Optional<Bookmarks> posts = this.bookmarkRepository.findByLink(value);
        return !posts.isPresent();
    }
}
