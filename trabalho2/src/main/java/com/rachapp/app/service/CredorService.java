package com.rachapp.app.service;

import com.rachapp.app.model.Credor;
import com.rachapp.app.model.CredorId;
import com.rachapp.app.repository.CredorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredorService {

    private final CredorRepository credorRepository;

    @Autowired
    public CredorService(CredorRepository credorRepository) {
        this.credorRepository = credorRepository;
    }

    // Add or Update a Payment
    public Credor saveCredor(Credor credor) {
        return credorRepository.save(credor);
    }

    // Get all payments for a specific Racha
    public List<Credor> getPagamentosByRacha(Long idRacha) {
        return credorRepository.findByIdRacha(idRacha);
    }

    // Remove a payment
    public boolean deleteCredor(Long idRacha, Long idUsuario) {
        CredorId id = new CredorId(idRacha, idUsuario);
        if (credorRepository.existsById(id)) {
            credorRepository.deleteById(id);
            return true;
        }
        return false;
    }
}