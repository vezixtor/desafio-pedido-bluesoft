package br.com.bluesoft.desafio.dto;

public class NovoPedidoDTO {

    private String gtin;

    private Integer quantidade;

    public NovoPedidoDTO() {
    }

    public NovoPedidoDTO(String gtin, Integer quantidade) {
        this.gtin = gtin;
        this.quantidade = quantidade;
    }

    public String getGtin() {
        return gtin;
    }

    public void setGtin(String gtin) {
        this.gtin = gtin;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}
