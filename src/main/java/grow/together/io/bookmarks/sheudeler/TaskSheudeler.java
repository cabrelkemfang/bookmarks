package grow.together.io.bookmarks.sheudeler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TaskSheudeler {

    @Scheduled(cron = "${cron-jop}")
    public void scheduleTaskWithCronExpression() {
        log.info("ok it is working");
    }
}

