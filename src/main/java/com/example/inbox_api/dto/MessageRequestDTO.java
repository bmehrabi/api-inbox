package com.example.inbox_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageRequestDTO {
    @NotBlank
    @Size(max = 40, message = "Subject must be at most 40 characters")
    private String subject;

    @NotBlank
    private String text;
}
