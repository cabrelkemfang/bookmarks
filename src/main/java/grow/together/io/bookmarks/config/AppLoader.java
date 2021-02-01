package grow.together.io.bookmarks.config;

import grow.together.io.bookmarks.domain.User;
import grow.together.io.bookmarks.dtomodel.UserDtaoIn;
import grow.together.io.bookmarks.repository.UserRepository;
import grow.together.io.bookmarks.service.UserService;
import grow.together.io.bookmarks.validator.annotation.UniqueGithub;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import java.util.Optional;

@Component
@Slf4j
public class AppLoader implements CommandLineRunner {
    private final UserService userService;
    private final UserRepository userRepository;

    @Value("${bookmarks.admin-email}")
    private String adminEmail;
    @Value("${bookmarks.password}")
    private String adminPassword;

    @Autowired
    public AppLoader(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Optional<User> user = this.userRepository.findByEmail(adminEmail);
        if (!user.isPresent()) {
            log.info("Create A Default User Of The System");
            this.userService.createAdminUser(new UserDtaoIn(adminEmail, "test", adminPassword, adminPassword, "Admin User"));
            log.info("Activate The Admin User");
            this.userService.updateUserStatus(adminEmail, true);
        }
    }
}