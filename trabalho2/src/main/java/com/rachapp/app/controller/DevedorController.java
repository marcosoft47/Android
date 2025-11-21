package com.rachapp.app.controller;

import com.rachapp.app.model.Devedor;
import com.rachapp.app.service.DevedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/devedores")
public class DevedorController {

    private final DevedorService devedorService;

    @Autowired
    public DevedorController(DevedorService devedorService) {
        this.devedorService = devedorService;
    }

    @PostMapping
    public ResponseEntity<Devedor> addDevedor(@RequestBody Devedor devedor) {
        return ResponseEntity.ok(devedorService.saveDevedor(devedor));
    }

    @GetMapping("/item/{idItemRacha}")
    public ResponseEntity<List<Devedor>> getDevedoresByItem(@PathVariable Long idItemRacha) {
        return ResponseEntity.ok(devedorService.getDevedoresByItem(idItemRacha));
    }

    @DeleteMapping("/{idItemRacha}/{idUsuario}")
    public ResponseEntity<Void> deleteDevedor(@PathVariable Long idItemRacha, @PathVariable Long idUsuario) {
        if (devedorService.deleteDevedor(idItemRacha, idUsuario)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}