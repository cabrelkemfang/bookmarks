package grow.together.io.bookmarks.eventlistener;

import grow.together.io.bookmarks.domain.Subscriber;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class SubscriptionEvent extends ApplicationEvent {

    private final Subscriber subscriber;

    public SubscriptionEvent(Subscriber subscriber) {
        super(subscriber);
        this.subscriber = subscriber;
    }
}
