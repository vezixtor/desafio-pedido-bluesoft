package br.com.bluesoft.desafio.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Produto {

    @Id
    private String gtin;

    private String nome;

    public Produto(String gtin, String nome) {
        this.gtin = gtin;
        this.nome = nome;
    }

    public String getGtin() {
        return gtin;
    }

    public void setGtin(String gtin) {
        this.gtin = gtin;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
