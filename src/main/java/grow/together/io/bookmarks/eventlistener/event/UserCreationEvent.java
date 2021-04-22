package grow.together.io.bookmarks.eventlistener.event;

import grow.together.io.bookmarks.domain.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UserCreationEvent extends ApplicationEvent {

    private final User user;

    public UserCreationEvent(Object source, User user) {
        super(source);
        this.user = user;
    }
}
