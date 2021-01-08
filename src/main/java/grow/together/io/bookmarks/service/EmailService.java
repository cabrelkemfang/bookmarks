package grow.together.io.bookmarks.service;

import grow.together.io.bookmarks.dtomodel.DataResponse;

import javax.mail.MessagingException;


public interface EmailService {
    DataResponse<Void> sendTextEmail(String body, String subject, String sendTo);

    DataResponse<Void> sendHtml(String body, String subject, String sendTo);
}
