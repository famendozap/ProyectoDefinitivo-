package com.sushi.soporte.controller;

import com.sushi.soporte.model.Ticket;
import com.sushi.soporte.service.TicketService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TicketController.class)
public class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketService service;

    @Test
    void listarTickets() throws Exception {
        when(service.listar()).thenReturn(List.of(new Ticket()));

        mockMvc.perform(get("/tickets/listar"))
                .andExpect(status().isOk());
    }

    @Test
    void agregarTicket() throws Exception {
        String json = """
                {
                  "idUsuario": 12,
                  "asunto": "Consulta general",
                  "descripcion": "Tengo una duda sobre mi pedido",
                  "categoria": "consulta",
                  "estado": "abierto",
                  "prioridad": "media",
                  "fechaApertura": "2026-06-26T09:00:00"
                }
                """;

        when(service.guardar(any(Ticket.class))).thenReturn(new Ticket());

        mockMvc.perform(post("/tickets/agregar")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
    }
}
