package grow.together.io.bookmarks.eventlistener;

import grow.together.io.bookmarks.service.EmailService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserStatusEventListener implements ApplicationListener<UserStatusEvent> {

    @Value("${bookmarks.ui_base_url}")
    private String uiBaseUrl;

    private final EmailService emailService;

    @Autowired
    public UserStatusEventListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public void onApplicationEvent(UserStatusEvent userStatusEvent) {
        if (userStatusEvent.getUser().isActive() && userStatusEvent.getUser().isAccountNonLocked()) {
            //Send a mail to user to inform that the account have been Activated
//            String body = "You Account Have Been Activated";
//            String subject = "You Account Have Been Created On Bookmarks";
//            String receiver = userStatusEvent.getUser().getGmail();
//
//            this.emailService.sendTextEmail(body, subject, receiver);

        } else if (!userStatusEvent.getUser().isAccountNonLocked() && userStatusEvent.getUser().isActive()) {
            //Send a mail to user to inform that the account have been Lock Due To Failed_attempts;
        }
    }
}
