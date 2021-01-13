package grow.together.io.bookmarks.errorhandler;


import grow.together.io.bookmarks.dtomodel.ErrorValidatorDetail;
import grow.together.io.bookmarks.dtomodel.ValidationError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.mail.MailParseException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.net.SocketTimeoutException;
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

    @ExceptionHandler({SocketTimeoutException.class, MessagingException.class})
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
        errorDetails.setStatus(httpStatus.value());
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
