package com.rachapp.app.dto;

import java.math.BigDecimal;
import java.util.List;

public class ResumoDTO {
    private BigDecimal totalA_Receber;
    private BigDecimal totalA_Pagar;
    private List<ResumoItemDTO> listaReceber;
    private List<ResumoItemDTO> listaPagar;

    public ResumoDTO(BigDecimal totalA_Receber, BigDecimal totalA_Pagar, List<ResumoItemDTO> listaReceber, List<ResumoItemDTO> listaPagar) {
        this.totalA_Receber = totalA_Receber;
        this.totalA_Pagar = totalA_Pagar;
        this.listaReceber = listaReceber;
        this.listaPagar = listaPagar;
    }

    // Getters and Setters
    public BigDecimal getTotalA_Receber() { return totalA_Receber; }
    public void setTotalA_Receber(BigDecimal totalA_Receber) { this.totalA_Receber = totalA_Receber; }
    public BigDecimal getTotalA_Pagar() { return totalA_Pagar; }
    public void setTotalA_Pagar(BigDecimal totalA_Pagar) { this.totalA_Pagar = totalA_Pagar; }
    public List<ResumoItemDTO> getListaReceber() { return listaReceber; }
    public void setListaReceber(List<ResumoItemDTO> listaReceber) { this.listaReceber = listaReceber; }
    public List<ResumoItemDTO> getListaPagar() { return listaPagar; }
    public void setListaPagar(List<ResumoItemDTO> listaPagar) { this.listaPagar = listaPagar; }
}