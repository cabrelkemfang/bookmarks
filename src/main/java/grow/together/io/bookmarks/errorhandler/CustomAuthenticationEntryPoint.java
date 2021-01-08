package grow.together.io.bookmarks.errorhandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import grow.together.io.bookmarks.dtomodel.ErrorValidatorDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;


@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    public CustomAuthenticationEntryPoint() {

    }

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {

        ErrorValidatorDetail errorDetails = new ErrorValidatorDetail();
        errorDetails.setStatus(HttpStatus.UNAUTHORIZED.value());
        errorDetails.setMessage(e.getMessage());
        errorDetails.setTitle("Resource Not Found");

        OutputStream out = httpServletResponse.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out, errorDetails);
        out.flush();
    }
}
