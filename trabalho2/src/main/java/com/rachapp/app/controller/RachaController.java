package com.rachapp.app.controller;

import com.rachapp.app.dto.BalanceDTO;
import com.rachapp.app.model.Racha;
import com.rachapp.app.model.RachaStatus;
import com.rachapp.app.service.RachaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rachas")
public class RachaController {

    private final RachaService rachaService;

    @Autowired
    public RachaController(RachaService rachaService) {
        this.rachaService = rachaService;
    }

    @PostMapping
    public ResponseEntity<Racha> createRacha(@RequestBody Racha racha) {
        return ResponseEntity.ok(rachaService.createRacha(racha));
    }

    @GetMapping
    public ResponseEntity<List<Racha>> getAllRachas() {
        return ResponseEntity.ok(rachaService.getAllRachas());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Racha>> getRachasByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(rachaService.getRachasByUserId(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Racha> getRachaById(@PathVariable Long id) {
        return rachaService.getRachaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRacha(@PathVariable Long id) {
        if (rachaService.deleteRacha(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}/fechar")
    public ResponseEntity<Racha> fecharRacha(@PathVariable Long id) {
        return rachaService.getRachaById(id).map(racha -> {
            racha.setStatus(RachaStatus.FECHADO);
            return ResponseEntity.ok(rachaService.createRacha(racha)); // Save update
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/balances")
    public ResponseEntity<List<BalanceDTO>> getRachaBalances(@PathVariable Long id) {
        return ResponseEntity.ok(rachaService.calculateBalances(id));
    }
}