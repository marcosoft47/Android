package com.rachapp.app.controller;

import com.rachapp.app.model.Usuario;
import com.rachapp.app.service.UsuarioService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController // Diz que essa classe responde a requisições web
@RequestMapping("/usuarios") // Define o endereço base: site.com/usuarios
public class UsuarioController {

    // Injeta o service para podermos usar o banco
    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    // Quando o Android fizer um GET em /usuarios, roda isso:
    // @GetMapping
    // public List<Usuario> listarTodos() {
    //     // return service.findAll(); // Retorna todos os usuários em formato JSON
    // }

    // // Quando o Android fizer um POST (enviar dados), roda isso:
    // @PostMapping
    // public Usuario salvarNovo(@RequestBody Usuario novoUsuario) {
    //     // return service.save(novoUsuario);
    // }
}