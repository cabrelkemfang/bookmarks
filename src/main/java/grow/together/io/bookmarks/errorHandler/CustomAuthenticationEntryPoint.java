package grow.together.io.bookmarks.errorHandler;

import grow.together.io.bookmarks.dtoModel.ErrorValidatorDetail;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
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
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

//    private final HttpMessageConverter<String> messageConverter;
//    private final ObjectMapper mapper;
//
//    public CustomAuthenticationEntryPoint(HttpMessageConverter<String> messageConverter, ObjectMapper mapper) {
//        this.messageConverter = messageConverter;
//        this.mapper = mapper;
//    }

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {

//        ErrorValidatorDetail errorDetails = new ErrorValidatorDetail();
//        errorDetails.setStatus(HttpStatus.UNAUTHORIZED.value());
//        errorDetails.setMessage(e.getMessage());
//        errorDetails.setTitle("Resource Not Found");
//
////        ServerHttpResponse outputMessage = new ServletServerHttpResponse(httpServletResponse);
////        outputMessage.setStatusCode(HttpStatus.UNAUTHORIZED);
////
////        messageConverter.write(mapper.writeValueAsString(errorDetails), MediaType.APPLICATION_JSON, outputMessage);
//
//        OutputStream out = httpServletResponse.getOutputStream();
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.writeValue(out, errorDetails);
//        out.flush();

        Map map = new HashMap();
        map.put("errorentry", "401");
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
