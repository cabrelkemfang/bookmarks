package grow.together.io.bookmarks.serviceImpl;

import grow.together.io.bookmarks.domain.User;
import grow.together.io.bookmarks.repository.UserRepository;
import grow.together.io.bookmarks.service.LoginAttempsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
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
        this.userRepository.updateFailedAttempts(newFailedAttempts, user.getGmail());
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
        user.setLockTime(new Date());
        this.userRepository.save(user);
    }

    @Override
    public void unlockWhenTimeExpired(User user) {

    }
}
