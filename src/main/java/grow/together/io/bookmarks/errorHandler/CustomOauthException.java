package grow.together.io.bookmarks.errorHandler;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

@JsonSerialize(using = CustomOauthExceptionSerializer.class)
public class CustomOauthException extends OAuth2Exception {
    private int code;

    public CustomOauthException(String message, Throwable cause) {
        super(message, cause);
        code = 401;
    }

    public CustomOauthException(String message) {
        super(message);
        code = 401;
    }

    public CustomOauthException(String message, int code) {
        this(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
