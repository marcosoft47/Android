package com.rachapp.app.service;

import com.rachapp.app.model.Pagamento;
import com.rachapp.app.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;

    @Autowired
    public PagamentoService(PagamentoRepository pagamentoRepository) {
        this.pagamentoRepository = pagamentoRepository;
    }

    public Pagamento createPagamento(Pagamento pagamento) {
        // You could add validation here (e.g., check if user actually owes money)
        return pagamentoRepository.save(pagamento);
    }

    public List<Pagamento> getPagamentosByRacha(Long idRacha) {
        return pagamentoRepository.findByRachaIdRacha(idRacha);
    }

    public Optional<Pagamento> getPagamentoById(Long id) {
        return pagamentoRepository.findById(id);
    }

    public boolean deletePagamento(Long id) {
        if (pagamentoRepository.existsById(id)) {
            pagamentoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}