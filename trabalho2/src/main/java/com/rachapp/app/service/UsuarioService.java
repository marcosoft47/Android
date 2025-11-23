package com.rachapp.app.service;

import com.rachapp.app.model.Usuario;
import com.rachapp.app.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder; // New Import
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder; // Inject the encoder

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario createUsuario(Usuario usuario) {
        if (usuario.getAvatarId() == null) {
            usuario.setAvatarId(1);
        }

        // SECURITY FIX: Hash the password before saving
        String encodedPassword = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(encodedPassword);

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

            // SECURITY FIX: Only hash if the password is new/changed
            if (usuarioDetails.getSenha() != null && !usuarioDetails.getSenha().isEmpty()) {
                String encodedPassword = passwordEncoder.encode(usuarioDetails.getSenha());
                existingUser.setSenha(encodedPassword);
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

    public Usuario validarLogin(String email, String rawPassword) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            // Check if the raw password matches the stored Hash
            if (passwordEncoder.matches(rawPassword, usuario.getSenha())) {
                return usuario;
            }
        }
        return null;
    }

    public List<Usuario> searchUsuarios(String query) {
        return usuarioRepository.findByNomeContainingIgnoreCaseOrEmailContainingIgnoreCase(query, query);
    }
}