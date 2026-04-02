package com.github.objoradddd.mailer.application.usecase.command;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record SendEmailCommand(
        @NotBlank @Email String to,
        List<@Email String> cc,
        List<@Email String> bcc,
        @Email String replyTo,
        @NotBlank String subject,
        @NotBlank String body,
        boolean html
) {
    public SendEmailCommand {
        cc = cc == null ? List.of() : List.copyOf(cc);
        bcc = bcc == null ? List.of() : List.copyOf(bcc);
    }
}
