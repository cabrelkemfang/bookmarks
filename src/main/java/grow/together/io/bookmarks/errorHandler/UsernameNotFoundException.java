package grow.together.io.bookmarks.errorHandler;

import javax.naming.AuthenticationException;

public class UsernameNotFoundException extends AuthenticationException {
    public UsernameNotFoundException(String s) {
        super(s);
    }
}
