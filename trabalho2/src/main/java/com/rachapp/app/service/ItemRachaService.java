package com.rachapp.app.service;

import com.rachapp.app.model.ItemRacha;
import com.rachapp.app.repository.ItemRachaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemRachaService {

    private final ItemRachaRepository itemRachaRepository;

    @Autowired
    public ItemRachaService(ItemRachaRepository itemRachaRepository) {
        this.itemRachaRepository = itemRachaRepository;
    }

    // Create Item
    public ItemRacha createItem(ItemRacha item) {
        return itemRachaRepository.save(item);
    }

    // Get Items for a specific Racha
    public List<ItemRacha> getItemsByRacha(Long idRacha) {
        return itemRachaRepository.findByRachaIdRacha(idRacha);
    }

    // Get Single Item
    public Optional<ItemRacha> getItemById(Long id) {
        return itemRachaRepository.findById(id);
    }

    // Delete Item
    public boolean deleteItem(Long id) {
        if (itemRachaRepository.existsById(id)) {
            itemRachaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}