package com.example.demo.layers.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.layers.entities.Descricoes;
import com.example.demo.layers.entities.Situacao;
import com.example.demo.layers.repository.DescricoesRepository;
import com.example.demo.layers.repository.SituacaoRepository;

@Service
public class SituacaoService {

    @Autowired
    private SituacaoRepository situacaoRepository;

    @Autowired
    private DescricoesRepository descricoesRepository;

    // Método para salvar uma nova situação
    public Situacao salvar(Situacao situacao) {
        return situacaoRepository.save(situacao);
    }

    // Método para buscar todos as situações
    public List<Situacao> buscarTodos() {
        return situacaoRepository.findAll();
    }

    // Método para buscar uma situação por ID
    public Optional<Situacao> buscarPorId(Long id) {
        return situacaoRepository.findById(id);
    }

     public Situacao atualizarSituacao(Long id, Situacao situacaoAtualizada, String novaDescricao) {
        // Buscar a situação existente pelo ID
        Situacao situacaoExistente = situacaoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Situação não encontrada"));

        // Atualizar os campos da situação
        situacaoExistente.setSituacao(situacaoAtualizada.getSituacao());
        situacaoExistente.setDatamodf(LocalDateTime.now());

        // Salvar a situação atualizada no banco
        situacaoExistente = situacaoRepository.save(situacaoExistente);

        // Criar uma nova descrição e associar à situação
        Descricoes novaDescricaoEntity = new Descricoes();
        novaDescricaoEntity.setDescricao(novaDescricao);
        novaDescricaoEntity.setSituacao(situacaoExistente);

        // Salvar a nova descrição no banco de dados
        descricoesRepository.save(novaDescricaoEntity);

        return situacaoExistente;
    }


    // Método para deletar uma situação
    public void deletar(Long id) {
        if (!situacaoRepository.existsById(id)) {
            throw new IllegalArgumentException("Situação não encontrada para deletar.");
        }
        situacaoRepository.deleteById(id);
    }

    public List<Situacao> buscarSituacoesEmAberto() {
        return situacaoRepository.findSituacoesEmAberto(Situacao.SituacaoEnum.ABERTO);
    }


    public List<Descricoes> buscarDescricoesPorSituacao(Long situacaoId) {
        // Buscar a situação pela ID
        Optional<Situacao> situacao = situacaoRepository.findById(situacaoId);
        if (situacao.isPresent()) {
            return descricoesRepository.findBySituacao(situacao.get());
        } else {
            throw new RuntimeException("Situação não encontrada");
        }
    }

}
