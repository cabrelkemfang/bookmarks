package grow.together.io.bookmarks.eventlistener;

import grow.together.io.bookmarks.domain.User;
import grow.together.io.bookmarks.errorhandler.UsernameNotFoundException;
import grow.together.io.bookmarks.eventlistener.event.FailedAttemptsEvent;
import grow.together.io.bookmarks.repository.UserRepository;
import grow.together.io.bookmarks.service.LoginAttempsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthenticationFailedEventListener {

    private final UserRepository userRepository;
    private final LoginAttempsService loginAttempsService;
    private final ApplicationEventPublisher eventPublisher;

    @Value("${spring.aouth.max-failled-attempts}")
    private int maxFailedAttempts;
    @Value("${spring.aouth.lock-time}")
    public int failedTime;

    @Autowired
    public AuthenticationFailedEventListener(
            UserRepository userRepository,
            LoginAttempsService loginAttempsService,
            ApplicationEventPublisher eventPublisher
    ) {
        this.userRepository = userRepository;
        this.loginAttempsService = loginAttempsService;
        this.eventPublisher = eventPublisher;
    }

    @EventListener(AuthenticationFailureBadCredentialsEvent.class)
    public void authenticationFailed(AuthenticationFailureBadCredentialsEvent event) {

        if (event.getSource() instanceof UsernamePasswordAuthenticationToken) {
            log.info("-------------------------------Authentication FailureBad Credentials Event-------------------------------");

            String email = (String) event.getAuthentication().getPrincipal();

            User user = this.userRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("UserName or password Not Correct"));

            // update the failed login count for the user
            if (user.getFailedAttempt() >= maxFailedAttempts) {
                this.loginAttempsService.lock(user);
                eventPublisher.publishEvent(new FailedAttemptsEvent(this, user));
                throw new UsernameNotFoundException("Your account has been locked due to " + maxFailedAttempts + " failed attempts. It will be unlocked after " + failedTime + " minutes.");
            } else {
                this.loginAttempsService.increaseFailedAttempts(user);
            }
        }
    }
}