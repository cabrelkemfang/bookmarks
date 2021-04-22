package grow.together.io.bookmarks.eventlistener;

import grow.together.io.bookmarks.common.EmailTemplate;
import grow.together.io.bookmarks.domain.User;
import grow.together.io.bookmarks.eventlistener.event.FailedAttemptsEvent;
import grow.together.io.bookmarks.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FailedAttemptsEventListener {
    @Value("${spring.aouth.max-failled-attempts}")
    private int maxFailedAttempts;

    @Value("${spring.aouth.lock-time}")
    public int failedTime;

    private final EmailService emailService;

    @Autowired
    public FailedAttemptsEventListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @Async("bookmarksTaskExecutor")
    @EventListener
    void handleConditionalListener(FailedAttemptsEvent event) {
        log.info("-------------------------------Failed Attempts Event-----------------------------------------");

        String subject = "Your Account Have Been Lock";
        User user = event.getUser();
        String title = " Account Lock";
        String content = " Your account has been locked due to " + maxFailedAttempts +
                " failed attempts.It will be unlocked after" + failedTime + " minutes. ";

        String body = EmailTemplate.getEmailTemplate(user.getName(), content, title);

        this.emailService.sendTextEmail(body, subject, user.getEmail());
    }

}
