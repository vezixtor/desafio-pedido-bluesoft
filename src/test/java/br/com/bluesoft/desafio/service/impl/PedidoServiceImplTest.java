package br.com.bluesoft.desafio.service.impl;

import br.com.bluesoft.desafio.dto.NovoPedidoDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class PedidoServiceImplTest {

    @InjectMocks
    private PedidoServiceImpl service;

    @Test
    public void filtrarPedidosOrcadosComOrcamento() {
        Integer quantidadeItensOrcados = 3;
        List<NovoPedidoDTO> pedidos = Arrays.asList(
                new NovoPedidoDTO("x", 0),
                new NovoPedidoDTO("y", 10),
                new NovoPedidoDTO("z", 20),
                new NovoPedidoDTO("a", 30)
        );
        List<NovoPedidoDTO> pedidosFiltrados = ReflectionTestUtils.invokeMethod(service, "filtrarPedidosOrcados", pedidos);
        assertThat(quantidadeItensOrcados, equalTo(pedidosFiltrados.size()));
    }

    @Test
    public void filtrarPedidosOrcadosSemOrcamento() {
        Integer quantidadeItensOrcados = 0;
        List<NovoPedidoDTO> pedidos = Arrays.asList(
                new NovoPedidoDTO("x", 0),
                new NovoPedidoDTO("y", 0),
                new NovoPedidoDTO("z", 0),
                new NovoPedidoDTO("a", 0)
        );
        List<NovoPedidoDTO> pedidosFiltrados = ReflectionTestUtils.invokeMethod(service, "filtrarPedidosOrcados", pedidos);
        assertThat(quantidadeItensOrcados, equalTo(pedidosFiltrados.size()));
    }
}