package grow.together.io.bookmarks.eventListener;

import grow.together.io.bookmarks.domain.User;
import grow.together.io.bookmarks.errorHandler.ResourceNotFoundException;
import grow.together.io.bookmarks.errorHandler.UsernameNotFoundException;
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
public class AuthenticationFailledEventListener {

    private final UserRepository userRepository;
    private final LoginAttempsService loginAttempsService;

    @Value("${sprint.max-failled-attempts}")
    private int maxFailledAttempts;

    @Autowired
    public AuthenticationFailledEventListener(UserRepository userRepository, LoginAttempsService loginAttempsService) {
        this.userRepository = userRepository;
        this.loginAttempsService = loginAttempsService;
    }

    @EventListener
    public void authenticationFailed(AuthenticationFailureBadCredentialsEvent event) throws UsernameNotFoundException {

        String email = (String) event.getAuthentication().getPrincipal();

        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("UserName or password Not Correct"));

//        // update the failed login count for the user
//        if (user.getFailedAttempt() >= maxFailledAttempts) {
//            this.loginAttempsService.lock(user);
//            throw new UsernameNotFoundException("Your account has been locked due to 3 failed attempts. It will be unlocked after 24 hours.");
//        } else {
//            this.loginAttempsService.increaseFailedAttempts(user);
//        }
    }



}