package grow.together.io.bookmarks.config;

import grow.together.io.bookmarks.errorhandler.CustomOauthException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.security.core.AuthenticationException;

@Component
@Slf4j
public class ResponseExceptionTranslator extends DefaultWebResponseExceptionTranslator {

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception exception) {
        log.info(exception.toString());
        if (exception instanceof OAuth2Exception) {
            OAuth2Exception oAuth2Exception = (OAuth2Exception) exception;
            return ResponseEntity
                    .status(oAuth2Exception.getHttpErrorCode())
                    .body(new CustomOauthException(oAuth2Exception.getMessage()));
        } else if (exception instanceof AuthenticationException) {
            AuthenticationException authenticationException = (AuthenticationException) exception;
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new CustomOauthException(authenticationException.getMessage()));
        }
//        else if (exception.getSource() instanceof PreAuthenticatedAuthenticationToken) {
////            AuthenticationException authenticationException = (AuthenticationException) exception;
//            return ResponseEntity
//                    .status(HttpStatus.UNAUTHORIZED)
//                    .body(new CustomOauthException("authenticationException.getMessage()"));
//        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new CustomOauthException(exception.getMessage()));
    }
}
