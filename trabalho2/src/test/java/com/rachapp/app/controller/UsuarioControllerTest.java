package com.rachapp.app.controller;

import com.rachapp.app.model.Usuario;
import com.rachapp.app.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UsuarioService usuarioService;

    // --- LIST ALL ---
    @Test
    public void getAllUsuarios_ShouldReturnList() throws Exception {
        Usuario u1 = new Usuario("Maria Silva", "maria@test.com", "9999-9999");
        u1.setIdUsuario(1L);
        Usuario u2 = new Usuario("Carlos Souza", "carlos@test.com", "8888-8888");
        u2.setIdUsuario(2L);

        given(usuarioService.getAllUsuarios()).willReturn(Arrays.asList(u1, u2));

        mockMvc.perform(get("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nome", is("Maria Silva")));
    }

    // --- CREATE ---
    @Test
    public void createUsuario_ShouldReturnSavedUser() throws Exception {
        Usuario savedUser = new Usuario("Novo User", "new@test.com", "1111-2222");
        savedUser.setIdUsuario(10L);

        given(usuarioService.createUsuario(any(Usuario.class))).willReturn(savedUser);

        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\": \"Novo User\", \"email\": \"new@test.com\", \"telefone\": \"1111-2222\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idUsuario", is(10)))
                .andExpect(jsonPath("$.nome", is("Novo User")));
    }

    // --- GET BY ID ---
    @Test
    public void getUsuarioById_WhenExists_ShouldReturnUser() throws Exception {
        Usuario u = new Usuario("Maria", "maria@test.com", "123");
        u.setIdUsuario(1L);

        given(usuarioService.getUsuarioById(1L)).willReturn(Optional.of(u));

        mockMvc.perform(get("/api/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is("Maria")));
    }

    @Test
    public void getUsuarioById_WhenNotExists_ShouldReturnNotFound() throws Exception {
        given(usuarioService.getUsuarioById(99L)).willReturn(Optional.empty());

        mockMvc.perform(get("/api/usuarios/99"))
                .andExpect(status().isNotFound());
    }

    // --- UPDATE (PUT) ---
    @Test
    public void updateUsuario_WhenExists_ShouldReturnUpdatedUser() throws Exception {
        // Arrange
        Usuario updatedUser = new Usuario("Maria Updated", "maria@new.com", "123");
        updatedUser.setIdUsuario(1L);

        given(usuarioService.updateUsuario(eq(1L), any(Usuario.class)))
                .willReturn(Optional.of(updatedUser));

        // Act & Assert
        mockMvc.perform(put("/api/usuarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\": \"Maria Updated\", \"email\": \"maria@new.com\", \"telefone\": \"123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is("Maria Updated")))
                .andExpect(jsonPath("$.email", is("maria@new.com")));
    }

    @Test
    public void updateUsuario_WhenNotExists_ShouldReturnNotFound() throws Exception {
        // Arrange
        given(usuarioService.updateUsuario(eq(99L), any(Usuario.class)))
                .willReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(put("/api/usuarios/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\": \"Ghost\", \"email\": \"ghost@test.com\"}"))
                .andExpect(status().isNotFound());
    }

    // --- DELETE ---
    @Test
    public void deleteUsuario_WhenExists_ShouldReturnNoContent() throws Exception {
        given(usuarioService.deleteUsuario(1L)).willReturn(true);

        mockMvc.perform(delete("/api/usuarios/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteUsuario_WhenNotExists_ShouldReturnNotFound() throws Exception {
        given(usuarioService.deleteUsuario(99L)).willReturn(false);

        mockMvc.perform(delete("/api/usuarios/99"))
                .andExpect(status().isNotFound());
    }
}