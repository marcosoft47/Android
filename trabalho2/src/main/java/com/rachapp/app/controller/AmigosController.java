package com.rachapp.app.controller;

import com.rachapp.app.model.Usuario;
import com.rachapp.app.service.AmigosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/amigos")
public class AmigosController {

    private final AmigosService amigosService;

    @Autowired
    public AmigosController(AmigosService amigosService) {
        this.amigosService = amigosService;
    }

    // GET /api/amigos/1 -> Returns list of User objects who are friends with User 1
    @GetMapping("/{idUsuario}")
    public ResponseEntity<List<Usuario>> getFriends(@PathVariable Long idUsuario) {
        List<Usuario> friends = amigosService.listFriendsOfUser(idUsuario);
        return ResponseEntity.ok(friends);
    }

    // POST /api/amigos/1/5 -> User 1 adds User 5 as a friend
    @PostMapping("/{idUsuario}/{idAmigo}")
    public ResponseEntity<String> addFriend(@PathVariable Long idUsuario, @PathVariable Long idAmigo) {
        boolean success = amigosService.addAmigo(idUsuario, idAmigo);
        if (success) {
            return ResponseEntity.ok("Friend added successfully");
        } else {
            return ResponseEntity.badRequest().body("Could not add friend (Users invalid or already friends)");
        }
    }

    // DELETE /api/amigos/1/5 -> User 1 removes User 5
    @DeleteMapping("/{idUsuario}/{idAmigo}")
    public ResponseEntity<String> removeFriend(@PathVariable Long idUsuario, @PathVariable Long idAmigo) {
        boolean success = amigosService.removeAmigo(idUsuario, idAmigo);
        if (success) {
            return ResponseEntity.ok("Friend removed");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}