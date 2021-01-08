package grow.together.io.bookmarks.eventlistener;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UserStatusEventListener implements ApplicationListener<UserStatusEvent> {

    @Override
    public void onApplicationEvent(UserStatusEvent userStatusEvent) {
        if (userStatusEvent.getUser().isActive() && userStatusEvent.getUser().isAccountNonLocked()) {
            //Send a mail to user to inform that the account have been Activated

        }else if (!userStatusEvent.getUser().isAccountNonLocked() && userStatusEvent.getUser().isActive()){
            //Send a mail to user to inform that the account have been Lock Due To Failed_attempts;
        }
    }
}
