package com.example.demo.layers.entities;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Situacao {

    public enum SituacaoEnum {
        ABERTO,
        EM_ANDAMENTO,
        CONCLUIDO,
        FECHADO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SituacaoEnum situacao;

    @Column(nullable = false)
    private LocalDateTime datamodf;

    @ManyToOne
    @JoinColumn(name = "chamado_id", nullable = false)
    private Chamado chamado;

    @JsonIgnore
    @OneToMany(mappedBy = "situacao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Descricoes> descricoes; // Relacionamento com Descricoes

    // Construtor padrão
    public Situacao() {
        this.datamodf = LocalDateTime.now(); // Define a data de modificação ao inicializar
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SituacaoEnum getSituacao() {
        return situacao;
    }

    public void setSituacao(SituacaoEnum situacao) {
        this.situacao = situacao;
    }

    public LocalDateTime getDatamodf() {
        return datamodf;
    }

    public void setDatamodf(LocalDateTime datamodf) {
        this.datamodf = datamodf;
    }

    public Chamado getChamado() {
        return chamado;
    }

    public void setChamado(Chamado chamado) {
        this.chamado = chamado;
    }

    public List<Descricoes> getDescricoes() {
        return descricoes;
    }

    public void setDescricoes(List<Descricoes> descricoes) {
        this.descricoes = descricoes;
    }

    public void adicionarDescricao(Descricoes descricao) {
        this.descricoes.add(descricao);
        descricao.setSituacao(this); // Define o vínculo bidirecional
    }

    public void removerDescricao(Descricoes descricao) {
        this.descricoes.remove(descricao);
        descricao.setSituacao(null); // Remove o vínculo bidirecional
    }
}
