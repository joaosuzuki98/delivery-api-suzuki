package com.deliverytech.delivery.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deliverytech.delivery.entities.Restaurante;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
    Optional<Restaurante> findByNome(String nome);
    
    List<Restaurante> findByCategoria(String categoria);

    List<Restaurante> findByAtivoTrue();

    List<Restaurante> findByAtivoTrueOrderByAvaliacaoDesc();
}
