package com.rachapp.app.controller;

import com.rachapp.app.model.Racha;
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
}