package com.example.demo.layers.services;

import com.example.demo.layers.entities.Local;
import com.example.demo.layers.repository.LocalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class LocalService {

    private final LocalRepository localRepository;

    @Autowired
    public LocalService(LocalRepository localRepository) {
        this.localRepository = localRepository;
    }

    // Salvar um novo Local
      public Local salvar(Local local) {
        // Verifica se já existe um local com o mesmo departamento
        Optional<Local> localExistente = localRepository.findByDepartamento(local.getDepartamento());
        
        if (localExistente.isPresent()) {
            // Se já existe, lança uma exceção com mensagem de erro
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe um local com o mesmo departamento.");
        }

        // Se não existir, salva o novo local
        return localRepository.save(local);
    }
    // Buscar Local por nome (departamento)
    public Local buscarPorNome(String departamento) {
         return localRepository.findByDepartamento(departamento)
         .orElseThrow(() -> new RuntimeException("Local com o departamento '" + departamento + "' não encontrado"));
     }

    // Buscar Local por ID
    public Local buscarPorId(Long id) {
        return localRepository.findById(id).orElseThrow(() -> new RuntimeException("Local não encontrado"));
    }

    // Listar todos os Locais
    public List<Local> listarTodos() {
        return localRepository.findAll();
    }

    // Atualizar um Local existente
    public Local atualizar(Long id, Local local) {
        Optional<Local> localExistente = localRepository.findById(id);
        if (localExistente.isPresent()) {
            Local localAtualizado = localExistente.get();
            localAtualizado.setDepartamento(local.getDepartamento());
            localAtualizado.setDescricao(local.getDescricao());
            return localRepository.save(localAtualizado);
        } else {
            throw new RuntimeException("Local não encontrado");
        }
    }

    // Deletar um Local
    public void deletar(Long id) {
        localRepository.deleteById(id);
    }
}
