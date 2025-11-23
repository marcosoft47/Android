package com.rachapp.app.controller;

import com.rachapp.app.dto.ItemCreationDTO;
import com.rachapp.app.model.ItemRacha;
import com.rachapp.app.service.ItemRachaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/itens")
public class ItemRachaController {

    private final ItemRachaService itemRachaService;

    @Autowired
    public ItemRachaController(ItemRachaService itemRachaService) {
        this.itemRachaService = itemRachaService;
    }

    @PostMapping
    public ResponseEntity<ItemRacha> createItem(@RequestBody ItemCreationDTO dto) {
        ItemRacha savedItem = itemRachaService.adicionarItemComParticipantes(dto);
        return ResponseEntity.ok(savedItem);
    }

    @GetMapping("/racha/{idRacha}")
    public ResponseEntity<List<ItemRacha>> getItemsByRacha(@PathVariable Long idRacha) {
        return ResponseEntity.ok(itemRachaService.getItemsByRacha(idRacha));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        if (itemRachaService.deleteItem(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemRacha> updateItem(@PathVariable Long id, @RequestBody ItemCreationDTO dto) {
        ItemRacha updated = itemRachaService.updateItem(id, dto);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }
}