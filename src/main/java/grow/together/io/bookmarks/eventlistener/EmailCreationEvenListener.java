package grow.together.io.bookmarks.eventlistener;

import grow.together.io.bookmarks.common.EmailTemplate;
import grow.together.io.bookmarks.domain.User;
import grow.together.io.bookmarks.service.EmailService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailCreationEvenListener implements ApplicationListener<UserRegistrationEvent> {

    private final EmailService emailService;

    @Autowired
    public EmailCreationEvenListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public void onApplicationEvent(UserRegistrationEvent userRegistrationEvent) {
        log.info("Account Have been Created user"+ userRegistrationEvent.getUser().getGmail());
        String subject = "You Account Have Been Created On Bookmarks";
        User user = userRegistrationEvent.getUser();
        String title = "Account Creation";
        String content = "You Account Have Been Successfully Created On Bookmarks." +
                "\n <br>You Will be notify when you account will be activated ." +
                "\n <br>Thanks For Confidence And Welcome to Grow Together Family";

        String body = EmailTemplate.getEmailTemplate(user.getName(), content, title);

        this.emailService.sendTextEmail(body, subject, user.getGmail());
    }
}
