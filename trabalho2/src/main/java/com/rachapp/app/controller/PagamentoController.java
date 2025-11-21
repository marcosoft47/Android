package com.rachapp.app.controller;

import com.rachapp.app.model.Pagamento;
import com.rachapp.app.service.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagamentos")
public class PagamentoController {

    private final PagamentoService pagamentoService;

    @Autowired
    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    // POST /api/pagamentos
    @PostMapping
    public ResponseEntity<Pagamento> createPagamento(@RequestBody Pagamento pagamento) {
        Pagamento saved = pagamentoService.createPagamento(pagamento);
        return ResponseEntity.ok(saved);
    }

    // GET /api/pagamentos/racha/1
    @GetMapping("/racha/{idRacha}")
    public ResponseEntity<List<Pagamento>> getPagamentosByRacha(@PathVariable Long idRacha) {
        return ResponseEntity.ok(pagamentoService.getPagamentosByRacha(idRacha));
    }

    // DELETE /api/pagamentos/1
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePagamento(@PathVariable Long id) {
        if (pagamentoService.deletePagamento(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}