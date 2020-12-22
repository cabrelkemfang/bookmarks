package grow.together.io.bookmarks.eventListener;

import grow.together.io.bookmarks.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthenticationSuccessEventListener {

//    @EventListener({AuthenticationSuccessEvent.class, InteractiveAuthenticationSuccessEvent.class})
//    public void processAuthenticationSuccessEvent(AbstractAuthenticationEvent e) {
//
//        // Actualizamos la útltima fecha de acceso
////        String username = ((User) e.getAuthentication().getPrincipal()).getGmail();
//        log.info(e.toString());
//
//    }
}
