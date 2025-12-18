package com.example.inbox_api.controller;

import com.example.inbox_api.dto.MessageResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
@Tag(name = "Messages", description = "CRUD operations for messages")
public class MessageController {
    @GetMapping
    @Operation(summary = "Get all messages")
    public ResponseEntity<List<MessageResponseDTO>> findAll() {
        MessageResponseDTO dto = new MessageResponseDTO(
                1L, "subject", "text", LocalDateTime.now()
        );

        return ResponseEntity.ok(List.of(dto));
    }
}
