package grow.together.io.bookmarks.eventlistener;

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
        String body = "<body style='border:2px solid black'>"
                + "Your onetime password for registration is  "
                + "Please use this OTP to complete your new user registration." +
                "OTP is confidential, do not share this  with anyone.</body>";
        String subject = "You Account Have Been Created On Bookmarks";
        String receiver = userRegistrationEvent.getUser().getGmail();

        this.emailService.sendTextEmail(body, subject, receiver);
    }
}
