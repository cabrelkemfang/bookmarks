package grow.together.io.bookmarks.service;

import grow.together.io.bookmarks.domain.User;

public interface LoginAttempsService {

    void increaseFailedAttempts(User user);

    void resetFailedAttempts(String email);

    void lock(User user);

    boolean unlockWhenTimeExpired(User user);
}
