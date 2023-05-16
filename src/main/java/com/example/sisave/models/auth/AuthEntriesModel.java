package com.example.sisave.models.auth;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AuthEntriesModel {
    @JsonProperty("email")
    private String email;
    @JsonProperty("password")
    private String password;
}
