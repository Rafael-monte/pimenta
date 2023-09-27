package com.example.sisave.models.dto;

import com.example.sisave.models.UsuarioModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsuarioDto implements DtoHandler<UsuarioDto, UsuarioModel> {

    @JsonProperty("username")
    private String username;
    @JsonProperty("secret")
    private String secret;
    @JsonProperty("email")
    private String email;
    @JsonProperty("birthDate")
    private LocalDate birthDate;


    @Override
    public UsuarioDto wrap(UsuarioModel usuarioModel) {
        return UsuarioDto.builder()
                .email(usuarioModel.getEmail())
                .secret(null)
                .username(usuarioModel.getUsername())
                .birthDate(usuarioModel.getBirthDate())
                .build();
    }

    @Override
    public UsuarioModel unwrap() {
        UsuarioModel person = new UsuarioModel();
        person.setEmail(this.getEmail());
        person.setUsername(this.getUsername());
        person.setSecret(this.getSecret());
        person.setBirthDate(this.getBirthDate());
        return person;
    }
}
