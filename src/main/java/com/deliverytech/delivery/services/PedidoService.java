package com.deliverytech.delivery.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deliverytech.delivery.entities.ItemPedido;
import com.deliverytech.delivery.entities.Pedido;
import com.deliverytech.delivery.entities.Produto;
import com.deliverytech.delivery.repositories.ItemPedidoRepository;
import com.deliverytech.delivery.repositories.PedidoRepository;
import com.deliverytech.delivery.repositories.ProdutoRepository;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    public Pedido create(Pedido pedido) {
        if (pedido.getNumeroPedido() == null || pedido.getNumeroPedido().isBlank()) {
            pedido.setNumeroPedido(gerarNumeroPedido());
        }

        if (pedido.getDataPedido() == null) {
            pedido.setDataPedido(LocalDateTime.now());
        }

        if (pedido.getItensPedido() != null) {
            for (ItemPedido item : pedido.getItensPedido()) {
                Produto produto = produtoRepository.findById(item.getProduto().getId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado com id: " + item.getProduto().getId()));

                item.setProduto(produto);
                item.setPedido(pedido);
                item.setPrecoUnitario(produto.getPreco());
                item.setSubtotal(produto.getPreco() * item.getQuantidade());
            }
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

    public List<Pedido> getByDate(LocalDateTime dataInicio, LocalDateTime dataFim) {
        return repository.findByDataPedidoBetween(dataInicio, dataFim);
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

    public Pedido updateItem(Long pedidoId, ItemPedido updatedItem) {
        Pedido pedido = repository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com id: " + pedidoId));

        ItemPedido itemExistente = itemPedidoRepository.findById(updatedItem.getId())
                .orElseThrow(() -> new RuntimeException("Item não encontrado com id: " + updatedItem.getId()));

        Produto produto = produtoRepository.findById(updatedItem.getProduto().getId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com id: " + updatedItem.getProduto().getId()));

        itemExistente.setProduto(produto);
        itemExistente.setQuantidade(updatedItem.getQuantidade());
        itemExistente.setPrecoUnitario(produto.getPreco());
        itemExistente.setSubtotal(produto.getPreco() * updatedItem.getQuantidade());

        itemPedidoRepository.save(itemExistente);

        double novoTotal = itemPedidoRepository.findByPedidoId(pedidoId).stream()
                .mapToDouble(ItemPedido::getSubtotal).sum();

        pedido.setValorTotal(novoTotal);
        return repository.save(pedido);
    }

    public void deleteItem(Long itemId) {
        ItemPedido item = itemPedidoRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item não encontrado com id: " + itemId));

        Pedido pedido = item.getPedido();

        itemPedidoRepository.delete(item);

        double novoTotal = itemPedidoRepository.findByPedidoId(pedido.getId()).stream()
                .mapToDouble(ItemPedido::getSubtotal).sum();

        pedido.setValorTotal(novoTotal);
        repository.save(pedido);
    }
}
