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
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private Usuario personInput;

    private static final String SAVE_ENDPOINT="/person/save";

    private static final String UPDATE_PASSWORD_ENDPOINT="/person/update-secret";

    private static final String EMPTY_STRING="";
    private ObjectMapper mapper;

    private String token;

    @BeforeEach
    public void setUp() {
        this.personInput = new Usuario();
        this.personInput.setUsername("Rafao");
        this.personInput.setEmail("rafaelmonte47@gg.com");
        this.personInput.setSecret("123456");
        this.token = "qhHHrdaDaXhSyd8AXUCCze9qI6wsEJW5LXivNRMJbEc=";
        this.mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }


    @Test
    @Transactional
    @Rollback
    public void whenCreateNewUserWithEmptyUsername_ReturnsFail() throws Exception {
        this.personInput.setUsername(EMPTY_STRING);
        mockMvc.perform(
                MockMvcRequestBuilders.post(SAVE_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writeValueAsString(new UsuarioDto().wrap(this.personInput)))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Transactional
    @Rollback
    public void whenCreateNewUserWithEmptyEmail_ReturnsFail() throws Exception {
        this.personInput.setEmail(EMPTY_STRING);
        mockMvc.perform(
                MockMvcRequestBuilders.post(SAVE_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writeValueAsString(new UsuarioDto().wrap(this.personInput)))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Transactional
    @Rollback
    public void whenCreateNewUserWithEmptySecret_ReturnsFail() throws Exception {
        this.personInput.setSecret(EMPTY_STRING);
        mockMvc.perform(
                MockMvcRequestBuilders.post(SAVE_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writeValueAsString(new UsuarioDto().wrap(this.personInput)))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    @Transactional
    @Rollback
    public void whenCreateNewUserWithNullUsername_ReturnsFail() throws Exception {
        this.personInput.setUsername(null);
        mockMvc.perform(
                MockMvcRequestBuilders.post(SAVE_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writeValueAsString(new UsuarioDto().wrap(this.personInput)))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Transactional
    @Rollback
    public void whenCreateNewUserWithNullEmail_ReturnsFail() throws Exception {
        this.personInput.setEmail(null);
        mockMvc.perform(
                MockMvcRequestBuilders.post(SAVE_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writeValueAsString(new UsuarioDto().wrap(this.personInput)))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    @Transactional
    @Rollback
    public void whenCreateNewUserWithNullSecret_ReturnsFail() throws Exception {
        this.personInput.setSecret(null);
        mockMvc.perform(
                MockMvcRequestBuilders.post(SAVE_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writeValueAsString(new UsuarioDto().wrap(this.personInput)))
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Transactional
    @Rollback
    public void whenUpdatePerson_ReturnsSuccess() throws Exception {
        this.personInput.setUsername("Rafao2");
        this.mockMvc.perform(
                MockMvcRequestBuilders.put(SAVE_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("u-token", this.token)
                        .content(this.mapper.writeValueAsString(new UsuarioDto().wrap(this.personInput)))
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @Transactional
    @Rollback
    public void whenUpdatePassword_ReturnsDifferentToken() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders.put(UPDATE_PASSWORD_ENDPOINT)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("old-secret", "123456")
                        .param("new-secret", "1234567")
                        .header("u-token", this.token))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    @Transactional
    @Rollback
    public void whenGiveEmptyOldSecret_GetBadRequest() throws Exception {
        this.mockMvc.perform(
                        MockMvcRequestBuilders.put(UPDATE_PASSWORD_ENDPOINT)
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("old-secret", "")
                                .param("new-secret", "1234567")
                                .header("u-token", this.token))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Transactional
    @Rollback
    public void whenGiveEmptyNewSecret_GetBadRequest() throws Exception {
        this.mockMvc.perform(
                        MockMvcRequestBuilders.put(UPDATE_PASSWORD_ENDPOINT)
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("old-secret", "123456")
                                .param("new-secret", "")
                                .header("u-token", this.token))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}
