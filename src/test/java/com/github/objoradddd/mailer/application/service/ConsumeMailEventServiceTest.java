package com.github.objoradddd.mailer.application.service;

import com.github.objoradddd.mailer.application.port.out.EmailSenderPort;
import com.github.objoradddd.mailer.application.usecase.command.SendEmailCommand;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class ConsumeMailEventServiceTest {

    private EmailSenderPort emailSenderPort;
    private ConsumeMailEventService service;

    @BeforeEach
    void setUp() {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        emailSenderPort = mock(EmailSenderPort.class);
        service = new ConsumeMailEventService(emailSenderPort, validator);
    }

    @Test
    void shouldSendEmailWhenCommandIsValid() {
        SendEmailCommand command = new SendEmailCommand(
                "john@example.com",
                List.of("copy@example.com"),
                List.of(),
                null,
                "Welcome",
                "Hello from mailer",
                false
        );

        service.handle(command);

        verify(emailSenderPort, times(1)).send(any());
    }

    @Test
    void shouldFailWhenRecipientIsInvalid() {
        SendEmailCommand command = new SendEmailCommand(
                "bad-email",
                List.of(),
                List.of(),
                null,
                "Welcome",
                "Hello",
                false
        );

        assertThrows(Exception.class, () -> service.handle(command));
    }
}
