package com.rachapp.app.service;

import com.rachapp.app.model.Amigos;
import com.rachapp.app.model.AmigosId;
import com.rachapp.app.model.Usuario;
import com.rachapp.app.repository.AmigosRepository;
import com.rachapp.app.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AmigosService {

    private final AmigosRepository amigosRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public AmigosService(AmigosRepository amigosRepository, UsuarioRepository usuarioRepository) {
        this.amigosRepository = amigosRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // Add a Friend
    public boolean addAmigo(Long idUsuario, Long idAmigo) {
        // 1. Verify if both users actually exist
        if (!usuarioRepository.existsById(idUsuario) || !usuarioRepository.existsById(idAmigo)) {
            return false;
        }

        // 2. Avoid self-friending
        if (idUsuario.equals(idAmigo)) {
            return false;
        }

        // 3. Check if already friends
        if (amigosRepository.existsByIdUsuarioAndIdAmigo(idUsuario, idAmigo)) {
            return false;
        }

        // 4. Save the relationship
        Amigos newAmizade = new Amigos(idUsuario, idAmigo);
        amigosRepository.save(newAmizade);
        return true;
    }

    // Remove a Friend
    public boolean removeAmigo(Long idUsuario, Long idAmigo) {
        AmigosId id = new AmigosId(idUsuario, idAmigo);
        if (amigosRepository.existsById(id)) {
            amigosRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Get List of Friends (Returns actual User objects, not just IDs)
    public List<Usuario> listFriendsOfUser(Long idUsuario) {
        // 1. Get all friendship rows for this user
        List<Amigos> amizades = amigosRepository.findByIdUsuario(idUsuario);

        // 2. Extract the IDs of the friends
        List<Long> friendIds = new ArrayList<>();
        for (Amigos a : amizades) {
            friendIds.add(a.getIdAmigo());
        }

        // 3. Fetch the actual User objects for those IDs
        // findAllById is a standard JPA method that takes a list of IDs
        return usuarioRepository.findAllById(friendIds);
    }
}