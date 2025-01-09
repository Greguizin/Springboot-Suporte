package com.example.demo.LAYERS.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.example.demo.LAYERS.entities.Local;
import com.example.demo.LAYERS.services.LocalService;

@RestController
@RequestMapping("/locais")
public class LocalController {

    private final LocalService localService;

    @Autowired
    public LocalController(LocalService localService) {
        this.localService = localService;
    }

    // Endpoint para salvar um novo Local
    @PostMapping
    @Secured(value = { "ROLE_ADMIN", "ROLE_GESTOR", })
    public ResponseEntity<Local> salvar(@RequestBody Local local) {
        Local localSalvo = localService.salvar(local);
        return new ResponseEntity<>(localSalvo, HttpStatus.CREATED);
    }
    @GetMapping("/nome/{departamento}")
    @Secured(value = { "ROLE_ADMIN", "ROLE_GESTOR", })
    public ResponseEntity<Local> buscarPorNome(@PathVariable String departamento) {
        Local local = localService.buscarPorNome(departamento);
        return ResponseEntity.ok(local);
    }

    // Endpoint para buscar um Local por ID
    @GetMapping("/{id}")
    @Secured(value = { "ROLE_ADMIN", "ROLE_GESTOR", })
    public ResponseEntity<Local> buscarPorId(@PathVariable Long id) {
        Local local = localService.buscarPorId(id);
        return ResponseEntity.ok(local);
    }

    // Endpoint para listar todos os Locais
    @GetMapping
    @Secured(value = { "ROLE_ADMIN", "ROLE_GESTOR", })
    public ResponseEntity<List<Local>> listarTodos() {
        List<Local> locais = localService.listarTodos();
        return ResponseEntity.ok(locais);
    }

    // Endpoint para atualizar um Local existente
    @PutMapping("/{id}")
    @Secured(value = { "ROLE_ADMIN", "ROLE_GESTOR", })
    public ResponseEntity<Local> atualizar(@PathVariable Long id, @RequestBody Local local) {
        Local localAtualizado = localService.atualizar(id, local);
        return ResponseEntity.ok(localAtualizado);
    }

    // Endpoint para deletar um Local
    @DeleteMapping("/{id}")
    @Secured(value = { "ROLE_ADMIN",})
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        localService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
