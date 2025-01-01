package com.example.demo.layers.entities;
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
import jakarta.persistence.OneToMany;


@Entity
public class Equipamento {
    public enum TipoEnum {
        COMPUTADOR,
        NOTEBOOK,
        MONITOR,
        NOBREAK_ESTABILIZADOR,
        PROJETOR,
        ROTEADOR,
        SWITCH,
        OUTRO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column (columnDefinition = "TEXT")
    private String descricao;

    @Column(nullable = false)
    private String tombamento;

    @Enumerated (EnumType.STRING)
    private TipoEnum tipo;

    @JsonIgnore
    @OneToMany(mappedBy = "equipamento",cascade = CascadeType.ALL)
    private List<Chamado> chamado;

    
    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getTombamento(){
        return tombamento;
    }

    public void setTombamento(String tombamento){
        this.tombamento = tombamento;
    }
    public String getNome(){
        return nome;
    }
    
    public void setNome(String nome){
        this.nome = nome;
    }
    public String getDescricao(){
        return descricao;
    }
    public void setDescricao(String descricao){
        this.descricao = descricao;
    }

    public TipoEnum getTipo(){
        return tipo;
    }

    public void setTipo(TipoEnum tipo){
        this.tipo = tipo;
    }
    public List<Chamado> getChamado(){
        return chamado;
    }
    public void setChamado(List<Chamado> chamado){
        this.chamado = chamado;
    }

}
