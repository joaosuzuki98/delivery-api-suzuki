package com.deliverytech.delivery.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.deliverytech.delivery.entities.Cliente;
import com.deliverytech.delivery.services.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @PostMapping
    public ResponseEntity<Cliente> create(@RequestBody Cliente cliente) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(cliente));
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }
        
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        Optional<Cliente> cliente = service.get(id);

        if (cliente.isPresent()) {
            return ResponseEntity.ok(cliente.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Cliente cliente, @PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.update(cliente, id));
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

    @PatchMapping("/{id}/inativar")
    public ResponseEntity<?> deactivate(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.deactivate(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
