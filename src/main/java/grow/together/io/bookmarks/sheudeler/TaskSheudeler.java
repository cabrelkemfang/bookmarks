package grow.together.io.bookmarks.sheudeler;

import grow.together.io.bookmarks.domain.User;
import grow.together.io.bookmarks.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TaskSheudeler {

    private final UserRepository userRepository;

    @Autowired
    public TaskSheudeler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Scheduled(cron = "${cron-jop}")
    public void scheduleTaskWithCronExpression() {
        log.info("ok it is working");
    }

    public void unlockUser() {
        User user = this.userRepository.
    }
}

