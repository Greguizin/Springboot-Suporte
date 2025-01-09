package com.example.demo.LAYERS.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.LAYERS.entities.Chamado;

public interface ChamadoRepository extends JpaRepository<Chamado, Long> {
    List<Chamado> findByEquipamentoTombamento(String tombamento);
}
