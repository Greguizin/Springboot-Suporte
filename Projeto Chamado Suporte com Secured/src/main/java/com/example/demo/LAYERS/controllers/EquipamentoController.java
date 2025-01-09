package com.example.demo.LAYERS.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.LAYERS.entities.Equipamento;
import com.example.demo.LAYERS.services.EquipamentoService;

@RestController
@RequestMapping("/equipamentos")
public class EquipamentoController {

    @Autowired
    private EquipamentoService equipamentoService;

    // Endpoint para listar todos os equipamentos
    @GetMapping
    @Secured(value = { "ROLE_ADMIN", "ROLE_GESTOR", })
    public ResponseEntity<List<Equipamento>> listarTodos() {
        List<Equipamento> equipamentos = equipamentoService.listarTodos();
        return ResponseEntity.ok(equipamentos);
    }
     // Endpoint para buscar equipamento por ID
    @GetMapping("/{id}")
    @Secured(value = { "ROLE_ADMIN", "ROLE_GESTOR", })
    public ResponseEntity<Object> buscarPorId(@PathVariable Long id) {
    return equipamentoService.buscarPorId(id);
}

    // Endpoint para criar um novo equipamento
    @PostMapping
    @Secured(value = { "ROLE_ADMIN", "ROLE_GESTOR", })
    public ResponseEntity<Equipamento> criar(@RequestBody Equipamento equipamento) {
        Equipamento novoEquipamento = equipamentoService.salvar(equipamento);
        return ResponseEntity.status(201).body(novoEquipamento);
    }

    // Endpoint para atualizar um equipamento existente
    @PutMapping("/{id}")
    @Secured(value = { "ROLE_ADMIN", "ROLE_GESTOR", })
    public ResponseEntity<Equipamento> atualizar(@PathVariable Long id, @RequestBody Equipamento equipamento) {
        Equipamento equipamentoAtualizado = equipamentoService.atualizar(id, equipamento);
        return ResponseEntity.ok(equipamentoAtualizado);
    }

    // Endpoint para excluir um equipamento por ID
    @DeleteMapping("/{id}")
    @Secured(value = { "ROLE_ADMIN", })
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        equipamentoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
