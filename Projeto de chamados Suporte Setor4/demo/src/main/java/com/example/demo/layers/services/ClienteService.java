package com.example.demo.layers.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.demo.layers.entities.Cliente;
import com.example.demo.layers.repository.ClienteRepository;
import com.example.demo.utils.ValidadorTelefone;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    // Método para salvar um cliente
    public Cliente salvar(Cliente cliente) {
        // Valida se os campos obrigatórios estão preenchidos
        if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do cliente não pode ser nulo ou vazio!");
        }

        // Valida se o telefone está no formato correto
        if (cliente.getTelefone() == null || cliente.getTelefone().trim().isEmpty()) {
            throw new IllegalArgumentException("O telefone do cliente não pode ser nulo ou vazio!");
        }

        if (!ValidadorTelefone.validarTelefone(cliente.getTelefone())) {
            throw new IllegalArgumentException("O telefone do cliente não está no formato válido. O formato esperado é (XX) XXXXX-XXXX.");
        }

        // Salva o cliente no banco de dados
        return clienteRepository.save(cliente);
    }


    // Método para buscar todos os clientes
    public ResponseEntity<List<Cliente>> buscarTodos() {
        List<Cliente> clientes = clienteRepository.findAll();
        return ResponseEntity.ok(clientes);
    }

    // Método para buscar um cliente pelo ID
    public ResponseEntity<Object> buscarPorId(Long id) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);

        // Verifica se o cliente foi encontrado
        if (clienteOptional.isPresent()) {
            return ResponseEntity.ok(clienteOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado!");
        }
    }

    // Método para deletar um cliente
    public ResponseEntity<Object> deletar(Long id) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);

        // Verifica se o cliente existe antes de tentar deletar
        if (clienteOptional.isPresent()) {
            clienteRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado!");
        }
    }
}
