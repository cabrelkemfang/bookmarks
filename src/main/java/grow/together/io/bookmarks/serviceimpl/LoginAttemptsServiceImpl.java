package grow.together.io.bookmarks.serviceimpl;

import grow.together.io.bookmarks.domain.User;
import grow.together.io.bookmarks.repository.UserRepository;
import grow.together.io.bookmarks.service.LoginAttempsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
public class LoginAttemptsServiceImpl implements LoginAttempsService {

    private final UserRepository userRepository;

    @Autowired
    public LoginAttemptsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void increaseFailedAttempts(User user) {
        int newFailedAttempts = user.getFailedAttempt() + 1;
        this.userRepository.updateFailedAttempts(newFailedAttempts, user.getEmail());
    }

    @Override
    @Transactional
    public void resetFailedAttempts(String email) {
        this.userRepository.updateFailedAttempts(0, email);
    }

    @Override
    @Transactional
    public void lock(User user) {
        user.setAccountNonLocked(false);
        user.setLockTime( LocalDateTime.now());
        this.userRepository.save(user);
    }

    @Override
    @Transactional
    public boolean unlockWhenTimeExpired(User user) {
        user.setLockTime(null);
        user.setFailedAttempt(0);
        user.setAccountNonLocked(true);
        this.userRepository.save(user);
        log.info("Account Onlock");
        return true;
    }
}
