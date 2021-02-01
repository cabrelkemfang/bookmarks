package grow.together.io.bookmarks.eventlistener;

import grow.together.io.bookmarks.common.EmailTemplate;
import grow.together.io.bookmarks.domain.User;
import grow.together.io.bookmarks.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FailedAttempsEventListener implements ApplicationListener<FailledAttempsEvent> {
    @Value("${spring.aouth.max-failled-attempts}")
    private int maxFailledAttempts;
    @Value("${spring.aouth.lock-time}")
    public int failledTime;

    private final EmailService emailService;

    @Autowired
    public FailedAttempsEventListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    @Async("bookmarksTaskExecutor")
    public void onApplicationEvent(FailledAttempsEvent failledAttempsEvent) {
        log.info("-------------------------------Failled Attemps Event------------------------------------------------------------------------");

        String subject = "Your Account Have Been Lock";
        User user = failledAttempsEvent.getUser();
        String title = " Account Lock";
        String content = " Your account has been locked due to " + maxFailledAttempts +
                " failed attempts.It will be unlocked after"  + failledTime + " minutes. ";

        String body = EmailTemplate.getEmailTemplate(user.getName(), content, title);

        this.emailService.sendTextEmail(body, subject, user.getEmail());
    }
}
