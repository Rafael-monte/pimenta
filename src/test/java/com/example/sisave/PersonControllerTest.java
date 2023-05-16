package com.example.sisave;

import com.example.sisave.models.Usuario;
import com.example.sisave.models.dto.UsuarioDto;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private Usuario personInput;

    private static final String SAVE_ENDPOINT="/person/save";

    private static final String EMPTY_STRING="";

    private ObjectMapper mapper;

    @BeforeEach
    public void setUp() {
        this.personInput = new Usuario();
        this.personInput.setUsername("Rafao");
        this.personInput.setEmail("rafaelmonte47@gmail.com");
        this.personInput.setSecret("1234");
        this.mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }


    @Test
    public void whenCreateNewUserWithEmptyUsername_ReturnsFail() throws Exception {
        this.personInput.setUsername(EMPTY_STRING);
        mockMvc.perform(
                MockMvcRequestBuilders.post(SAVE_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writeValueAsString(new UsuarioDto().wrap(this.personInput)))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void whenCreateNewUserWithEmptyEmail_ReturnsFail() throws Exception {
        this.personInput.setEmail(EMPTY_STRING);
        mockMvc.perform(
                MockMvcRequestBuilders.post(SAVE_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writeValueAsString(new UsuarioDto().wrap(this.personInput)))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void whenCreateNewUserWithEmptySecret_ReturnsFail() throws Exception {
        this.personInput.setSecret(EMPTY_STRING);
        mockMvc.perform(
                MockMvcRequestBuilders.post(SAVE_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writeValueAsString(new UsuarioDto().wrap(this.personInput)))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    public void whenCreateNewUserWithNullUsername_ReturnsFail() throws Exception {
        this.personInput.setUsername(null);
        mockMvc.perform(
                MockMvcRequestBuilders.post(SAVE_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writeValueAsString(new UsuarioDto().wrap(this.personInput)))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void whenCreateNewUserWithNullEmail_ReturnsFail() throws Exception {
        this.personInput.setEmail(null);
        mockMvc.perform(
                MockMvcRequestBuilders.post(SAVE_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writeValueAsString(new UsuarioDto().wrap(this.personInput)))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    public void whenCreateNewUserWithNullSecret_ReturnsFail() throws Exception {
        this.personInput.setSecret(null);
        mockMvc.perform(
                MockMvcRequestBuilders.post(SAVE_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writeValueAsString(new UsuarioDto().wrap(this.personInput)))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}
