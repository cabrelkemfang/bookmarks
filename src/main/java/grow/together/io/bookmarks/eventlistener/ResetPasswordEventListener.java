package grow.together.io.bookmarks.eventlistener;

import grow.together.io.bookmarks.common.EmailTemplate;
import grow.together.io.bookmarks.domain.PasswordResetToken;
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
public class ResetPasswordEventListener implements ApplicationListener<ResetTokenEvent> {

    private final EmailService emailService;

    @Value("${bookmarks.ui_base_url}")
    private String baseUrl;

    @Autowired
    public ResetPasswordEventListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    @Async("bookmarksTaskExecutor")
    public void onApplicationEvent(ResetTokenEvent resetTokenEvent) {
        log.info("-------------------------------Reset Password Event------------------------------------------------");

        String subject = "Reset Password";
        PasswordResetToken passwordResetToken = resetTokenEvent.getPasswordResetToken();
        User user = passwordResetToken.getUser();

        String title = "Here's the link to reset your password";
        String link = baseUrl + "/change-password?token=" + resetTokenEvent.getPasswordResetToken().getToken();

        String content = "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + link + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";

        String body = EmailTemplate.getEmailTemplate(user.getName(), content, title);

        this.emailService.sendTextEmail(body, subject, user.getEmail());
    }
}
