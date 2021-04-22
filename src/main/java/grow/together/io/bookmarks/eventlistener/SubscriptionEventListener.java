package grow.together.io.bookmarks.eventlistener;

import grow.together.io.bookmarks.domain.SubscriberStatus;
import grow.together.io.bookmarks.eventlistener.event.SubscriptionEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SubscriptionEventListener implements ApplicationListener<SubscriptionEvent> {

    @Override
    public void onApplicationEvent(SubscriptionEvent subscriptionEvent) {
        if (subscriptionEvent.getSubscriber().getStatus().equals(SubscriberStatus.SUBSCRIBE)) {
            // Sent A mail to inform that the user Have Subscribe to a th channel
            log.info("Subscrib");
        }
    }

}
