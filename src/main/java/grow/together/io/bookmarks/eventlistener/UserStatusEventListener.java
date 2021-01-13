package grow.together.io.bookmarks.eventlistener;

import grow.together.io.bookmarks.common.EmailTemplate;
import grow.together.io.bookmarks.domain.User;
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
    private String baseUrl;

    private final EmailService emailService;

    @Autowired
    public UserStatusEventListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public void onApplicationEvent(UserStatusEvent userStatusEvent) {
        if (userStatusEvent.getUser().isActive() && userStatusEvent.getUser().isAccountNonLocked()) {

            String subject = "New Account Created HAve been Activated";
            User user = userStatusEvent.getUser();
            String title = "New Account Creation";
            String content = "You Account Have Been Successfully Activated ." +
                    "\nTo Access The Dashboard click on the following : " + baseUrl + "/login";

            String body = EmailTemplate.getEmailTemplate(user.getName(), content, title);

            this.emailService.sendTextEmail(body, subject, user.getGmail());

        } else if (!userStatusEvent.getUser().isAccountNonLocked() && userStatusEvent.getUser().isActive()) {
            //Send a mail to user to inform that the account have been Lock Due To Failed_attempts;
        }
    }
}
