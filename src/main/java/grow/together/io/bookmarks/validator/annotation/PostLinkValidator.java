package grow.together.io.bookmarks.validator.annotation;

import grow.together.io.bookmarks.domain.Posts;
import grow.together.io.bookmarks.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class PostLinkValidator implements ConstraintValidator<UniquePostLink, String> {

    private final PostRepository postRepository;

    @Autowired
    public PostLinkValidator(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        Optional<Posts> posts = this.postRepository.findByLink(value);
        return !posts.isPresent();
    }
}
