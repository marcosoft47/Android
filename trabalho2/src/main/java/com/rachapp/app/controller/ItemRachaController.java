package com.rachapp.app.controller;

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

    // POST /api/itens -> Create new Item
    @PostMapping
    public ResponseEntity<ItemRacha> createItem(@RequestBody ItemRacha item) {
        ItemRacha savedItem = itemRachaService.createItem(item);
        return ResponseEntity.ok(savedItem);
    }

    // GET /api/itens/racha/1 -> Get all items for Racha 1
    @GetMapping("/racha/{idRacha}")
    public ResponseEntity<List<ItemRacha>> getItemsByRacha(@PathVariable Long idRacha) {
        return ResponseEntity.ok(itemRachaService.getItemsByRacha(idRacha));
    }

    // DELETE /api/itens/5 -> Delete Item 5
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        if (itemRachaService.deleteItem(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}