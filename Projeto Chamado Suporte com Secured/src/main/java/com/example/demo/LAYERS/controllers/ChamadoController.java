package com.example.demo.LAYERS.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.LAYERS.entities.Chamado;
import com.example.demo.LAYERS.services.ChamadoService;

@RestController
@RequestMapping("/chamados")
public class ChamadoController {

    @Autowired
    private ChamadoService chamadoService;

    // Endpoint para criar um novo chamado
    @PostMapping
    @Secured(value = { "ROLE_ADMIN", "ROLE_GESTOR", })
    public ResponseEntity<Chamado> criarChamado( @RequestBody Chamado chamado) {
        Chamado chamadoCriado = chamadoService.criarChamado(chamado);
        return new ResponseEntity<>(chamadoCriado, HttpStatus.CREATED);
    }
    

    // Endpoint para listar todos os chamados
    @GetMapping
    @Secured(value = { "ROLE_ADMIN", "ROLE_GESTOR", })
    public List<Chamado> listar() {
        return chamadoService.buscarTodos();
    }

    // Endpoint para buscar um chamado por ID
    @GetMapping("/{id}")
    @Secured(value = { "ROLE_ADMIN", "ROLE_GESTOR", })
    public Chamado buscarPorId(@PathVariable Long id) {
        return chamadoService.buscarPorId(id);
    }

    // Endpoint para buscar chamados pelo tombamento do equipamento
    @GetMapping("/tombamento/{tombamento}")
    @Secured(value = { "ROLE_ADMIN", "ROLE_GESTOR", })
    public List<Chamado> buscarChamadosPorTombamento(@PathVariable String tombamento) {
        return chamadoService.buscarPorTombamento(tombamento);
    }

    @PutMapping("/{id}")
    @Secured(value = { "ROLE_ADMIN", "ROLE_GESTOR", })
    public ResponseEntity<Chamado> atualizarChamado(@PathVariable Long id, @RequestBody Chamado chamadoAtualizado) {
    // Chama o serviço para atualizar o chamado
        Chamado chamado = chamadoService.atualizarChamado(id, chamadoAtualizado);
        return ResponseEntity.ok(chamado); // Retorna o chamado atualizado com status 200 OK

    
}
}
