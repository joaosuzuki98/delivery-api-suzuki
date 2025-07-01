package com.deliverytech.delivery.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deliverytech.delivery.entities.ItemPedido;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
    List<ItemPedido> findByPedidoId(Long pedidoId);
}
