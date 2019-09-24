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
import br.com.bluesoft.desafio.service.FornecedorService;
import br.com.bluesoft.desafio.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PedidoServiceImpl implements PedidoService {

    private final FornecedorService fornecedorService;
    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;

    @Autowired
    public PedidoServiceImpl(FornecedorService fornecedorService, PedidoRepository pedidoRepository, ProdutoRepository produtoRepository) {
        this.fornecedorService = fornecedorService;
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
    }

    @Override
    public List<Pedido> save(List<NovoPedidoDTO> pedidos) {
        List<NovoPedidoDTO> pedidosFiltrados = this.filtrarPedidosOrcados(pedidos);
        Map<String, Pedido> agrupamentoDePedido = new HashMap<>();

        for (NovoPedidoDTO pedidosFiltrado : pedidosFiltrados) {
            MelhorFornecedorDTO melhorFornecedorDTO = this.cotarFornecedores(pedidosFiltrado);
            this.validarFornecedor(melhorFornecedorDTO, pedidosFiltrado.getGtin());
            agrupamentoDePedido = this.agruparPedidos(agrupamentoDePedido, pedidosFiltrado, melhorFornecedorDTO);
        }
        List<Pedido> pedidosFeito = agrupamentoDePedido.entrySet().stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
        return this.pedidoRepository.save(pedidosFeito);
    }

    private Map<String, Pedido> agruparPedidos(Map<String, Pedido> agrupamentoDePedido, NovoPedidoDTO pedidosFiltrado, MelhorFornecedorDTO melhorFornecedorDTO) {
        Pedido pedido = agrupamentoDePedido.get(melhorFornecedorDTO.getCnpj());
        if (Objects.isNull(pedido)) {
            pedido = gerarPedido(melhorFornecedorDTO, pedidosFiltrado.getGtin());
        } else {
            pedido.getItens().add(this.gerarItem(melhorFornecedorDTO, pedidosFiltrado.getGtin()));
        }
        agrupamentoDePedido.put(melhorFornecedorDTO.getCnpj(), pedido);
        return agrupamentoDePedido;
    }

    private Pedido gerarPedido(MelhorFornecedorDTO melhorFornecedorDTO, String gtin) {
        Pedido pedido = new Pedido();
        pedido.setFornecedor(new Fornecedor(melhorFornecedorDTO.getCnpj(), melhorFornecedorDTO.getNome()));
        pedido.getItens().add(this.gerarItem(melhorFornecedorDTO, gtin));
        return pedido;
    }

    private Item gerarItem(MelhorFornecedorDTO melhorFornecedorDTO, String gtin) {
        BigDecimal preco = melhorFornecedorDTO.getPreco();
        Integer quantidade = melhorFornecedorDTO.getQuantidade_minima();
        Item item = new Item();
        item.setPreco(preco);
        item.setTotal(preco.multiply(BigDecimal.valueOf(quantidade)));
        item.setQuantidade(quantidade);
        item.setProduto(produtoRepository.findByGtin(gtin));
        return item;
    }

    private void validarFornecedor(MelhorFornecedorDTO melhorFornecedorDTO, String gtin) {
        if (melhorFornecedorDTO.getCnpj() == null || melhorFornecedorDTO.getPreco() == null) {
            Produto produto = this.produtoRepository.findByGtin(gtin);
            throw new RuntimeException("Não encontrado fornecedor para o produto " + produto.getNome());
        }
    }

    private MelhorFornecedorDTO cotarFornecedores(NovoPedidoDTO novoPedidoDTO) {
        MelhorFornecedorDTO melhorFornecedorDTO = new MelhorFornecedorDTO();
        List<FornecedorDTO> fornecedores = this.fornecedorService.obterFornecedores(novoPedidoDTO.getGtin());
        for (FornecedorDTO fornecedor : fornecedores) {
            PrecoDTO precoCotado = cotarPrecos(novoPedidoDTO.getQuantidade(), fornecedor.getPrecos());
            if (Objects.nonNull(precoCotado) && isMelhorPreco(melhorFornecedorDTO.getPreco(), precoCotado.getPreco())) {
                melhorFornecedorDTO.setCnpj(fornecedor.getCnpj());
                melhorFornecedorDTO.setNome(fornecedor.getNome());
                melhorFornecedorDTO.setPreco(precoCotado.getPreco());
                melhorFornecedorDTO.setQuantidade_minima(precoCotado.getQuantidade_minima());
            }
        }
        return melhorFornecedorDTO;
    }

    private PrecoDTO cotarPrecos(Integer quantidadePedida, List<PrecoDTO> precos) {
        PrecoDTO melhorPreco = null;
        for (PrecoDTO precoDTO : precos) {
            if (hasQuantidadeMinima(quantidadePedida, precoDTO.getQuantidade_minima())) {
                melhorPreco = precoDTO;
            }
        }
        return melhorPreco;
    }

    private boolean isMelhorPreco(BigDecimal precoAtual, BigDecimal outroPreco) {
        return precoAtual == null || precoAtual.compareTo(outroPreco) > 0;
    }

    private boolean hasQuantidadeMinima(Integer quantidadePedida, Integer quantidadeDoFornecedor) {
        return Objects.equals(quantidadePedida, quantidadeDoFornecedor);
    }

    private List<NovoPedidoDTO> filtrarPedidosOrcados(List<NovoPedidoDTO> pedidos) {
        return pedidos.stream().filter(p -> p.getQuantidade() > 0).collect(Collectors.toList());
    }
}
