package com.rachapp.app.controller;

import com.rachapp.app.model.Credor;
import com.rachapp.app.service.CredorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/credores")
public class CredorController {

    private final CredorService credorService;

    @Autowired
    public CredorController(CredorService credorService) {
        this.credorService = credorService;
    }

    @PostMapping
    public ResponseEntity<Credor> addPagamento(@RequestBody Credor credor) {
        return ResponseEntity.ok(credorService.saveCredor(credor));
    }

    @GetMapping("/racha/{idRacha}")
    public ResponseEntity<List<Credor>> getPagamentosByRacha(@PathVariable Long idRacha) {
        return ResponseEntity.ok(credorService.getPagamentosByRacha(idRacha));
    }

    @DeleteMapping("/{idRacha}/{idUsuario}")
    public ResponseEntity<Void> deletePagamento(@PathVariable Long idRacha, @PathVariable Long idUsuario) {
        if (credorService.deleteCredor(idRacha, idUsuario)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}