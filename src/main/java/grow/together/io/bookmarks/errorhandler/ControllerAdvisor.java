package grow.together.io.bookmarks.errorhandler;


import com.sun.mail.util.MailConnectException;
import grow.together.io.bookmarks.dtomodel.ErrorValidatorDetail;
import grow.together.io.bookmarks.dtomodel.ValidationError;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.ClientAbortException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailSendException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.EOFException;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@Slf4j
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorValidatorDetail> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException,
                                                                                HttpServletRequest httpServletRequest) {
        ErrorValidatorDetail errorDetails = new ErrorValidatorDetail();
        errorDetails.setStatus(HttpStatus.NOT_FOUND.value());
        errorDetails.setMessage(resourceNotFoundException.getMessage());
        errorDetails.setTitle("Resource Not Found");

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorValidatorDetail> handleBadRequestException(BadRequestException badRequestException,
                                                                          HttpServletRequest httpServletRequest) {
        ErrorValidatorDetail errorDetails = new ErrorValidatorDetail();
        errorDetails.setStatus(HttpStatus.BAD_REQUEST.value());
        errorDetails.setMessage(badRequestException.getMessage());
        errorDetails.setTitle("Bad Request");

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            SocketTimeoutException.class,
            ClientAbortException.class,
            MessagingException.class,
            IndexOutOfBoundsException.class,
            EOFException.class,
            MailSendException.class,
            IOException.class,
            MailConnectException.class
    })
    public ResponseEntity<ErrorValidatorDetail> socketTimeoutRequestException(Exception e) {
        ErrorValidatorDetail errorDetails = new ErrorValidatorDetail();
        errorDetails.setStatus(HttpStatus.BAD_REQUEST.value());
        errorDetails.setMessage(e.getMessage());
        errorDetails.setTitle("Time Out");

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<ErrorValidatorDetail> handleEmailError(Exception e) {
        ErrorValidatorDetail errorDetails = new ErrorValidatorDetail();
        errorDetails.setStatus(HttpStatus.UNAUTHORIZED.value());
        errorDetails.setMessage("Smtp Username and Password not accepted");
        errorDetails.setTitle("UNAUTHORIZED");

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({SQLException.class})
    public ResponseEntity<ErrorValidatorDetail> sqlHandlerException(SQLException e) {
        ErrorValidatorDetail errorDetails = new ErrorValidatorDetail();
        errorDetails.setStatus(HttpStatus.BAD_REQUEST.value());
        errorDetails.setMessage(e.getMessage());
        errorDetails.setTitle("Sql Error");

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(CustomOauthException.class)
    public ResponseEntity<ErrorValidatorDetail> oauthException(CustomOauthException badRequestException,
                                                               HttpServletRequest httpServletRequest) {
        ErrorValidatorDetail errorDetails = new ErrorValidatorDetail();
        errorDetails.setStatus(HttpStatus.UNAUTHORIZED.value());
        errorDetails.setMessage(badRequestException.getMessage());
        errorDetails.setTitle("Un Authorize");

        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorValidatorDetail> UsernameNotFoundExceptionException(UsernameNotFoundException badRequestException,
                                                                                   HttpServletRequest httpServletRequest) {
        ErrorValidatorDetail errorDetails = new ErrorValidatorDetail();
        errorDetails.setStatus(HttpStatus.UNAUTHORIZED.value());
        errorDetails.setMessage(badRequestException.getMessage());

        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ResourceExist.class)
    public ResponseEntity<ErrorValidatorDetail> handleResourceExistException(ResourceExist resourceExist,
                                                                             HttpServletRequest httpServletRequest) {
        ErrorValidatorDetail errorDetails = new ErrorValidatorDetail();
        errorDetails.setStatus(HttpStatus.BAD_REQUEST.value());
        errorDetails.setMessage(resourceExist.getMessage());
        errorDetails.setTitle("Resource Already Exist");

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RetryException.class)
    public ResponseEntity<ErrorValidatorDetail> handleRetryException(RetryException retryException) {
        ErrorValidatorDetail errorDetails = new ErrorValidatorDetail();
        errorDetails.setStatus(HttpStatus.BAD_REQUEST.value());
        errorDetails.setMessage(retryException.getMessage());
        errorDetails.setTitle("Retry Exception");

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnknownHostException.class)
    public ResponseEntity<ErrorValidatorDetail> handleRetryException(UnknownHostException e) {
        ErrorValidatorDetail errorDetails = new ErrorValidatorDetail();
        errorDetails.setStatus(HttpStatus.BAD_REQUEST.value());
        errorDetails.setMessage("Meta Data Unavailable please Retry Again");
        errorDetails.setTitle("Retry Exception");

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DeleteNotAllowExeption.class)
    public ResponseEntity<ErrorValidatorDetail> handleDeleNotException(DeleteNotAllowExeption deleteNotAllowExeption,
                                                                       HttpServletRequest httpServletRequest) {
        ErrorValidatorDetail errorDetails = new ErrorValidatorDetail();
        errorDetails.setStatus(HttpStatus.BAD_REQUEST.value());
        errorDetails.setMessage(deleteNotAllowExeption.getMessage());
        errorDetails.setTitle("Bad Operation");

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException messageNotReadableException,
                                                                  HttpHeaders httpHeaders,
                                                                  HttpStatus httpStatus,
                                                                  WebRequest webRequest) {

        ErrorValidatorDetail errorDetails = new ErrorValidatorDetail();
//        errorDetails.setStatus(httpStatus.value());
        errorDetails.setTitle("Message Not Readable");
        errorDetails.setMessage(messageNotReadableException.getMessage());

        return handleExceptionInternal(messageNotReadableException, errorDetails, httpHeaders, httpStatus, webRequest);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException notValidException,
                                                               HttpHeaders headers,
                                                               HttpStatus status,
                                                               WebRequest request) {

        ErrorValidatorDetail errorValidatorDetail = new ErrorValidatorDetail();

        errorValidatorDetail.setStatus(HttpStatus.BAD_REQUEST.value());
        errorValidatorDetail.setTitle("Validation Failled");
        errorValidatorDetail.setMessage("Input Validation");

        List<FieldError> fieldErrorList = notValidException.getBindingResult().getFieldErrors();

        for (FieldError fieldError : fieldErrorList) {
            List<ValidationError> validationErrorList = errorValidatorDetail.getErrors().get(fieldError.getField());
            if (validationErrorList == null) {
                validationErrorList = new ArrayList<>();
                errorValidatorDetail.getErrors().put(fieldError.getField(), validationErrorList);
            }
            ValidationError validationError = new ValidationError();
            validationError.setCode(fieldError.getCode());
            validationError.setMessage(fieldError.getDefaultMessage());
            validationErrorList.add(validationError);
        }

        return handleExceptionInternal(notValidException, errorValidatorDetail, headers, status, request);
    }
}
