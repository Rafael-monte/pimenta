package com.example.sisave.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NotesDto {
    @JsonProperty("subject")
    private String subject;

    @JsonProperty("note")
    private String note;
}
