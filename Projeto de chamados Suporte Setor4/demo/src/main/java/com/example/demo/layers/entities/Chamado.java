package com.example.demo.layers.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.CascadeType;
import java.util.List;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"cliente_id","equipamento_id","local_id"}))
public class Chamado {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @CreationTimestamp  
    private LocalDateTime datacriacao;

    @OneToMany(mappedBy = "chamado", cascade = CascadeType.ALL)
    private List<Situacao> situacao;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "equipamento_id", nullable = false)
    private Equipamento equipamento;

    @ManyToOne
    @JoinColumn(name = "local_id", nullable = false)
    private Local local;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataCriacao() {
        return datacriacao;
    }

    public void setDataCriacao(LocalDateTime datacriacao) {
        this.datacriacao = datacriacao;
    }

    public Cliente getCliente(){
        return cliente;
    }

    public void setCliente(Cliente cliente){
        this.cliente = cliente;
    }
    public Equipamento getEquipamento(){
        return equipamento;
    }

    public void setEquipamento(Equipamento equipamento){
        this.equipamento = equipamento;
    }
    public Local getLocal(){
        return local;
    }

    public void setLocal(Local local){
        this.local = local;
    }


    public List<Situacao> getSituacao() {
        return situacao;
    }

    public void setSituacao(List<Situacao> situacao) {
        this.situacao = situacao;
    }


}
