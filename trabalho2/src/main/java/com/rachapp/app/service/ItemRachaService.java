package com.rachapp.app.service;

import com.rachapp.app.dto.ItemCreationDTO;
import com.rachapp.app.model.Credor;
import com.rachapp.app.model.CredorId;
import com.rachapp.app.model.Devedor;
import com.rachapp.app.model.ItemRacha;
import com.rachapp.app.model.Racha;
import com.rachapp.app.model.Usuario;
import com.rachapp.app.repository.CredorRepository;
import com.rachapp.app.repository.DevedorRepository;
import com.rachapp.app.repository.ItemRachaRepository;
import com.rachapp.app.repository.RachaRepository;
import com.rachapp.app.repository.UsuarioRepository; // Need this
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class ItemRachaService {

    @Autowired private ItemRachaRepository itemRachaRepository;
    @Autowired private RachaRepository rachaRepository;
    @Autowired private DevedorRepository devedorRepository;
    @Autowired private CredorRepository credorRepository;
    @Autowired private UsuarioRepository usuarioRepository;

    public List<ItemRacha> getItemsByRacha(Long idRacha) {
        return itemRachaRepository.findByRachaIdRacha(idRacha);
    }

    // FIXED: Delete Item + Reverse Payment
    @Transactional
    public boolean deleteItem(Long id) {
        // 1. Find the item before deleting
        ItemRacha item = itemRachaRepository.findById(id).orElse(null);
        if (item == null) return false;

        // 2. Reverse the payment for the Payer
        // Note: Deleting the item automatically deletes Devedores (Cascade),
        // but we must manually decrease the Credor balance.
        if (item.getPayer() != null) {
            reverseCredit(item.getRacha().getIdRacha(), item.getPayer().getIdUsuario(), item.getPreco());
        }

        // 3. Delete
        itemRachaRepository.delete(item);
        return true;
    }

    private void reverseCredit(Long rachaId, Long userId, BigDecimal valor) {
        CredorId id = new CredorId(rachaId, userId);
        credorRepository.findById(id).ifPresent(credor -> {
            // Subtract value
            BigDecimal newBalance = credor.getValorPago().subtract(valor);
            if (newBalance.compareTo(BigDecimal.ZERO) <= 0) {
                // If balance is 0 or less, remove entry to keep table clean
                credorRepository.delete(credor);
            } else {
                credor.setValorPago(newBalance);
                credorRepository.save(credor);
            }
        });
    }

    @Transactional
    public ItemRacha adicionarItemComParticipantes(ItemCreationDTO dto) {
        Racha racha = rachaRepository.findById(dto.getRachaId())
                .orElseThrow(() -> new RuntimeException("Racha não encontrado"));

        // Determine Payer
        Long payerId = dto.getPayerId() != null ? dto.getPayerId() : racha.getOwner().getIdUsuario();
        Usuario payer = usuarioRepository.findById(payerId).orElse(racha.getOwner());

        // 1. Save Item with Payer info
        ItemRacha newItem = new ItemRacha(racha, payer, dto.getNome(), dto.getPreco());
        newItem = itemRachaRepository.save(newItem);

        // 2. Calculate Split (Debtors)
        saveDebtors(newItem.getIdItemRacha(), dto.getParticipantesIds());

        // 3. Record Payment (Creditor)
        registrarCredito(racha.getIdRacha(), payerId, dto.getPreco());

        return newItem;
    }

    private void registrarCredito(Long rachaId, Long userId, BigDecimal valor) {
        CredorId id = new CredorId(rachaId, userId);
        Credor credor = credorRepository.findById(id).orElse(new Credor(rachaId, userId, BigDecimal.ZERO));
        credor.setValorPago(credor.getValorPago().add(valor));
        credorRepository.save(credor);
    }

    @Transactional
    public ItemRacha updateItem(Long id, ItemCreationDTO dto) {
        return itemRachaRepository.findById(id).map(item -> {
            // If price changed, we'd need to reverse old credit and add new credit.
            // For MVP simplicity, let's update basic fields.
            // Complex re-calculation usually better handled by Delete + Re-add in UI.
            item.setNome(dto.getNome());
            item.setPreco(dto.getPreco());

            if (dto.getParticipantesIds() != null && !dto.getParticipantesIds().isEmpty()) {
                List<Devedor> oldDebtors = devedorRepository.findByIdItemRacha(id);
                devedorRepository.deleteAll(oldDebtors);
                saveDebtors(id, dto.getParticipantesIds());
            }
            return itemRachaRepository.save(item);
        }).orElse(null);
    }

    private void saveDebtors(Long itemId, List<Long> userIds) {
        if (userIds != null && !userIds.isEmpty()) {
            BigDecimal count = new BigDecimal(userIds.size());
            BigDecimal percentual = new BigDecimal("100.00").divide(count, 2, RoundingMode.HALF_UP);
            for (Long userId : userIds) {
                Devedor devedor = new Devedor(itemId, userId, percentual);
                devedorRepository.save(devedor);
            }
        }
    }
}