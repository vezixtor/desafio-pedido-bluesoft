package br.com.bluesoft.desafio.dto;

import java.util.List;

public class FornecedorDTO {

    private String cnpj;

    private List<PrecoDTO> precos;

    private String nome;

    public FornecedorDTO() {
    }

    public FornecedorDTO(String cnpj, List<PrecoDTO> precos, String nome) {
        this.cnpj = cnpj;
        this.precos = precos;
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public List<PrecoDTO> getPrecos() {
        return precos;
    }

    public void setPrecos(List<PrecoDTO> precos) {
        this.precos = precos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
