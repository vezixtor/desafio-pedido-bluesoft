package br.com.bluesoft.desafio.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Produto produto;

    private Integer quantidade;

    private BigDecimal total;

    private BigDecimal preco;

    public Item() {
    }

    public Item(Long id, Produto produto, Integer quantidade, BigDecimal total, BigDecimal preco) {
        this.id = id;
        this.produto = produto;
        this.quantidade = quantidade;
        this.total = total;
        this.preco = preco;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }
}
