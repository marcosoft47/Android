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

    // Create
    public Usuario createUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Read All
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    // Read One
    public Optional<Usuario> getUsuarioById(Long id) {
        return usuarioRepository.findById(id);
    }

    // UPDATE
    public Optional<Usuario> updateUsuario(Long id, Usuario usuarioDetails) {
        return usuarioRepository.findById(id).map(existingUser -> {
            existingUser.setNome(usuarioDetails.getNome());
            existingUser.setEmail(usuarioDetails.getEmail());
            existingUser.setTelefone(usuarioDetails.getTelefone());
            return usuarioRepository.save(existingUser);
        });
    }

    // DELETE
    public boolean deleteUsuario(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }
}