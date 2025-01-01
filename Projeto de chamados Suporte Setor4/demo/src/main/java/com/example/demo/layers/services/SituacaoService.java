package com.example.demo.layers.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.layers.entities.Chamado;
import com.example.demo.layers.entities.Situacao;
import com.example.demo.layers.repository.ChamadoRepository;
import com.example.demo.layers.repository.SituacaoRepository;

@Service
public class SituacaoService {

    @Autowired
    private SituacaoRepository situacaoRepository;
    @Autowired
    private ChamadoRepository chamadoRepository;

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

    // Método para atualizar uma situação
     public Situacao atualizarSituacao(Long id, Situacao situacaoAtualizada) {
        // Buscar a situação pelo id
        Situacao situacaoExistente = situacaoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Situação não encontrada"));

        // Atualizar o status da situação
        situacaoExistente.setSituacao(situacaoAtualizada.getSituacao());

        // Atualizar a data de modificação
        situacaoExistente.setDatamodf(LocalDateTime.now());

        // Atualizar o chamado associado (se necessário)
        Chamado chamado = situacaoExistente.getChamado();
        if (chamado != null) {
            // Se necessário, você pode fazer algo com o chamado, como salvar ou atualizar o chamado também
            chamadoRepository.save(chamado);
        }

        // Salvar a situação atualizada
        return situacaoRepository.save(situacaoExistente);
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

}
