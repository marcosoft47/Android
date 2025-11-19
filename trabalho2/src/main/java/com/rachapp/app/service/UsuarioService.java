package com.rachapp.app.service;

import com.rachapp.app.model.Usuario;
import com.rachapp.app.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service // Define que aqui é a camada de lógica
public class UsuarioService {

    private final UsuarioRepository repository;

    // Injeção de dependência do Repository
    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public List<Usuario> buscarTodos() {
        return repository.findAll();
    }

    public Usuario salvarUsuario(Usuario usuario) {
        // --- AQUI ENTRA A LÓGICA DE NEGÓCIO ---
        
        // Exemplo: Validar se o nome está vazio
        if (usuario.get_nome() == null || usuario.get_nome().isEmpty()) {
            throw new RuntimeException("O nome é obrigatório!");
        }

        return repository.save(usuario);
    }
}