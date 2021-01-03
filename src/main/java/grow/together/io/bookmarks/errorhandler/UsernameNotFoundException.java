package grow.together.io.bookmarks.errorhandler;

public class UsernameNotFoundException extends RuntimeException {
    public UsernameNotFoundException(String s) {
        super(s);
    }
}
