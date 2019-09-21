package br.com.bluesoft.desafio.service.impl;

import br.com.bluesoft.desafio.dto.NovoPedidoDTO;
import br.com.bluesoft.desafio.service.PedidoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Override
    public List<Object> save(List<NovoPedidoDTO> pedidos) {
        return null;
    }
}
