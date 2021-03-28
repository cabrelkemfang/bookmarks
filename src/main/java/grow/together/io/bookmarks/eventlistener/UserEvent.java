package grow.together.io.bookmarks.eventlistener;

import grow.together.io.bookmarks.domain.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UserEvent extends ApplicationEvent {
    private final User user;

    public UserEvent(User user) {
        super(user);
        this.user = user;
    }
}
