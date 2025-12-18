package com.example.inbox_api.service;

import com.example.inbox_api.dto.MessageResponseDTO;
import com.example.inbox_api.entity.Message;
import com.example.inbox_api.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository repository;

    public List<MessageResponseDTO> findAll() {
        return repository.findAll().stream().map(this::map).toList();
    }

    private MessageResponseDTO map(Message message) {
        return new MessageResponseDTO(
                message.getId(),
                message.getSubject(),
                message.getText(),
                message.getCreatedDate()
        );
    }
}
