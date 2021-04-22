package grow.together.io.bookmarks.eventlistener;

import grow.together.io.bookmarks.common.EmailTemplate;
import grow.together.io.bookmarks.domain.User;
import grow.together.io.bookmarks.eventlistener.event.UserCreationEvent;
import grow.together.io.bookmarks.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserCreationEvenListener {
    @Value("${bookmarks.admin-email}")
    private String systemAdminEmail;

    private final EmailService emailService;

    @Autowired
    public UserCreationEvenListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @Async("bookmarksTaskExecutor")
    @EventListener
    void handleConditionalListener(UserCreationEvent event) {
        User user = event.getUser();

        if (!user.getEmail().equals(systemAdminEmail)) {
            log.info("-------------------------------User Registration Event--------------------------------------------");

            String subject = "You Account Have Been Created On Bookmarks";
            String title = "Account Creation";
            String content = "You Account Have Been Successfully Created On Bookmarks." +
                    "\n <br>You Will be notify when you account will be activated ." +
                    "\n <br>Thanks For Confidence And Welcome to Grow Together Family";

            String body = EmailTemplate.getEmailTemplate(user.getName(), content, title);

            this.emailService.sendTextEmail(body, subject, user.getEmail());
        }

    }
}
