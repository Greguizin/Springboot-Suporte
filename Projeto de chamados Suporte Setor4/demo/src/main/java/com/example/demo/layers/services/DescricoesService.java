package com.example.demo.layers.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.layers.entities.Descricoes;
import com.example.demo.layers.repository.DescricoesRepository;

@Service
public class DescricoesService {

    @Autowired
    private DescricoesRepository descricoesRepository;

    // Método para salvar uma nova descrição
    public Descricoes salvar(Descricoes descricao) {
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

    // Método para deletar uma descrição
    public void deletar(Long id) {
        if (!descricoesRepository.existsById(id)) {
            throw new RuntimeException("Descrição não encontrada para deletar.");
        }
        descricoesRepository.deleteById(id);
    }
}
