package com.github.objoradddd.mailer.adapters.in.kafka;

import com.github.objoradddd.mailer.application.port.in.ConsumeMailEventUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class KafkaMailEventListenerTest {

    private ConsumeMailEventUseCase useCase;
    private KafkaMailEventListener listener;

    @BeforeEach
    void setUp() {
        useCase = mock(ConsumeMailEventUseCase.class);
        listener = new KafkaMailEventListener(useCase);
    }

    @Test
    void shouldPassEventToUseCase() {
        KafkaMailEvent event = new KafkaMailEvent();
        event.setTo("jane@example.com");
        event.setSubject("Subject");
        event.setBody("Body");

        listener.consume(event);

        verify(useCase, times(1)).handle(any());
    }

    @Test
    void shouldFailWhenEventIsNull() {
        assertThrows(IllegalArgumentException.class, () -> listener.consume(null));
    }
}
