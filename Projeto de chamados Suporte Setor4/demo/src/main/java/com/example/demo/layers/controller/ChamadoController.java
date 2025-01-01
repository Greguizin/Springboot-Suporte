package com.example.demo.layers.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.layers.entities.Chamado;
import com.example.demo.layers.services.ChamadoService;

@RestController
@RequestMapping("/chamados")
public class ChamadoController {

    @Autowired
    private ChamadoService chamadoService;

    // Endpoint para criar um novo chamado
     @PostMapping
    public ResponseEntity<Chamado> criarChamado(@RequestBody Chamado chamado) {
        Chamado chamadoCriado = chamadoService.criarChamado(chamado);
        return new ResponseEntity<>(chamadoCriado, HttpStatus.CREATED);
    }

    // Endpoint para listar todos os chamados
    @GetMapping
    public List<Chamado> listar() {
        return chamadoService.buscarTodos();
    }

    // Endpoint para buscar um chamado por ID
    @GetMapping("/{id}")
    public Chamado buscarPorId(@PathVariable Long id) {
        return chamadoService.buscarPorId(id);
    }

    // Endpoint para buscar chamados pelo tombamento do equipamento
    @GetMapping("/tombamento/{tombamento}")
    public List<Chamado> buscarChamadosPorTombamento(@PathVariable String tombamento) {
        return chamadoService.buscarPorTombamento(tombamento);
    }
}
