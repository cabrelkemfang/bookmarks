package grow.together.io.bookmarks.eventListener;

import grow.together.io.bookmarks.domain.User;
import grow.together.io.bookmarks.service.LoginAttempsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthenticationSuccessEventListener {

    private final LoginAttempsService loginAttempsService;

    @Autowired
    public AuthenticationSuccessEventListener(LoginAttempsService loginAttempsService) {
        this.loginAttempsService = loginAttempsService;
    }

    @EventListener({AuthenticationSuccessEvent.class, InteractiveAuthenticationSuccessEvent.class})
    public void onAuthenticationSuccess(AuthenticationSuccessEvent event) {

//        WebAuthenticationDetails auth = (WebAuthenticationDetails) event.getAuthentication().getDetails();
////        // Actualizamos la útltima fecha de acceso
//        Authentication authentication = event.getAuthentication();
//        log.info(auth.getRemoteAddress());

//        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("UserName or password Not Correct"));
//        if (user.getFailedAttempt() > 0) {
//            this.loginAttempsService.resetFailedAttempts(email);
//        }
    }
}
