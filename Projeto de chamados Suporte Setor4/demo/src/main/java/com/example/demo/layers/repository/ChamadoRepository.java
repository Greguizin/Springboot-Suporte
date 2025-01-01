package com.example.demo.layers.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.layers.entities.Chamado;

public interface ChamadoRepository extends JpaRepository<Chamado, Long> {
    List<Chamado> findByEquipamentoTombamento(String tombamento);
}
