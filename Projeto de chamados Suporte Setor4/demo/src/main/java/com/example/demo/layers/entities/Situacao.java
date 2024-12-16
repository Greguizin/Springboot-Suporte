package com.example.demo.layers.entities;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.time.LocalDateTime;



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
private SituacaoEnum situacao;

@Column(nullable = false)
private LocalDateTime datamodf;

@ManyToOne
@JoinColumn(name = "chamado_id", nullable = false)
private Chamado chamado;

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

public LocalDateTime  getDatamodf() {
    return datamodf;
}

public void setDatamodf(LocalDateTime  datamodf) {
    this.datamodf = LocalDateTime.now();
}

public Chamado getChamado(){
    return chamado;
}
public void setChamado(Chamado chamado){
this.chamado = chamado;
}



}
