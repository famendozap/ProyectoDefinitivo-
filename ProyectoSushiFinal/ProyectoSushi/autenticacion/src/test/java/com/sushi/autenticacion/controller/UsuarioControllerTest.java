package com.sushi.autenticacion.controller;

import com.sushi.autenticacion.dto.UsuarioDTO;
import com.sushi.autenticacion.service.UsuarioService;
import com.sushi.autenticacion.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsuarioController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService service;

    @MockBean
    private JwtUtil jwtUtil;

    @Test
    void listarUsuarios_sinToken_debeRetornar401() throws Exception {

        mockMvc.perform(get("/usuarios/listar"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void listarUsuarios_conTokenValido_debeRetornar200() throws Exception {

        when(jwtUtil.validarToken(anyString())).thenReturn(true);
        when(service.listar()).thenReturn(List.of(new UsuarioDTO()));

        mockMvc.perform(get("/usuarios/listar")
                        .header("Authorization", "Bearer token-de-prueba"))
                .andExpect(status().isOk());
    }
}
