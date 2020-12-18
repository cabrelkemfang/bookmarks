package grow.together.io.bookmarks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.config.BootstrapMode;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication

@EnableScheduling
@EnableJpaAuditing
@EnableJpaRepositories(bootstrapMode = BootstrapMode.DEFAULT, basePackages = {"grow.together.io.bookmarks.repository"})
@EnableAuthorizationServer
@EnableResourceServer
public class BookmarksApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookmarksApplication.class, args);
    }

}
