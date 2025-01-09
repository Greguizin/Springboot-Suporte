package com.example.demo.LAYERS.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import com.example.demo.LAYERS.entities.Situacao;
import com.example.demo.LAYERS.services.SituacaoService;

@RestController
@RequestMapping("/situacoes")
public class SituacaoController {

    @Autowired
    private SituacaoService situacaoService;

    // Endpoint para salvar uma nova situação
    @PostMapping("/{idChamado}")
    @Secured(value = { "ROLE_ADMIN", "ROLE_GESTOR", })
    public ResponseEntity<Situacao> criarSituacao(@PathVariable Long idChamado, @RequestBody Situacao situacao) {
        // Criar a situação e associá-la ao chamado
        Situacao novaSituacao = situacaoService.salvarSituacaoComChamado(idChamado, situacao);

        return ResponseEntity.status(HttpStatus.CREATED).body(novaSituacao);
    }

    // Endpoint para buscar todas as situações
    @GetMapping
    @Secured(value = { "ROLE_ADMIN", "ROLE_GESTOR", })
    public ResponseEntity<List<Situacao>> buscarTodasSituacoes() {
        List<Situacao> situacoes = situacaoService.buscarTodos();
        return new ResponseEntity<>(situacoes, HttpStatus.OK);
    }

    // Endpoint para buscar uma situação por ID
    @GetMapping("/{id}")
    @Secured(value = { "ROLE_ADMIN", "ROLE_GESTOR", })
    public ResponseEntity<Situacao> buscarSituacaoPorId(@PathVariable Long id) {
        Optional<Situacao> situacao = situacaoService.buscarPorId(id);
        return situacao.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint para atualizar uma situação
    @PutMapping("/{id}")
    @Secured(value = { "ROLE_ADMIN", "ROLE_GESTOR" })
    public ResponseEntity<Situacao> atualizarSituacao(@PathVariable Long id, @RequestBody Descricoes descricaoRequest) {
        Situacao situacaoAtualizada = new Situacao();
        situacaoAtualizada.setSituacao(descricaoRequest.getSituacao().getSituacao());
    
        Situacao situacao = situacaoService.atualizarSituacao(id, situacaoAtualizada, descricaoRequest.getDescricao());
        return ResponseEntity.ok(situacao);
    }
    


    // Endpoint para deletar uma situação
    @DeleteMapping("/{id}")
    @Secured(value = { "ROLE_ADMIN", })
    public ResponseEntity<Void> deletarSituacao(@PathVariable Long id) {
        situacaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint para buscar todas as situações em aberto
    @GetMapping("/abertas")
    @Secured(value = { "ROLE_ADMIN", "ROLE_GESTOR", })
    public List<Situacao> buscarSituacoesEmAberto() {
         return situacaoService.buscarSituacoesEmAberto();
    }

      // Endpoint para buscar todas as descrições de uma situação por ID
    @GetMapping("/{id}/descricoes")
    @Secured(value = { "ROLE_ADMIN", "ROLE_GESTOR", })
    public ResponseEntity<List<String>> buscarDescricoesPorSituacao(@PathVariable Long id) {
        // Buscar as descrições e retornar somente os valores de descricao
        List<String> descricoes = situacaoService.buscarDescricoesPorSituacao(id)
                                                 .stream()
                                                 .map(Descricoes::getDescricao)  // Extrair apenas a descrição
                                                 .collect(Collectors.toList());
        return ResponseEntity.ok(descricoes);
    }



}
