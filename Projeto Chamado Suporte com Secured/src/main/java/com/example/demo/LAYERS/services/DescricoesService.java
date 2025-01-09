package com.example.demo.LAYERS.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.LAYERS.entities.Descricoes;
import com.example.demo.LAYERS.entities.Situacao;
import com.example.demo.LAYERS.repositories.DescricoesRepository;
import com.example.demo.LAYERS.repositories.SituacaoRepository;

@Service
public class DescricoesService {

    @Autowired
    private DescricoesRepository descricoesRepository;
    @Autowired
    private SituacaoRepository situacaoRepository;

    // Método para salvar uma nova descrição
    public Descricoes salvarDescricaoComSituacao(Long idSituacao, Descricoes descricao) {
        // Buscar a situação pelo ID
        Situacao situacao = situacaoRepository.findById(idSituacao)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Situação não encontrada"));

        // Associar a situação à descrição
        descricao.setSituacao(situacao);

        // Salvar a nova descrição com a situação associada
        return descricoesRepository.save(descricao);
    }

    // Método para buscar todas as descrições
    public List<Descricoes> buscarTodos() {
        return descricoesRepository.findAll();
    }

    // Método para buscar uma descrição por ID
    public Optional<Descricoes> buscarPorId(Long id) {
        return descricoesRepository.findById(id);
    }

    // Método para atualizar uma descrição
    public Descricoes atualizar(Long id, Descricoes descricaoAtualizada) {
        Descricoes descricaoExistente = descricoesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Descrição não encontrada"));

        descricaoExistente.setDescricao(descricaoAtualizada.getDescricao());

        return descricoesRepository.save(descricaoExistente);
    }

     // Método para associar uma nova descrição a uma situação existente
    public Descricoes associarDescricaoASituacao(Long idSituacao, String descricao) {
        // Buscar a situação pelo ID
        Situacao situacao = situacaoRepository.findById(idSituacao)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Situação não encontrada"));

        // Criar a nova descrição e associar à situação
        Descricoes novaDescricao = new Descricoes();
        novaDescricao.setDescricao(descricao);
        novaDescricao.setSituacao(situacao);  // Associando a situação à nova descrição

        // Salvar a descrição no banco
        return descricoesRepository.save(novaDescricao);
    }

    // Método para deletar uma descrição
    public void deletar(Long id) {
        if (!descricoesRepository.existsById(id)) {
            throw new RuntimeException("Descrição não encontrada para deletar.");
        }
        descricoesRepository.deleteById(id);
    }
}
