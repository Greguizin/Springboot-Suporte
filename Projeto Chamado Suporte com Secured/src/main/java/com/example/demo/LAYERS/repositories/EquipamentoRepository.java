package com.example.demo.LAYERS.repositories;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.LAYERS.entities.Equipamento;


@Repository
public interface EquipamentoRepository extends JpaRepository<Equipamento, Long> {
    Optional<Equipamento> findByTombamento(String tombamento);
    Equipamento findByNome(String nome);
}


