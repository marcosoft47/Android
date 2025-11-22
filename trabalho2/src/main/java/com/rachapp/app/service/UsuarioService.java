package com.rachapp.app.service;

import com.rachapp.app.model.Usuario;
import com.rachapp.app.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario createUsuario(Usuario usuario) {
        if (usuario.getAvatarId() == null) {
            usuario.setAvatarId(1);
        }
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> getUsuarioById(Long id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> updateUsuario(Long id, Usuario usuarioDetails) {
        return usuarioRepository.findById(id).map(existingUser -> {
            existingUser.setNome(usuarioDetails.getNome());
            existingUser.setEmail(usuarioDetails.getEmail());
            existingUser.setTelefone(usuarioDetails.getTelefone());

            if (usuarioDetails.getAvatarId() != null) {
                existingUser.setAvatarId(usuarioDetails.getAvatarId());
            }

            if (usuarioDetails.getSenha() != null && !usuarioDetails.getSenha().isEmpty()) {
                existingUser.setSenha(usuarioDetails.getSenha());
            }

            return usuarioRepository.save(existingUser);
        });
    }

    public boolean deleteUsuario(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // NEW: Login Logic
    public Usuario validarLogin(String email, String senha) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);

        // Check if user exists AND password matches
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if (usuario.getSenha().equals(senha)) {
                return usuario;
            }
        }
        return null; // Login failed
    }
}