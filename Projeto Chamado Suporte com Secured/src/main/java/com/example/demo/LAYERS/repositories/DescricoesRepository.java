package com.example.demo.LAYERS.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.LAYERS.entities.Descricoes;
import com.example.demo.LAYERS.entities.Situacao;

public interface DescricoesRepository extends JpaRepository<Descricoes, Long> {
    List<Descricoes> findBySituacaoId(Long situacaoId);
    List<Descricoes> findBySituacao(Situacao situacao);
}
