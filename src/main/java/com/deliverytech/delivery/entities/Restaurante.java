package com.deliverytech.delivery.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "restaurantes")
public class Restaurante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(length = 50)
    private String categoria;

    @Column(length = 255)
    private String endereco;

    @Column(length = 20)
    private String telefone;

    @Column(name = "taxa_entrega")
    private Double taxaEntrega;

    @Column()
    private Double avaliacao;

    @Column(nullable = false)
    private Boolean ativo = true;

    @OneToMany(mappedBy = "restaurante")
    @JsonIgnore
    private List<Produto> produtos;
}
