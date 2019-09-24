package br.com.bluesoft.desafio.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Fornecedor fornecedor;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Item> itens;

    public Pedido() {
        itens = new ArrayList<>();
    }

    public Pedido(Long id, Fornecedor fornecedor, List<Item> itens) {
        this.id = id;
        this.fornecedor = fornecedor;
        this.itens = itens;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public List<Item> getItens() {
        return itens;
    }

    public void setItens(List<Item> itens) {
        this.itens = itens;
    }
}
