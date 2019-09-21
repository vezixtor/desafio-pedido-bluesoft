package br.com.bluesoft.desafio.service;

import br.com.bluesoft.desafio.dto.NovoPedidoDTO;

import java.util.List;

public interface PedidoService {

    List<Object> save(List<NovoPedidoDTO> pedidos);
}
