package com.example.demo.layers.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.layers.entities.Descricoes;
import com.example.demo.layers.entities.Situacao;

public interface DescricoesRepository extends JpaRepository<Descricoes, Long> {
    List<Descricoes> findBySituacaoId(Long situacaoId);
    List<Descricoes> findBySituacao(Situacao situacao);
}
