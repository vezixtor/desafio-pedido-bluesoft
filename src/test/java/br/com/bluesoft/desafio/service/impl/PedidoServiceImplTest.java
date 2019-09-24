package br.com.bluesoft.desafio.service.impl;

import br.com.bluesoft.desafio.dto.FornecedorDTO;
import br.com.bluesoft.desafio.dto.MelhorFornecedorDTO;
import br.com.bluesoft.desafio.dto.NovoPedidoDTO;
import br.com.bluesoft.desafio.dto.PrecoDTO;
import br.com.bluesoft.desafio.model.Fornecedor;
import br.com.bluesoft.desafio.model.Item;
import br.com.bluesoft.desafio.model.Pedido;
import br.com.bluesoft.desafio.model.Produto;
import br.com.bluesoft.desafio.repository.PedidoRepository;
import br.com.bluesoft.desafio.repository.ProdutoRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PedidoServiceImplTest {

    @InjectMocks
    private PedidoServiceImpl service;

    @Mock
    private FornecedorServiceImpl fornecedorService;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private PedidoRepository pedidoRepository;

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

    @Test
    public void cotarFornecedores() {
        String gtin = "7892840222949";
        this.obterFornecedoresMock(gtin);
        int quantidadeEsperada = 10;
        MelhorFornecedorDTO melhorFornecedorDTO = ReflectionTestUtils.invokeMethod(service, "cotarFornecedores", new NovoPedidoDTO(gtin, quantidadeEsperada));

        assertNotNull(melhorFornecedorDTO);

        BigDecimal melhorPreco = new BigDecimal("4.13");
        assertThat(melhorFornecedorDTO.getPreco(), equalTo(melhorPreco));
        assertThat(melhorFornecedorDTO.getQuantidade_minima(), equalTo(quantidadeEsperada));

        String melhorFornecedor = "Fornecedor 3";
        assertThat(melhorFornecedorDTO.getNome(), equalTo(melhorFornecedor));
    }

    private void obterFornecedoresMock(String gtin) {
        String json = "[{\"cnpj\": \"56.918.868/0001-20\", \"precos\": [{\"preco\": 4.5, \"quantidade_minima\": 1}, {\"preco\": 3.1, \"quantidade_minima\": 100}], \"nome\": \"Fornecedor 1\"}, {\"cnpj\": \"37.563.823/0001-35\", \"precos\": [{\"preco\": 4.5, \"quantidade_minima\": 10}, {\"preco\": 4.1, \"quantidade_minima\": 35}], \"nome\": \"Fornecedor 2\"}, {\"cnpj\": \"42.217.933/0001-85\", \"precos\": [{\"preco\": 4.13, \"quantidade_minima\": 10}, {\"preco\": 4.1, \"quantidade_minima\": 22}], \"nome\": \"Fornecedor 3\"}]";
        Object value = null;
        try {
            value = new ObjectMapper().readValue(json, new TypeReference<List<FornecedorDTO>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        doReturn(value).when(fornecedorService).obterFornecedores(gtin);
    }

    @Test
    public void cotarPrecos() {
        List<PrecoDTO> precoDTOS = Arrays.asList(
                new PrecoDTO(BigDecimal.TEN, 1),
                new PrecoDTO(BigDecimal.ONE, 10)
        );
        PrecoDTO preco = ReflectionTestUtils.invokeMethod(service, "cotarPrecos", 1, precoDTOS);
        assertThat(preco.getPreco(), equalTo(BigDecimal.TEN));
        assertThat(preco.getQuantidade_minima(), equalTo(1));

        preco = ReflectionTestUtils.invokeMethod(service, "cotarPrecos", 10, precoDTOS);
        assertThat(preco.getPreco(), equalTo(BigDecimal.ONE));
        assertThat(preco.getQuantidade_minima(), equalTo(10));

        preco = ReflectionTestUtils.invokeMethod(service, "cotarPrecos", 100, precoDTOS);
        assertNull(preco);
    }

    @Test(expected = RuntimeException.class)
    public void validarFornecedorComErro() {
        Produto produto = new Produto("7892840222949", "SALGADINHO FANDANGOS QUEIJO");
        doReturn(produto).when(produtoRepository).findByGtin(produto.getGtin());
        ReflectionTestUtils.invokeMethod(service, "validarFornecedor", new MelhorFornecedorDTO(), produto.getGtin());
    }

    @Test
    public void validarFornecedorSemErro() {
        Produto produto = new Produto("7892840222949", "SALGADINHO FANDANGOS QUEIJO");
        doReturn(produto).when(produtoRepository).findByGtin(produto.getGtin());
        ReflectionTestUtils.invokeMethod(service, "validarFornecedor", new MelhorFornecedorDTO("", "", BigDecimal.TEN, 10), produto.getGtin());
    }
}