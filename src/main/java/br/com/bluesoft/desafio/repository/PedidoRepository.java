package br.com.bluesoft.desafio.repository;

import br.com.bluesoft.desafio.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
