package com.deliverytech.delivery.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.deliverytech.delivery.entities.ItemPedido;
import com.deliverytech.delivery.entities.Pedido;
import com.deliverytech.delivery.services.PedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @PostMapping
    public ResponseEntity<Pedido> create(@RequestBody Pedido pedido) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(pedido));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Pedido>> listarPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(service.getByCustomer(clienteId));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> atualizarStatus(@PathVariable Long id, @RequestBody String novoStatus) {
        try {
            Pedido atualizado = service.updateStatus(id, novoStatus);
            return ResponseEntity.ok(atualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{pedidoId}/itens")
    public ResponseEntity<?> updateItem(@PathVariable Long pedidoId, @RequestBody ItemPedido item) {
        try {
            Pedido updatedPedido = service.updateItem(pedidoId, item);
            return ResponseEntity.ok(updatedPedido);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/itens/{itemId}")
    public ResponseEntity<?> deleteItem(@PathVariable Long itemId) {
        try {
            service.deleteItem(itemId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
