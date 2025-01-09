package com.example.demo.LAYERS.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.LAYERS.entities.Situacao;

@Repository
public interface SituacaoRepository extends JpaRepository<Situacao, Long> {
@Query("SELECT s FROM Situacao s WHERE s.situacao = :situacao")
List<Situacao> findSituacoesEmAberto(@Param("situacao") Situacao.SituacaoEnum situacao);
}
