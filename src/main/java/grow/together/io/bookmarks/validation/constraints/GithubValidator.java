package grow.together.io.bookmarks.validation.constraints;

import grow.together.io.bookmarks.domain.User;
import grow.together.io.bookmarks.repository.UserRepository;
import grow.together.io.bookmarks.validation.UniqueGithub;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class GithubValidator implements ConstraintValidator<UniqueGithub, String> {

    private final UserRepository userRepository;

    @Autowired
    public GithubValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        Optional<User> user = this.userRepository.findByGithub(s);
        return !user.isPresent();
    }
}
