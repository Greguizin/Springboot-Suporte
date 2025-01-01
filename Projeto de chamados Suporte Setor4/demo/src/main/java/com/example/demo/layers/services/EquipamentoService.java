package com.example.demo.layers.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.layers.entities.Equipamento;
import com.example.demo.layers.repository.EquipamentoRepository;

@Service
public class EquipamentoService {
    @Autowired
    private EquipamentoRepository equipamentoRepository;

    // Lista todos os equipamentos.
    public List<Equipamento> listarTodos() {
        return equipamentoRepository.findAll();
    }

    // Buscar por id.
    public ResponseEntity<Object> buscarPorId(Long id) {
  
    Equipamento equipamento = equipamentoRepository.findById(id).orElse(null);

    if (equipamento == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Equipamento não encontrado!");
    }
    
    return ResponseEntity.ok(equipamento);
}

    //Salvar equipamento
    public Equipamento salvar(Equipamento equipamento) {
        // Verifica se algum valor obrigatório é nulo
        if (equipamento.getNome() == null || equipamento.getNome().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O nome do equipamento não pode ser nulo ou vazio!");
        }
        if (equipamento.getTombamento() == null || equipamento.getTombamento().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O tombamento do equipamento não pode ser nulo ou vazio!");
        }
        if (equipamento.getTipo() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O tipo do equipamento não pode ser nulo!");
        }

        // Verifica se o tombamento já está em uso
        Optional<Equipamento> equipamentoExistente = equipamentoRepository.findByTombamento(equipamento.getTombamento());
        if (equipamentoExistente.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O tombamento informado já está em uso. Por favor, informe um tombamento único.");
        }
        // Salva o equipamento no banco de dados
        return equipamentoRepository.save(equipamento);
    }
    
    // Auxiliar para o atualizar abaixo.
    public Equipamento buscarPorId2(Long id) {
        return equipamentoRepository.findById(id).orElseThrow(() -> new RuntimeException("Equipamento não encontrado!"));
    }
    // Atualizar o equipamento
    public Equipamento atualizar(Long id, Equipamento equipamento) {
        Equipamento existente = buscarPorId2(id);
        existente.setNome(equipamento.getNome());
        existente.setDescricao(equipamento.getDescricao());
        existente.setTombamento(equipamento.getTombamento());
        existente.setTipo(equipamento.getTipo());
        return equipamentoRepository.save(existente);
    }

    public void excluir(Long id) {
        equipamentoRepository.deleteById(id);
    }
}
