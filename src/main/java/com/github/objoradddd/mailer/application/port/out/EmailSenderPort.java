package com.github.objoradddd.mailer.application.port.out;

import com.github.objoradddd.mailer.domain.model.EmailMessage;

public interface EmailSenderPort {
    void send(EmailMessage message);
}
