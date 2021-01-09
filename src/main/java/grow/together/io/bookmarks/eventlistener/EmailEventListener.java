package grow.together.io.bookmarks.eventlistener;

import grow.together.io.bookmarks.common.EmailTemplate;
import grow.together.io.bookmarks.domain.User;
import grow.together.io.bookmarks.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailEventListener implements ApplicationListener<UserRegistrationEvent> {

    private final EmailService emailService;

    @Value("${bookmarks.ui_base_url}")
    private String baseUrl;

    @Value("${bookmarks.admin-email}")
    private String systemAdmin;

    @Autowired
    public EmailEventListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public void onApplicationEvent(UserRegistrationEvent userRegistrationEvent) {
        log.info("Account Have been Created");
        User user = userRegistrationEvent.getUser();
        String subject = "New Account Created HAve been Created";
        String title = "New Account Creation";
        String content = "A New  Account Have Been Successfully Created On Bookmarks ." +
                "\n <br> Bellow is the User Information :" +
                "\n <br>&nbsp; &nbsp; -User Name: " + user.getName() +
                "\n <br>&nbsp; &nbsp; -Github Account: " + user.getGithub() +
                "\n <br>&nbsp; &nbsp; -User Email: " + user.getGmail() +
                "\n <br>&nbsp; &nbsp; -User Name: " + user.getName() +
                "\n <br>To Access The Admin Dashboard click on the following : " + baseUrl + "/login";

        String body = EmailTemplate.getEmailTemplate("", content, title);

        this.emailService.sendTextEmail(body, subject, systemAdmin);
    }
}
