package com.rachapp.app.controller;

import com.rachapp.app.model.Usuario;
import com.rachapp.app.service.AmigosService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// New import for Spring Boot 3.4+
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AmigosController.class)
public class AmigosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Changed from @MockBean to @MockitoBean for Spring Boot 3.4+
    @MockitoBean
    private AmigosService amigosService;

    @Test
    public void addFriend_WhenSuccess_ShouldReturnOk() throws Exception {
        // Arrange
        given(amigosService.addAmigo(1L, 2L)).willReturn(true);

        // Act & Assert
        mockMvc.perform(post("/api/amigos/1/2"))
                .andExpect(status().isOk())
                .andExpect(content().string("Friend added successfully"));
    }

    @Test
    public void addFriend_WhenFailure_ShouldReturnBadRequest() throws Exception {
        // Arrange
        given(amigosService.addAmigo(1L, 2L)).willReturn(false);

        // Act & Assert
        mockMvc.perform(post("/api/amigos/1/2"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getFriends_ShouldReturnList() throws Exception {
        // Arrange
        Usuario u1 = new Usuario("Carlos", "carlos@test.com", "123");
        given(amigosService.listFriendsOfUser(1L)).willReturn(Arrays.asList(u1));

        // Act & Assert
        mockMvc.perform(get("/api/amigos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nome", is("Carlos")));
    }
}