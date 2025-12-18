package com.example.inbox_api.service;

import com.example.inbox_api.dto.MessageRequestDTO;
import com.example.inbox_api.dto.MessageResponseDTO;
import com.example.inbox_api.dto.MessageResponseDetailsDTO;
import com.example.inbox_api.entity.Message;
import com.example.inbox_api.exception.MessageNotFoundException;
import com.example.inbox_api.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository repository;

    public List<MessageResponseDTO> findAll() {
        return repository.findAll().stream().map(this::map).toList();
    }

    public MessageResponseDTO create(MessageRequestDTO dto) {
        Message message = new Message();
        message.setSubject(dto.getSubject());
        message.setText(dto.getText());
        message.setCreatedDate(LocalDateTime.now());
        return map(repository.save(message));
    }

    public MessageResponseDetailsDTO findById(Long id) {
        Message message = repository.findById(id)
                .orElseThrow(() -> new MessageNotFoundException(id));
        return toDetailsDTO(message);
    }

    private MessageResponseDTO map(Message message) {
        return new MessageResponseDTO(
                message.getId(),
                message.getSubject(),
                message.getCreatedDate()
        );
    }

    private MessageResponseDetailsDTO toDetailsDTO(Message message) {
        return new MessageResponseDetailsDTO(
                message.getId(),
                message.getSubject(),
                message.getText(),
                message.getCreatedDate()
        );
    }
}
