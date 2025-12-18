package com.example.inbox_api.service;

import com.example.inbox_api.dto.MessageResponseDTO;
import com.example.inbox_api.entity.Message;
import com.example.inbox_api.repository.MessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MessageServiceTest {
    private MessageRepository repository;
    private MessageService service;

    @BeforeEach
    void setUp() {
        repository = mock(MessageRepository.class);
        service = new MessageService(repository);
    }

    @Test
    void findAll_shouldReturnAllMessages() {
        Message m1 = new Message(1L, "S1", "T1", LocalDateTime.now(), false);
        Message m2 = new Message(2L, "S2", "T2", LocalDateTime.now(), false);
        when(repository.findAll()).thenReturn(Arrays.asList(m1, m2));

        List<MessageResponseDTO> messages = service.findAll();

        assertThat(messages).hasSize(2);
        assertThat(messages.get(0).getSubject()).isEqualTo("S1");
        assertThat(messages.get(1).getSubject()).isEqualTo("S2");
    }

}
