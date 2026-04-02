package com.github.objoradddd.mailer.application.port.in;

import com.github.objoradddd.mailer.application.usecase.command.SendEmailCommand;

public interface ConsumeMailEventUseCase {
    void handle(SendEmailCommand command);
}
