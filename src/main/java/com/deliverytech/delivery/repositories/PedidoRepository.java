package com.deliverytech.delivery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deliverytech.delivery.entities.Pedido;
import java.util.List;
import java.time.LocalDateTime;


@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByClienteId(Long clienteId);

    List<Pedido> findByStatus(String status);

    List<Pedido> findByDataPedido(LocalDateTime dataPedido);

    List<Pedido> findByRestauranteId(Long restauranteId);
}
