package grow.together.io.bookmarks.eventlistener;

import grow.together.io.bookmarks.common.EmailTemplate;
import grow.together.io.bookmarks.domain.User;
import grow.together.io.bookmarks.service.EmailService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
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
    @Async("bookmarksTaskExecutor")
    public void onApplicationEvent(UserStatusEvent userStatusEvent) {
        log.info("-------------------------------User Status Event------------------------------------------------------------------------");

            String subject = "Your Account Created Have been Activated";
            User user = userStatusEvent.getUser();
            String title = " Account Activation";
            String content = "You Account Have Been Successfully Activated ." +
                    "\nTo Access The Dashboard click on the following : " + baseUrl + "/login";

            String body = EmailTemplate.getEmailTemplate(user.getName(), content, title);

            this.emailService.sendTextEmail(body, subject, user.getEmail());

    }
}
