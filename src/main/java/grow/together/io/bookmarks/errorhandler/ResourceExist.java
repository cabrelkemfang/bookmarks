package grow.together.io.bookmarks.errorhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ResourceExist extends Exception {

    private static final long serialVersionUID = 1L;

    public ResourceExist() {
    }

    public ResourceExist(String message) {
        super(message);
    }

    public ResourceExist(String message, Throwable cause) {
        super(message, cause);
    }
}
