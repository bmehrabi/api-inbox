package com.example.inbox_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class MessageResponseDetailsDTO {
    private Long id;
    private String subject;
    private String text;
    private LocalDateTime createdDate;
}
