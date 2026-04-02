package com.github.objoradddd.mailer.application.service;

import com.github.objoradddd.mailer.application.port.in.ConsumeMailEventUseCase;
import com.github.objoradddd.mailer.application.port.out.EmailSenderPort;
import com.github.objoradddd.mailer.application.usecase.command.SendEmailCommand;
import com.github.objoradddd.mailer.domain.model.EmailMessage;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ConsumeMailEventService implements ConsumeMailEventUseCase {

    private final EmailSenderPort emailSenderPort;
    private final Validator validator;

    public ConsumeMailEventService(EmailSenderPort emailSenderPort, Validator validator) {
        this.emailSenderPort = emailSenderPort;
        this.validator = validator;
    }

    @Override
    public void handle(SendEmailCommand command) {
        validate(command);

        EmailMessage message = new EmailMessage(
                command.to(),
                command.cc(),
                command.bcc(),
                command.replyTo(),
                command.subject(),
                command.body(),
                command.html()
        );

        emailSenderPort.send(message);
    }

    private void validate(SendEmailCommand command) {
        Set<ConstraintViolation<SendEmailCommand>> violations = validator.validate(command);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException("Mail event validation failed", violations);
        }
    }
}
