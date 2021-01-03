package grow.together.io.bookmarks.eventListener;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class EmailCreationEvenListener implements ApplicationListener<UserRegistrationEvent> {

    @Override
    public void onApplicationEvent(UserRegistrationEvent userRegistrationEvent) {
        log.info(userRegistrationEvent.getUser().getGmail());
    }
}
