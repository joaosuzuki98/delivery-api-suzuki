package com.deliverytech.delivery.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.deliverytech.delivery.entities.Restaurante;
import com.deliverytech.delivery.services.RestauranteService;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteService service;

    @PostMapping
    public ResponseEntity<Restaurante> create(@RequestBody Restaurante restaurante) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(restaurante));
    }

    @GetMapping
    public ResponseEntity<List<Restaurante>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        Optional<Restaurante> restaurante = service.get(id);

        if (restaurante.isPresent()) {
            return ResponseEntity.ok(restaurante.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Restaurante não encontrado");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Restaurante restaurante, @PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.update(restaurante, id));
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

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Restaurante>> getByCategory(@PathVariable String categoria) {
        return ResponseEntity.ok(service.getListByCategory(categoria));
    }
}
