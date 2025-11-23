package com.rachapp.app.service;

import com.rachapp.app.dto.ResumoDTO;
import com.rachapp.app.dto.ResumoItemDTO;
import com.rachapp.app.model.*;
import com.rachapp.app.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class ResumoService {

    @Autowired private DevedorRepository devedorRepository;
    @Autowired private RachaRepository rachaRepository;
    @Autowired private ItemRachaRepository itemRachaRepository;
    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private PagamentoRepository pagamentoRepository; // NEW Inject

    public ResumoDTO gerarResumoFinanceiro(Long userId) {
        List<ResumoItemDTO> aReceber = new ArrayList<>();
        List<ResumoItemDTO> aPagar = new ArrayList<>();

        // --- 1. Calculate "A Pagar" (What I owe) ---
        List<Devedor> minhasDividas = devedorRepository.findByIdUsuario(userId);

        // Group items by "Racha + Creditor" to check against total payments
        Map<String, List<ResumoItemDTO>> debtGroups = new HashMap<>();
        Map<String, BigDecimal> debtTotals = new HashMap<>();

        for (Devedor divida : minhasDividas) {
            ItemRacha item = itemRachaRepository.findById(divida.getIdItemRacha()).orElse(null);
            if (item == null) continue;

            Racha racha = item.getRacha();
            Usuario creditor = item.getPayer();
            if (creditor == null) creditor = racha.getOwner();
            if (creditor == null || creditor.getIdUsuario().equals(userId)) continue;

            BigDecimal valorItem = item.getPreco();
            BigDecimal meuValor = valorItem.multiply(divida.getPercentual()).divide(new BigDecimal(100));

            String key = racha.getIdRacha() + "-" + creditor.getIdUsuario();

            // Add to temporary list
            ResumoItemDTO dto = new ResumoItemDTO(
                    creditor.getNome(),
                    racha.getNome() + " - " + item.getNome(),
                    meuValor,
                    creditor.getAvatarId(),
                    creditor.getIdUsuario(),
                    racha.getIdRacha()
            );

            debtGroups.computeIfAbsent(key, k -> new ArrayList<>()).add(dto);
            debtTotals.merge(key, meuValor, BigDecimal::add);
        }

        // Filter out paid debts
        for (String key : debtGroups.keySet()) {
            String[] parts = key.split("-");
            Long rachaId = Long.parseLong(parts[0]);
            Long creditorId = Long.parseLong(parts[1]);

            BigDecimal totalDebt = debtTotals.get(key);
            BigDecimal totalPaid = pagamentoRepository.calcularTotalPago(rachaId, userId, creditorId); // Me -> Them

            // If I still owe money (Debt > Paid), add items to list
            // Note: This is a binary "Show/Hide". Partial payments logic is complex,
            // for now we hide ONLY if fully paid.
            if (totalDebt.subtract(totalPaid).compareTo(BigDecimal.ZERO) > 0) {
                aPagar.addAll(debtGroups.get(key));
            }
        }

        // --- 2. Calculate "A Receber" (What others owe ME) ---
        Map<String, List<ResumoItemDTO>> creditGroups = new HashMap<>();
        Map<String, BigDecimal> creditTotals = new HashMap<>();

        List<Racha> meusRachas = rachaRepository.findRachasByParticipante(userId);

        for (Racha racha : meusRachas) {
            for (ItemRacha item : racha.getItens()) {
                Usuario payer = item.getPayer();
                if (payer == null) payer = racha.getOwner();
                if (payer == null || !payer.getIdUsuario().equals(userId)) continue; // Not paid by me

                List<Devedor> devedores = devedorRepository.findByIdItemRacha(item.getIdItemRacha());

                for (Devedor devedor : devedores) {
                    if (devedor.getIdUsuario().equals(userId)) continue;

                    BigDecimal valorItem = item.getPreco();
                    BigDecimal valorDevido = valorItem.multiply(devedor.getPercentual()).divide(new BigDecimal(100));

                    Optional<Usuario> devedorUserOpt = usuarioRepository.findById(devedor.getIdUsuario());
                    if (devedorUserOpt.isPresent()) {
                        Usuario devedorUser = devedorUserOpt.get();

                        String key = racha.getIdRacha() + "-" + devedorUser.getIdUsuario();

                        ResumoItemDTO dto = new ResumoItemDTO(
                                devedorUser.getNome(),
                                racha.getNome() + " - " + item.getNome(),
                                valorDevido,
                                devedorUser.getAvatarId(),
                                devedorUser.getIdUsuario(),
                                racha.getIdRacha()
                        );

                        creditGroups.computeIfAbsent(key, k -> new ArrayList<>()).add(dto);
                        creditTotals.merge(key, valorDevido, BigDecimal::add);
                    }
                }
            }
        }

        // Filter out received payments
        for (String key : creditGroups.keySet()) {
            String[] parts = key.split("-");
            Long rachaId = Long.parseLong(parts[0]);
            Long debtorId = Long.parseLong(parts[1]);

            BigDecimal totalCredit = creditTotals.get(key);
            BigDecimal totalReceived = pagamentoRepository.calcularTotalPago(rachaId, debtorId, userId); // Them -> Me

            if (totalCredit.subtract(totalReceived).compareTo(BigDecimal.ZERO) > 0) {
                aReceber.addAll(creditGroups.get(key));
            }
        }

        BigDecimal totalReceber = aReceber.stream().map(ResumoItemDTO::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalPagar = aPagar.stream().map(ResumoItemDTO::getValor).reduce(BigDecimal.ZERO, BigDecimal::add);

        return new ResumoDTO(totalReceber, totalPagar, aReceber, aPagar);
    }
}