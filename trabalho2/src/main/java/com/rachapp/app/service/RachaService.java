package com.rachapp.app.service;

import com.rachapp.app.dto.BalanceDTO;
import com.rachapp.app.model.*;
import com.rachapp.app.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class RachaService {

    @Autowired private RachaRepository rachaRepository;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private ItemRachaRepository itemRachaRepository;
    @Autowired private DevedorRepository devedorRepository;

    // ... existing create/delete methods ...
    public Racha createRacha(Racha racha) {
        if (racha.getCriadoEm() == null) racha.setCriadoEm(LocalDateTime.now());
        if (racha.getStatus() == null) racha.setStatus(RachaStatus.ABERTO);
        if (racha.getOwnerId() != null) usuarioRepository.findById(racha.getOwnerId()).ifPresent(racha::setOwner);
        return rachaRepository.save(racha);
    }
    public java.util.List<Racha> getAllRachas() { return rachaRepository.findAll(); }
    public java.util.List<Racha> getRachasByUserId(Long userId) { return rachaRepository.findRachasByParticipante(userId); }
    public Optional<Racha> getRachaById(Long id) { return rachaRepository.findById(id); }
    public boolean deleteRacha(Long id) { if(rachaRepository.existsById(id)) { rachaRepository.deleteById(id); return true; } return false; }

    // NEW: Calculate Balances
    public List<BalanceDTO> calculateBalances(Long rachaId) {
        Racha racha = rachaRepository.findById(rachaId).orElseThrow(() -> new RuntimeException("Racha not found"));
        List<ItemRacha> items = itemRachaRepository.findByRachaIdRacha(rachaId);

        // Maps to store totals per user
        Map<Long, BigDecimal> paidMap = new HashMap<>();
        Map<Long, BigDecimal> consumedMap = new HashMap<>();
        Map<Long, List<ItemRacha>> itemsMap = new HashMap<>();
        Map<Long, Usuario> userMap = new HashMap<>();

        // Initialize with Owner (they are always a participant)
        userMap.put(racha.getOwner().getIdUsuario(), racha.getOwner());

        for (ItemRacha item : items) {
            // 1. Credit the Payer
            Usuario payer = item.getPayer();
            if (payer == null) payer = racha.getOwner(); // Fallback

            userMap.putIfAbsent(payer.getIdUsuario(), payer);
            paidMap.merge(payer.getIdUsuario(), item.getPreco(), BigDecimal::add);

            // 2. Debit the Consumers
            List<Devedor> debtors = devedorRepository.findByIdItemRacha(item.getIdItemRacha());
            for (Devedor d : debtors) {
                Long uid = d.getIdUsuario();
                // Fetch user info if not cached (Optimization: could fetch all at once, but this is ok for MVP)
                if (!userMap.containsKey(uid)) {
                    usuarioRepository.findById(uid).ifPresent(u -> userMap.put(uid, u));
                }

                // Calculate share price
                BigDecimal share = item.getPreco().multiply(d.getPercentual()).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);

                consumedMap.merge(uid, share, BigDecimal::add);

                // Add item to their list
                itemsMap.computeIfAbsent(uid, k -> new ArrayList<>()).add(item);
            }
        }

        // 3. Build Result List
        List<BalanceDTO> results = new ArrayList<>();
        for (Long uid : userMap.keySet()) {
            Usuario u = userMap.get(uid);
            BigDecimal paid = paidMap.getOrDefault(uid, BigDecimal.ZERO);
            BigDecimal consumed = consumedMap.getOrDefault(uid, BigDecimal.ZERO);
            List<ItemRacha> myItems = itemsMap.getOrDefault(uid, new ArrayList<>());

            results.add(new BalanceDTO(uid, u.getNome(), u.getAvatarId(), paid, consumed, myItems));
        }

        return results;
    }
}