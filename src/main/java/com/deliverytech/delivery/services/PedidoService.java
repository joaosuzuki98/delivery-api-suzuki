package com.deliverytech.delivery.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deliverytech.delivery.entities.Pedido;
import com.deliverytech.delivery.repositories.PedidoRepository;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    public Pedido create(Pedido pedido) {
        if (pedido.getNumeroPedido() == null || pedido.getNumeroPedido().isBlank()) {
            pedido.setNumeroPedido(gerarNumeroPedido());
        }

        if (pedido.getDataPedido() == null) {
            pedido.setDataPedido(LocalDateTime.now());
        }

        return repository.save(pedido);
    }

    public Optional<Pedido> get(Long id) {
        return repository.findById(id);
    }

    public List<Pedido> getAll() {
        return repository.findAll();
    }

    public List<Pedido> getByCustomer(Long clienteId) {
        return repository.findByClienteId(clienteId);
    }

    public List<Pedido> getByRestaurant(Long restauranteId) {
        return repository.findByRestauranteId(restauranteId);
    }

    public List<Pedido> getByStatus(String status) {
        return repository.findByStatus(status);
    }

    public List<Pedido> getByDate(LocalDateTime data) {
        return repository.findByDataPedido(data);
    }

    public Pedido update(Pedido updated, Long id) {
        Pedido existing = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pedido não encontrado com id: " + id));

        if (updated.getNumeroPedido() != null && !updated.getNumeroPedido().isBlank()) {
            existing.setNumeroPedido(updated.getNumeroPedido());
        }

        if (updated.getDataPedido() != null) {
            existing.setDataPedido(updated.getDataPedido());
        }

        if (updated.getStatus() != null && !updated.getStatus().isBlank()) {
            existing.setStatus(updated.getStatus());
        }

        if (updated.getValorTotal() != null) {
            existing.setValorTotal(updated.getValorTotal());
        }

        if (updated.getObservacoes() != null && !updated.getObservacoes().isBlank()) {
            existing.setObservacoes(updated.getObservacoes());
        }

        if (updated.getCliente() != null) {
            existing.setCliente(updated.getCliente());
        }

        if (updated.getRestaurante() != null) {
            existing.setRestaurante(updated.getRestaurante());
        }

        return repository.save(existing);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Pedido não encontrado com id: " + id);
        }
        repository.deleteById(id);
    }
    
    public Pedido updateStatus(Long id, String newStatus) {
        Pedido pedido = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pedido não encontrado com id: " + id));

        if (newStatus == null || newStatus.isBlank()) {
            throw new IllegalArgumentException("Status não pode ser vazio");
        }

        pedido.setStatus(newStatus);
        return repository.save(pedido);
    }

    private String gerarNumeroPedido() {
        return "PD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
