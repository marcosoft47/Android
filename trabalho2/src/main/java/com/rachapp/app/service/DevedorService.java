package com.rachapp.app.service;

import com.rachapp.app.model.Devedor;
import com.rachapp.app.model.DevedorId;
import com.rachapp.app.repository.DevedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DevedorService {

    private final DevedorRepository devedorRepository;

    @Autowired
    public DevedorService(DevedorRepository devedorRepository) {
        this.devedorRepository = devedorRepository;
    }

    // Assign a user to an item (Create/Update)
    public Devedor saveDevedor(Devedor devedor) {
        return devedorRepository.save(devedor);
    }

    // Get all debtors for an item
    public List<Devedor> getDevedoresByItem(Long idItemRacha) {
        return devedorRepository.findByIdItemRacha(idItemRacha);
    }

    // Remove a user from an item
    public boolean deleteDevedor(Long idItemRacha, Long idUsuario) {
        DevedorId id = new DevedorId(idItemRacha, idUsuario);
        if (devedorRepository.existsById(id)) {
            devedorRepository.deleteById(id);
            return true;
        }
        return false;
    }
}