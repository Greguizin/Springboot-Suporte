package com.example.demo.layers.DTOS;

import com.example.demo.layers.entities.Situacao;

public class AtualizarSituacaoRequest {

    private Situacao.SituacaoEnum situacao;   
    private String novaDescricao;            

    // Getters e Setters
    public Situacao.SituacaoEnum getSituacao() {
        return situacao;
    }

    public void setSituacao(Situacao.SituacaoEnum situacao) {
        this.situacao = situacao;
    }

    public String getNovaDescricao() {
        return novaDescricao;
    }

    public void setNovaDescricao(String novaDescricao) {
        this.novaDescricao = novaDescricao;
    }
}
