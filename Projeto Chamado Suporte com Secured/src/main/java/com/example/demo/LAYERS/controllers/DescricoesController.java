package com.example.demo.LAYERS.controllers;

import java.util.List;
import java.util.Optional;

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

import com.example.demo.LAYERS.entities.Descricoes;
import com.example.demo.LAYERS.services.DescricoesService;

@RestController
@RequestMapping("/descricoes")
public class DescricoesController {

    @Autowired
    private DescricoesService descricoesService;

    // Endpoint para criar uma nova descrição
    @PostMapping("/{idSituacao}")
    @Secured(value = { "ROLE_ADMIN", "ROLE_GESTOR", })
    public ResponseEntity<Descricoes> criarDescricao(@PathVariable Long idSituacao, @RequestBody Descricoes descricao) {
        // Criar a descrição e associar à situação
        Descricoes novaDescricao = descricoesService.salvarDescricaoComSituacao(idSituacao, descricao);

        return ResponseEntity.status(HttpStatus.CREATED).body(novaDescricao);
    }

    // Endpoint para buscar todas as descrições
    @GetMapping
    @Secured(value = { "ROLE_ADMIN", "ROLE_GESTOR", })
    public ResponseEntity<List<Descricoes>> buscarTodasDescricoes() {
        List<Descricoes> descricoes = descricoesService.buscarTodos();
        return ResponseEntity.ok(descricoes);
    }

    // Endpoint para buscar uma descrição por ID
    @GetMapping("/{id}")
    @Secured(value = { "ROLE_ADMIN", "ROLE_GESTOR", })
    public ResponseEntity<Descricoes> buscarDescricaoPorId(@PathVariable Long id) {
        Optional<Descricoes> descricao = descricoesService.buscarPorId(id);
        return descricao.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Endpoint para atualizar uma descrição por ID
    @PutMapping("/{id}")
    @Secured(value = { "ROLE_ADMIN", "ROLE_GESTOR", })
    public ResponseEntity<Descricoes> atualizarDescricao(@PathVariable Long id, @RequestBody Descricoes descricaoAtualizada) {
        try {
            Descricoes descricao = descricoesService.atualizar(id, descricaoAtualizada);
            return ResponseEntity.ok(descricao);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    // criar uma nova descricao atrelada a situacao.
    @PutMapping("/descricao/{idSituacao}")
    @Secured(value = { "ROLE_ADMIN", "ROLE_GESTOR", })
    public ResponseEntity<Descricoes> adicionarDescricao(@PathVariable Long idSituacao, @RequestBody String descricao) {
        Descricoes novaDescricao = descricoesService.associarDescricaoASituacao(idSituacao, descricao);
        return ResponseEntity.ok(novaDescricao);
}


    // Endpoint para deletar uma descrição por ID
    @DeleteMapping("/{id}")
    @Secured(value = { "ROLE_ADMIN", })
    public ResponseEntity<Void> deletarDescricao(@PathVariable Long id) {
        try {
            descricoesService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
