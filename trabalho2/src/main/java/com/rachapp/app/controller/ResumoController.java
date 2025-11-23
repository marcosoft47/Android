package com.rachapp.app.controller;

import com.rachapp.app.dto.ResumoDTO;
import com.rachapp.app.service.ResumoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resumo")
public class ResumoController {

    @Autowired
    private ResumoService resumoService;

    @GetMapping("/{userId}")
    public ResponseEntity<ResumoDTO> getResumo(@PathVariable Long userId) {
        return ResponseEntity.ok(resumoService.gerarResumoFinanceiro(userId));
    }
}