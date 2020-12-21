package grow.together.io.bookmarks.eventListener;

import grow.together.io.bookmarks.domain.User;
import grow.together.io.bookmarks.errorHandler.ResourceNotFoundException;
import grow.together.io.bookmarks.repository.UserRepository;
import grow.together.io.bookmarks.service.LoginAttempsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthenticationEventListener {

    private final UserRepository userRepository;
    private final LoginAttempsService loginAttempsService;

    @Value("${sprint.max-failled-attempts}")
    private int maxFailledAttempts;

    @Autowired
    public AuthenticationEventListener(UserRepository userRepository, LoginAttempsService loginAttempsService) {
        this.userRepository = userRepository;
        this.loginAttempsService = loginAttempsService;
    }

    @EventListener
    public void authenticationFailed(AuthenticationFailureBadCredentialsEvent event) {

        String email = (String) event.getAuthentication().getPrincipal();

        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(""));

        // update the failed login count for the user
        if (user.getFailedAttempt() >= maxFailledAttempts) {

        } else {
            this.loginAttempsService.increaseFailedAttempts(user);
        }
    }

}