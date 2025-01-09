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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.LAYERS.entities.Cliente;
import com.example.demo.LAYERS.services.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    // Endpoint para salvar um cliente
    @PostMapping
    @Secured(value = { "ROLE_ADMIN", "ROLE_GESTOR", })
    public ResponseEntity<Cliente> criarCliente(@RequestBody Cliente cliente) {
        Cliente clienteCriado = clienteService.salvar(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteCriado);
    }

    // Endpoint para buscar todos os clientes
    @GetMapping
    @Secured(value = { "ROLE_ADMIN", "ROLE_GESTOR", })
    public ResponseEntity<List<Cliente>> buscarTodosClientes() {
        return clienteService.buscarTodos();
    }

    // Endpoint para buscar um cliente pelo ID
    @GetMapping("/{id}")
    @Secured(value = { "ROLE_ADMIN", "ROLE_GESTOR", })
    public ResponseEntity<Object> buscarClientePorId(@PathVariable Long id) {
        return clienteService.buscarPorId(id);
    }

    // Endpoint para deletar um cliente
    @DeleteMapping("/{id}")
    @Secured(value = { "ROLE_ADMIN", })
    public ResponseEntity<Object> deletarCliente(@PathVariable Long id) {
        return clienteService.deletar(id);
    }
}
