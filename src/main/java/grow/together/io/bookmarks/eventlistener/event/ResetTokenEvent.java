package grow.together.io.bookmarks.eventlistener.event;


import grow.together.io.bookmarks.domain.PasswordResetToken;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ResetTokenEvent extends ApplicationEvent {
    private final PasswordResetToken passwordResetToken;

    public ResetTokenEvent(Object source, PasswordResetToken passwordResetToken) {
        super(source);
        this.passwordResetToken = passwordResetToken;
    }
}