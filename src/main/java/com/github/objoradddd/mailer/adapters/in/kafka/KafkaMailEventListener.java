package com.github.objoradddd.mailer.adapters.in.kafka;

import com.github.objoradddd.mailer.application.port.in.ConsumeMailEventUseCase;
import com.github.objoradddd.mailer.application.usecase.command.SendEmailCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaMailEventListener {

    private static final Logger log = LoggerFactory.getLogger(KafkaMailEventListener.class);

    private final ConsumeMailEventUseCase consumeMailEventUseCase;

    public KafkaMailEventListener(ConsumeMailEventUseCase consumeMailEventUseCase) {
        this.consumeMailEventUseCase = consumeMailEventUseCase;
    }

    @KafkaListener(topics = "#{@mailerKafkaProperties.topic}", groupId = "#{@mailerKafkaProperties.groupId}")
    public void consume(KafkaMailEvent event) {
        if (event == null) {
            throw new IllegalArgumentException("Kafka mail event must not be null");
        }

        log.info("Received mail event for recipient={} subject={}", event.getTo(), event.getSubject());

        SendEmailCommand command = new SendEmailCommand(
                event.getTo(),
                event.getCc(),
                event.getBcc(),
                event.getReplyTo(),
                event.getSubject(),
                event.getBody(),
                event.isHtml()
        );

        consumeMailEventUseCase.handle(command);
    }
}
