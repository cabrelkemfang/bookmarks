package grow.together.io.bookmarks.eventlistener;

import grow.together.io.bookmarks.common.EmailTemplate;
import grow.together.io.bookmarks.domain.User;
import grow.together.io.bookmarks.eventlistener.event.UpdateStatusEvent;
import grow.together.io.bookmarks.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UpdateStatusEventListener {

    @Value("${bookmarks.ui_base_url}")
    private String baseUrl;

    @Value("${bookmarks.admin-email}")
    private String systemAdminEmail;

    private final EmailService emailService;

    @Autowired
    public UpdateStatusEventListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @Async("bookmarksTaskExecutor")
    @EventListener
    void handleConditionalListener(UpdateStatusEvent event) {

        User user = event.getUser();

        if (!user.getEmail().equals(systemAdminEmail)) {
            log.info("-------------------------------User Status Event------------------------------------------");

            String subject = "Your Account Created Have been Activated";

            String title = " Account Activation";
            String content = "You Account Have Been Successfully Activated ." +
                    "\nTo Access The Dashboard click on the following link : <a href=\"" + baseUrl + "\">Login</a>";

            String body = EmailTemplate.getEmailTemplate(user.getName(), content, title);

            this.emailService.sendTextEmail(body, subject, user.getEmail());
        }
    }
}
