package com.deliverytech.delivery.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deliverytech.delivery.entities.Restaurante;
import com.deliverytech.delivery.repositories.RestauranteRepository;

@Service
public class RestauranteService {
    @Autowired
    private RestauranteRepository repository;

    public Restaurante create(Restaurante restaurante) {
        return repository.save(restaurante);
    }

    public Optional<Restaurante> get(Long id) {
        return repository.findById(id);
    }

    public List<Restaurante> getAll() {
        return repository.findAll();
    }

    public List<Restaurante> getListAtivos() {
        return repository.findByAtivoTrue();
    }

    public List<Restaurante> getListByCategory(String categoria) {
        return repository.findByCategoria(categoria);
    }

    public List<Restaurante> getByMostRated() {
        return repository.findByAtivoTrueOrderByAvaliacaoDesc();
    }

    public Restaurante update(Restaurante updated, Long id) {
        Restaurante existing = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Restaurante não encontrado com id: " + id));

        if (updated.getNome() != null && !updated.getNome().isBlank()) {
            existing.setNome(updated.getNome());
        }

        if (updated.getCategoria() != null && !updated.getCategoria().isBlank()) {
            existing.setCategoria(updated.getCategoria());
        }

        if (updated.getEndereco() != null && !updated.getEndereco().isBlank()) {
            existing.setEndereco(updated.getEndereco());
        }

        if (updated.getTelefone() != null && !updated.getTelefone().isBlank()) {
            existing.setTelefone(updated.getTelefone());
        }

        if (updated.getTaxaEntrega() != null) {
            existing.setTaxaEntrega(updated.getTaxaEntrega());
        }

        if (updated.getAvaliacao() != null) {
            existing.setAvaliacao(updated.getAvaliacao());
        }

        if (updated.getAtivo() != null) {
            existing.setAtivo(updated.getAtivo());
        }

        return repository.save(existing);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Restaurante não encontrado com id: " + id);
        }
        repository.deleteById(id);
    }

    public Restaurante desativarRestaurante(Long id) {
        Restaurante restaurante = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Restaurante não encontrado com id: " + id));
        restaurante.setAtivo(false);
        return repository.save(restaurante);
    }
}
