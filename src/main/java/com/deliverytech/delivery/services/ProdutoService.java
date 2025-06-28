package com.deliverytech.delivery.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deliverytech.delivery.entities.Produto;
import com.deliverytech.delivery.repositories.ProdutoRepository;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository repository;

    public Produto create(Produto produto) {
        return repository.save(produto);
    }

    public Optional<Produto> get(Long id) {
        return repository.findById(id);
    }

    public List<Produto> getAll() {
        return repository.findAll();
    }

    public List<Produto> getAvailable() {
        return repository.findByDisponivelTrue();
    }

    public List<Produto> getByRestaurant(Long restauranteId) {
        return repository.findByRestauranteId(restauranteId);
    }

    public List<Produto> getByCategory(String categoria) {
        return repository.findByCategoria(categoria);
    }

    public Produto update(Produto updated, Long id) {
        Produto existing = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Produto não encontrado com id: " + id));

        if (updated.getNome() != null && !updated.getNome().isBlank()) {
            existing.setNome(updated.getNome());
        }

        if (updated.getDescricao() != null && !updated.getDescricao().isBlank()) {
            existing.setDescricao(updated.getDescricao());
        }

        if (updated.getPreco() != null) {
            existing.setPreco(updated.getPreco());
        }

        if (updated.getCategoria() != null && !updated.getCategoria().isBlank()) {
            existing.setCategoria(updated.getCategoria());
        }

        if (updated.getDisponivel() != null) {
            existing.setDisponivel(updated.getDisponivel());
        }

        if (updated.getRestaurante() != null) {
            existing.setRestaurante(updated.getRestaurante());
        }

        return repository.save(existing);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Produto não encontrado com id: " + id);
        }
        repository.deleteById(id);
    }

    public Produto deactivate(Long id) {
        Produto produto = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Produto não encontrado com id: " + id));
        produto.setDisponivel(false);
        return repository.save(produto);
    }
}
