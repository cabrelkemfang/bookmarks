package grow.together.io.bookmarks.eventListener;

import grow.together.io.bookmarks.domain.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UserRegistrationEvent extends ApplicationEvent {
    private final User user;

    public UserRegistrationEvent(User user) {
        super(user);
        this.user = user;
    }
}
