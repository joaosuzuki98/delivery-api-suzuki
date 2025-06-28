package com.deliverytech.delivery.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.deliverytech.delivery.entities.Produto;
import com.deliverytech.delivery.services.ProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @PostMapping
    public ResponseEntity<Produto> create(@RequestBody Produto produto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(produto));
    }

    @GetMapping
    public ResponseEntity<List<Produto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        Optional<Produto> produto = service.get(id);
        if (produto.isPresent()) {
            return ResponseEntity.ok(produto.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Produto produto, @PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.update(produto, id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/restaurante/{restauranteId}")
    public ResponseEntity<List<Produto>> getListByRestaurant(@PathVariable Long restauranteId) {
        return ResponseEntity.ok(service.getByRestaurant(restauranteId));
    }
}
