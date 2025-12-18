package com.example.inbox_api.controller;

import com.example.inbox_api.dto.MessageRequestDTO;
import com.example.inbox_api.dto.MessageResponseDTO;
import com.example.inbox_api.dto.MessageResponseDetailsDTO;
import com.example.inbox_api.exception.MessageNotFoundException;
import com.example.inbox_api.service.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MessageController.class)
public class MessageControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MessageService messageService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void findAll_shouldReturnAllMessages() throws Exception {
        MessageResponseDTO m1 = new MessageResponseDTO(1L, "S1", LocalDateTime.now());
        MessageResponseDTO m2 = new MessageResponseDTO(2L, "S2", LocalDateTime.now());

        when(messageService.findAll()).thenReturn(Arrays.asList(m1, m2));

        mockMvc.perform(get("/api/messages"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].subject").value("S1"))
                .andExpect(jsonPath("$[1].subject").value("S2"));
    }

    @Test
    void create_shouldReturnOk_whenValidRequest() throws Exception {
        // Given
        MessageRequestDTO request = new MessageRequestDTO();
        request.setSubject("Valid subject");
        request.setText("Some text");

        MessageResponseDTO response = new MessageResponseDTO(1L, "Valid subject", LocalDateTime.now());
        when(messageService.create(any(MessageRequestDTO.class))).thenReturn(response);

        // When & Then
        mockMvc.perform(post("/api/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.subject").value("Valid subject"));
    }

    @Test
    void create_shouldReturnBadRequest_whenSubjectTooLong() throws Exception {
        // Given
        MessageRequestDTO request = new MessageRequestDTO();
        request.setSubject("This subject is definitely longer than forty characters, it will fail validation");
        request.setText("Some text");

        // When & Then
        mockMvc.perform(post("/api/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.subject").value("Subject must be at most 40 characters"));
    }

    @Test
    void details_shouldReturnMessage_whenIdExists() throws Exception {
        // Given
        MessageResponseDetailsDTO response = new MessageResponseDetailsDTO(
                1L,
                "Subject",
                "Text",
                LocalDateTime.now()
        );
        when(messageService.findById(1L)).thenReturn(response);

        // When & Then
        mockMvc.perform(get("/api/messages/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.subject").value("Subject"))
                .andExpect(jsonPath("$.text").value("Text"));
    }

    @Test
    void details_shouldReturnNotFound_whenIdDoesNotExist() throws Exception {
        // Given
        when(messageService.findById(999L)).thenThrow(new MessageNotFoundException(999L));

        // When & Then
        mockMvc.perform(get("/api/messages/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Message with id 999 not found"));
    }
}
