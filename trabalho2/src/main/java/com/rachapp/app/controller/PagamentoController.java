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

   @PostMapping
    public ResponseEntity<Void> createPagamento(@RequestBody Pagamento pagamento) {
        pagamentoService.createPagamento(pagamento);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/racha/{idRacha}")
    public ResponseEntity<List<Pagamento>> getPagamentosByRacha(@PathVariable Long idRacha) {
        return ResponseEntity.ok(pagamentoService.getPagamentosByRacha(idRacha));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePagamento(@PathVariable Long id) {
        if (pagamentoService.deletePagamento(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}