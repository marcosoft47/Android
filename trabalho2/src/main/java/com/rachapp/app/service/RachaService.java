package com.rachapp.app.service;

import com.rachapp.app.model.Racha;
import com.rachapp.app.repository.RachaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RachaService {

    private final RachaRepository rachaRepository;

    @Autowired
    public RachaService(RachaRepository rachaRepository) {
        this.rachaRepository = rachaRepository;
    }

    public Racha createRacha(Racha racha) {
        if (racha.getTimestamp() == null) {
            racha.setTimestamp(LocalDateTime.now());
        }
        return rachaRepository.save(racha);
    }

    public List<Racha> getAllRachas() {
        return rachaRepository.findAll();
    }

    public Optional<Racha> getRachaById(Long id) {
        return rachaRepository.findById(id);
    }

    public boolean deleteRacha(Long id) {
        if (rachaRepository.existsById(id)) {
            rachaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}