package com.example.demo.layers.entities;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;


@Entity
public class Local {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    

    @Column(nullable = false)
    private String departamento;

    @Column(nullable = false)
    private String descricao;

    //relação 1:N com Chamados

    @JsonIgnore
    @OneToMany(mappedBy = "local", cascade = CascadeType.ALL)
    private List<Chamado> chamado;



    public long getId(){
        return id;
    
    }

    public void Setid(long id){
        this.id = id;
    }


    public String getDepartamento(){
        return departamento;
    }
    
    public void setDepartamento(String departamento){
        this.departamento = departamento;
    }

    public String getDescricao(){
        return descricao;

    }
    public void setDescricao(String descricao){
        this.descricao = descricao;
    }
    public List<Chamado> getChamado(){
        return chamado;
    }
    public void setChamado(List<Chamado> chamado){
        this.chamado = chamado;
    }






}
