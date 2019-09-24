package br.com.bluesoft.desafio.dto;

import java.math.BigDecimal;

public class MelhorFornecedorDTO {

    private String cnpj;

    private String nome;

    private BigDecimal preco;

    private Integer quantidade_minima;

    public MelhorFornecedorDTO() {
    }

    public MelhorFornecedorDTO(String cnpj, String nome, BigDecimal preco, Integer quantidade_minima) {
        this.cnpj = cnpj;
        this.nome = nome;
        this.preco = preco;
        this.quantidade_minima = quantidade_minima;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
