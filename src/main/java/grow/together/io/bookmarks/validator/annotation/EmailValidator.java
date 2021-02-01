package grow.together.io.bookmarks.validator.annotation;

import grow.together.io.bookmarks.domain.Subscriber;
import grow.together.io.bookmarks.domain.User;
import grow.together.io.bookmarks.dtomodel.SubcriberDtoIn;
import grow.together.io.bookmarks.dtomodel.UserDtaoIn;
import grow.together.io.bookmarks.repository.SubscribersRepository;
import grow.together.io.bookmarks.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class EmailValidator implements ConstraintValidator<UniqueEmail, Object> {
    private final SubscribersRepository subscribersRepository;
    private final UserRepository userRepository;

    @Autowired
    public EmailValidator(SubscribersRepository subscribersRepository, UserRepository userRepository) {
        this.subscribersRepository = subscribersRepository;
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {

        if (o instanceof SubcriberDtoIn) {
            Optional<Subscriber> subcriber = this.subscribersRepository.findByEmail(((SubcriberDtoIn) o).getEmail());
            return !subcriber.isPresent();
        }

        if (o instanceof UserDtaoIn) {
            Optional<User> user = this.userRepository.findByEmail(((UserDtaoIn) o).getEmail());
            return !user.isPresent();
        }

        return false;
    }
}
