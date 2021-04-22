package grow.together.io.bookmarks.eventlistener.event;

import grow.together.io.bookmarks.domain.Subscriber;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class SubscriptionEvent extends ApplicationEvent {

    private final Subscriber subscriber;

    public SubscriptionEvent(Object source, Subscriber subscriber) {
        super(source);
        this.subscriber = subscriber;
    }
}
