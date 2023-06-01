package com.example.sisave;

import com.example.sisave.models.dto.UsuarioDto;
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
public class MiddlewareControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static String CONTROLLER_URL="/notes/";

    private Integer selectedIndex;

    private String token;
    private Boolean isMiddlewareCurrenctlyDown = Boolean.FALSE;

    @BeforeEach
    public void setUp() {
        this.selectedIndex = 0;
        this.token = "qhHHrdaDaXhSyd8AXUCCze9qI6wsEJW5LXivNRMJbEc=";
        this.isMiddlewareCurrenctlyDown = Boolean.TRUE;
    }

    @Test
    @Transactional
    @Rollback
    public void whenCallMiddlewareCorrectly_GetsOK() throws Exception {
        if (!this.isMiddlewareCurrenctlyDown) {
            this.mockMvc.perform(
                    MockMvcRequestBuilders.get(CONTROLLER_URL + this.selectedIndex)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("u-token", this.token)
            ).andExpect(MockMvcResultMatchers.status().isOk());
        }
    }

    @Test
    @Transactional
    @Rollback
    public void whenCallMiddlewareWhenItsDown_GetsBadGateway() throws Exception {
        if (this.isMiddlewareCurrenctlyDown) {
            this.mockMvc.perform(
                    MockMvcRequestBuilders.get(CONTROLLER_URL + this.selectedIndex)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("u-token", this.token)
            ).andExpect(MockMvcResultMatchers.status().isBadGateway());
        }
    }



}
