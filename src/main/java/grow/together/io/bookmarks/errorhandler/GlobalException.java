package grow.together.io.bookmarks.errorhandler;

import lombok.Data;

/**
 * Description of the file.
 *
 * @author Vicky Djoulako
 * @date 3/12/21 5:56 PM
 * Contributors :
 **/

@Data
public class GlobalException extends Exception {
    private int code = 500;
    private String message;

    public GlobalException(String message) {
        super(message);
    }

    public GlobalException(String message, int code) {
        this(message);
        this.code = code;
    }
}
