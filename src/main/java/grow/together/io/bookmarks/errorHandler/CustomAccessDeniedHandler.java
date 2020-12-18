package grow.together.io.bookmarks.errorHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import grow.together.io.bookmarks.dtoModel.ErrorValidatorDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

//    private final HttpMessageConverter<String> messageConverter;
//    private final ObjectMapper mapper;

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
//        ErrorValidatorDetail errorDetails = new ErrorValidatorDetail();
//        errorDetails.setStatus(HttpStatus.FORBIDDEN.value());
//        errorDetails.setMessage("Access Denied");
//        errorDetails.setTitle("Access Denied Exeption");
//
//
//        OutputStream out = httpServletResponse.getOutputStream();
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.writeValue(out, errorDetails);
//        out.flush();

        httpServletResponse.setContentType("application/json;charset=UTF-8");
        Map map = new HashMap();
        map.put("errorauth", "400");
        map.put("message", e.getMessage());
        map.put("path", httpServletRequest.getServletPath());
        map.put("timestamp", String.valueOf(new Date().getTime()));
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(httpServletResponse.getOutputStream(), map);
        } catch (Exception ex) {
            throw new ServletException();
        }
    }
}
