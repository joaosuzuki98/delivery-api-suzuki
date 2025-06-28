package com.deliverytech.delivery.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deliverytech.delivery.entities.Cliente;
import com.deliverytech.delivery.repositories.ClienteRepository;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository repository;

    public Cliente create(Cliente customer) {
        if (repository.findByEmail(customer.getEmail()).isPresent()) {
            throw new RuntimeException("E-mail já cadastrado.");
        }
        return repository.save(customer);
    }

    public Optional<Cliente> get(Long id) {
        return repository.findById(id);
    }

    public List<Cliente> getAll() {
        return repository.findAll();
    }

    public Cliente update(Cliente updatedCustomer, Long id) {
        Cliente existingCustomer = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Cliente não encontrado com id: " + id));

        if (updatedCustomer.getEmail() != null && !updatedCustomer.getEmail().isBlank()) {
            Optional<Cliente> emailOwner = repository.findByEmail(updatedCustomer.getEmail());
            if (emailOwner.isPresent() && !emailOwner.get().getId().equals(existingCustomer.getId())) {
                throw new RuntimeException("E-mail já está em uso por outro cliente.");
            }
            existingCustomer.setEmail(updatedCustomer.getEmail());
        }

        if (updatedCustomer.getNome() != null && !updatedCustomer.getNome().isBlank()) {
            existingCustomer.setNome(updatedCustomer.getNome());
        }

        if (updatedCustomer.getTelefone() != null && !updatedCustomer.getTelefone().isBlank()) {
            existingCustomer.setTelefone(updatedCustomer.getTelefone());
        }

        if (updatedCustomer.getEndereco() != null && !updatedCustomer.getEndereco().isBlank()) {
            existingCustomer.setEndereco(updatedCustomer.getEndereco());
        }

        if (updatedCustomer.getDataCadastro() != null) {
            existingCustomer.setDataCadastro(updatedCustomer.getDataCadastro());
        }

        if (updatedCustomer.getAtivo() != null) {
            existingCustomer.setAtivo(updatedCustomer.getAtivo());
        }

        return repository.save(existingCustomer);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Cliente não encontrado com id: " + id);
        }
        repository.deleteById(id);
    }

    public Cliente deactivate(Long id) {
        Cliente cliente = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Cliente não encontrado com id: " + id));
        cliente.setAtivo(false);
        return repository.save(cliente);
    }

    public List<Cliente> getClientesAtivos() {
        return repository.findByAtivoTrue();
    }

    public Optional<Cliente> getClienteByEmail(String email) {
        return repository.findByEmail(email);
    }
}
