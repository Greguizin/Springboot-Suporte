package com.example.demo.layers.controller;

import com.example.demo.layers.entities.Cliente;
import com.example.demo.layers.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    // Endpoint para salvar um cliente
    @PostMapping
    public ResponseEntity<Object> salvarCliente(@RequestBody Cliente cliente) {
        return ResponseEntity.status(201).body(cliente);
    }

    // Endpoint para buscar todos os clientes
    @GetMapping
    public ResponseEntity<List<Cliente>> buscarTodosClientes() {
        return clienteService.buscarTodos();
    }

    // Endpoint para buscar um cliente pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarClientePorId(@PathVariable Long id) {
        return clienteService.buscarPorId(id);
    }

    // Endpoint para deletar um cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarCliente(@PathVariable Long id) {
        return clienteService.deletar(id);
    }
}
