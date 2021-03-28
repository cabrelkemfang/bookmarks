package grow.together.io.bookmarks.eventlistener;


import grow.together.io.bookmarks.domain.PasswordResetToken;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ResetTokenEvent extends ApplicationEvent {
    private final PasswordResetToken passwordResetToken;

    public ResetTokenEvent(PasswordResetToken passwordResetToken) {
        super(passwordResetToken);
        this.passwordResetToken = passwordResetToken;
    }
}