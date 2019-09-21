package br.com.bluesoft.desafio.service.impl;

import br.com.bluesoft.desafio.dto.NovoPedidoDTO;
import br.com.bluesoft.desafio.service.PedidoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Override
    public List<Object> save(List<NovoPedidoDTO> pedidos) {
        List<NovoPedidoDTO> pedidosFiltrados = this.filtrarPedidosOrcados(pedidos);
        return null;
    }

    private List<NovoPedidoDTO> filtrarPedidosOrcados(List<NovoPedidoDTO> pedidos) {
        return pedidos.stream().filter(p -> p.getQuantidade() > 0).collect(Collectors.toList());
    }
}
