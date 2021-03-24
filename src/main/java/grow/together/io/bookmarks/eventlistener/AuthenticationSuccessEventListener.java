package grow.together.io.bookmarks.eventlistener;

import grow.together.io.bookmarks.repository.UserRepository;
import grow.together.io.bookmarks.service.LoginAttempsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthenticationSuccessEventListener {

    private final LoginAttempsService loginAttempsService;
    private final UserRepository userRepository;

    @Autowired
    public AuthenticationSuccessEventListener(LoginAttempsService loginAttempsService, UserRepository userRepository) {
        this.loginAttempsService = loginAttempsService;
        this.userRepository = userRepository;
    }

    //    @EventListener({AuthenticationSuccessEvent.class})
    public void onAuthenticationSuccess(AuthenticationSuccessEvent authorizedEvent) {
        log.info(authorizedEvent.toString());
        // the logic here
        log.info("-------------------------------Authentication Success Event------------------------------------------------------------------------");
        UserDetails details = (UserDetails) authorizedEvent.getAuthentication().getPrincipal();
        log.info(details.getUsername());

//        User user = this.userRepository.findByEmail(details.getUsername()).orElseThrow(() -> new UsernameNotFoundException("UserName or password Not Correct"));
//        if (user.getFailedAttempt() > 0) {
//            this.loginAttempsService.resetFailedAttempts(details.getUsername());
//        }
    }
}
