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
public class EmailEventListener {

    private final EmailService emailService;

    @Value("${bookmarks.ui_base_url}")
    private String baseUrl;

    @Value("${bookmarks.admin-email}")
    private String systemAdminEmail;

    @Autowired
    public EmailEventListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @Async("bookmarksTaskExecutor")
    @EventListener
    void handleConditionalListener(UserCreationEvent event) {
        User user = event.getUser();
        if (!user.getEmail().equals(systemAdminEmail)) {
            String subject = "New Account Have been Created";
            String title = "New Account Creation";
            String content = "A New  Account Have Been Successfully Created ." +
                    "\n <br> Bellow is the User Information :" +
                    "\n <br>&nbsp; &nbsp; -User Name: " + user.getName() +
                    "\n <br>&nbsp; &nbsp; -Github Account: " + user.getGithub() +
                    "\n <br>&nbsp; &nbsp; -User Email: " + user.getEmail() +
                    "\n <br>&nbsp; &nbsp; -User Name: " + user.getName() +
                    "\n <br>To Access The Admin Dashboard click on the following link: <a href=\"" + baseUrl + "/login" + "\">Login</a>";

            String body = EmailTemplate.getEmailTemplate("", content, title);

            this.emailService.sendTextEmail(body, subject, systemAdminEmail);
        }

    }
}
