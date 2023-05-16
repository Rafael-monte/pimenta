package com.example.sisave.models.dto;

import com.example.sisave.models.Usuario;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsuarioDto implements DtoHandler<UsuarioDto, Usuario> {

    @JsonProperty("username")
    private String username;
    @JsonProperty("secret")
    private String secret;
    @JsonProperty("email")
    private String email;


    @Override
    public UsuarioDto wrap(Usuario usuario) {
        return UsuarioDto.builder()
                .email(usuario.getEmail())
                .secret(usuario.getSecret())
                .username(usuario.getUsername())
                .build();
    }

    @Override
    public Usuario unwrap() {
        Usuario person = new Usuario();
        person.setEmail(this.getEmail());
        person.setUsername(this.getUsername());
        person.setSecret(null);
        return person;
    }
}
