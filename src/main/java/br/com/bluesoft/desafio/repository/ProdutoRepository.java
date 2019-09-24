package br.com.bluesoft.desafio.repository;

import br.com.bluesoft.desafio.model.Produto;
import org.springframework.data.repository.CrudRepository;

public interface ProdutoRepository extends CrudRepository<Produto, String> {

    Produto findByGtin(String gtin);
}
