package com.github.objoradddd.mailer.domain.model;

import java.util.List;
import java.util.Objects;

public final class EmailMessage {
    private final String to;
    private final List<String> cc;
    private final List<String> bcc;
    private final String replyTo;
    private final String subject;
    private final String body;
    private final boolean html;

    public EmailMessage(
            String to,
            List<String> cc,
            List<String> bcc,
            String replyTo,
            String subject,
            String body,
            boolean html
    ) {
        this.to = Objects.requireNonNull(to, "to must not be null");
        this.cc = List.copyOf(cc == null ? List.of() : cc);
        this.bcc = List.copyOf(bcc == null ? List.of() : bcc);
        this.replyTo = replyTo;
        this.subject = Objects.requireNonNull(subject, "subject must not be null");
        this.body = Objects.requireNonNull(body, "body must not be null");
        this.html = html;
    }

    public String getTo() {
        return to;
    }

    public List<String> getCc() {
        return cc;
    }

    public List<String> getBcc() {
        return bcc;
    }

    public String getReplyTo() {
        return replyTo;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public boolean isHtml() {
        return html;
    }
}
