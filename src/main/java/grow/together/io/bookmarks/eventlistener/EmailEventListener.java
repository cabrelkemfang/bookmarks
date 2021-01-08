package grow.together.io.bookmarks.eventlistener;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class EmailEventListener implements ApplicationListener<UserRegistrationEvent> {

    @Override
    public void onApplicationEvent(UserRegistrationEvent userRegistrationEvent) {
        log.info("Message Send to admin to inform that a user account have been created");
    }
}
