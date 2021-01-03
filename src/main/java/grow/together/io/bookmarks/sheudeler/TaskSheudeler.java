package grow.together.io.bookmarks.sheudeler;

import grow.together.io.bookmarks.repository.UserRepository;
import grow.together.io.bookmarks.service.LoginAttempsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Slf4j
@Component
public class TaskSheudeler {

    private final LoginAttempsService loginAttempsService;

    private final UserRepository userRepository;

    @Value("${spring.aouth.max-failled-attempts}")
    public int maxFailledAttempt;

    @Value("${spring.aouth.lock-time}")
    public int failledTime;

    @Autowired
    public TaskSheudeler(LoginAttempsService loginAttempsService, UserRepository userRepository) {
        this.loginAttempsService = loginAttempsService;
        this.userRepository = userRepository;
    }


    public void scheduleTaskWithCronExpression() {
        log.info("ok it is working");
    }

//    @Scheduled(cron = "${cron-jop}")
    public void unlockUser() {
        this.userRepository.getLockUser(failledTime,maxFailledAttempt).stream()
                .map(this.loginAttempsService::unlockWhenTimeExpired)
                .collect(Collectors.toList());
        log.info("Account unlock");
    }
}

