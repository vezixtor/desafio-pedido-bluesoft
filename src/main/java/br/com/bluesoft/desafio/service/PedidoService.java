package br.com.bluesoft.desafio.service;

import br.com.bluesoft.desafio.dto.NovoPedidoDTO;
import br.com.bluesoft.desafio.model.Pedido;

import java.util.List;

public interface PedidoService {

    List<Pedido> save(List<NovoPedidoDTO> pedidos);
}
