package com.example.inbox_api.controller;

import com.example.inbox_api.dto.MessageResponseDTO;
import com.example.inbox_api.service.MessageService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MessageController.class)
public class MessageControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MessageService messageService;

    @Test
    void findAll_shouldReturnAllMessages() throws Exception {
        MessageResponseDTO m1 = new MessageResponseDTO(1L, "S1", "T1", LocalDateTime.now());
        MessageResponseDTO m2 = new MessageResponseDTO(2L, "S2", "T2", LocalDateTime.now());

        Mockito.when(messageService.findAll()).thenReturn(Arrays.asList(m1, m2));

        mockMvc.perform(get("/api/messages"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].subject").value("S1"))
                .andExpect(jsonPath("$[1].subject").value("S2"));
    }
}
