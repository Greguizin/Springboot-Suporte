package com.example.demo.LAYERS.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.LAYERS.entities.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Cliente findByNome(String nome);
}
