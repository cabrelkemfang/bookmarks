package grow.together.io.bookmarks.errorhandler;

public class RetryException extends RuntimeException {

    public RetryException(String message) {
        super(message);
    }
}
