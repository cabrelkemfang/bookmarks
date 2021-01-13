package grow.together.io.bookmarks.serviceimpl;

import grow.together.io.bookmarks.dtomodel.DataResponse;
import grow.together.io.bookmarks.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailParseException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@Slf4j
public class emailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${bookmarks.admin-email}")
    private String adminEmail;

    @Autowired
    public emailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }


    @Override
    public DataResponse<Void> sendTextEmail(String body, String subject, String sendTo)  {

        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "utf-8");

            message.setContent(body, "text/html");
            helper.setFrom(adminEmail);
            helper.setTo(sendTo);
            helper.setSubject(subject);

            javaMailSender.send(message);

            return new DataResponse<>("Email Sent Successfully", HttpStatus.OK.value());

        } catch (MessagingException e) {
            throw new MailParseException(e);
        }

    }

    @Override
    public DataResponse<Void> sendHtml(String body, String subject, String sendTo) {
//        Response response = sendEmail(sendTo, subject, new Content("text/html", body));

        return new DataResponse<>("Email Sent Successfully", HttpStatus.OK.value());
    }

//    private Response sendEmail(String toM, String subjectM, Content content) throws IOException {
//        Email from = new Email(adminEmail);
//        String subject = subjectM;
//        Email to = new Email(toM);
//        Mail mail = new Mail(from, subject, to, content);
//
//        Request request = new Request();
//        try {
//            request.setMethod(Method.POST);
//            request.setEndpoint("mail/send");
//            request.setBody(mail.build());
//            Response response = this.sendGridClient.api(request);
//            log.info("Status: " + response.getStatusCode() + " Body: "
//                    + response.getBody() + " Headers: " + response.getHeaders());
//            return response;
//        } catch (IOException ex) {
//            log.error("Erroring mail: " + ex.getMessage());
//            throw ex;
//        }
//    }
}
