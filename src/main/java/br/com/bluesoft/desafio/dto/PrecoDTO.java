package br.com.bluesoft.desafio.dto;

import java.math.BigDecimal;

public class PrecoDTO {

    private BigDecimal preco;

    private Integer quantidade_minima;

    public PrecoDTO() {
    }

    public PrecoDTO(BigDecimal preco, Integer quantidade_minima) {
        this.preco = preco;
        this.quantidade_minima = quantidade_minima;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Integer getQuantidade_minima() {
        return quantidade_minima;
    }

    public void setQuantidade_minima(Integer quantidade_minima) {
        this.quantidade_minima = quantidade_minima;
    }
}
