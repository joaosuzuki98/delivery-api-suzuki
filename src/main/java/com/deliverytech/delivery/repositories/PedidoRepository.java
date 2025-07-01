package com.deliverytech.delivery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.deliverytech.delivery.entities.Pedido;
import java.util.List;
import java.time.LocalDateTime;


@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByClienteId(Long clienteId);

    List<Pedido> findByStatus(String status);

    List<Pedido> findByDataPedidoBetween(LocalDateTime inicio, LocalDateTime fim);

    List<Pedido> findByRestauranteId(Long restauranteId);

    List<Pedido> findTop10ByOrderByDataPedidoDesc();

    @Query("SELECT p.restaurante.id AS restauranteId, SUM(p.valorTotal) AS totalVendas " +
           "FROM Pedido p GROUP BY p.restaurante.id")
    List<Object[]> findTotalVendasPorRestaurante();

    List<Pedido> findByValorTotalGreaterThan(Double valor);
}
