package com.example.demo.layers.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.layers.entities.Chamado;
import com.example.demo.layers.entities.Cliente;
import com.example.demo.layers.entities.Equipamento;
import com.example.demo.layers.entities.Local;
import com.example.demo.layers.entities.Situacao;
import com.example.demo.layers.repository.ChamadoRepository;
import com.example.demo.layers.repository.ClienteRepository;
import com.example.demo.layers.repository.EquipamentoRepository;
import com.example.demo.layers.repository.LocalRepository;

@Service
public class ChamadoService {

    @Autowired
    private ChamadoRepository chamadoRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private EquipamentoService equipamentoService;

    @Autowired
    private LocalService localService;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EquipamentoRepository equipamentoRepository;

    @Autowired
    private LocalRepository localRepository;

public Chamado criarChamado(Chamado chamado) {
    // Verificar e salvar o cliente
    if (chamado.getCliente() != null && chamado.getCliente().getNome() != null) {
        Cliente clienteExistente = clienteRepository.findByNome(chamado.getCliente().getNome());
        if (clienteExistente == null) {
            chamado.setCliente(clienteService.salvar(chamado.getCliente()));
        } else {
            chamado.setCliente(clienteExistente);
        }
    } else {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cliente não informado ou nome inválido.");
    }

    // Verificar e salvar o equipamento
    if (chamado.getEquipamento() != null && chamado.getEquipamento().getTombamento() != null) {
        Optional<Equipamento> equipamentoExistente = equipamentoRepository.findByTombamento(chamado.getEquipamento().getTombamento());
        if (!equipamentoExistente.isPresent()) {
            chamado.setEquipamento(equipamentoService.salvar(chamado.getEquipamento()));
        } else {
            chamado.setEquipamento(equipamentoExistente.get());
        }
    } else {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Equipamento não informado ou tombamento inválido.");
    }

    // Verificar e salvar o local
    if (chamado.getLocal() != null && chamado.getLocal().getDepartamento() != null) {
        Optional<Local> localExistente = localRepository.findByDepartamento(chamado.getLocal().getDepartamento());
        if (!localExistente.isPresent()) {
            chamado.setLocal(localService.salvar(chamado.getLocal()));
        } else {
            chamado.setLocal(localExistente.get());
        }
    } else {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Local não informado ou departamento inválido.");
    }

    // Definir a situação do chamado e atribuir a data de modificação automaticamente
    if (chamado.getSituacao() == null) {
        Situacao situacao = new Situacao();
        situacao.setSituacao(Situacao.SituacaoEnum.ABERTO);  
        situacao.setDatamodf(LocalDateTime.now());  
        situacao.setChamado(chamado);  

        
        chamado.setSituacao(List.of(situacao)); 
    }

    
    return chamadoRepository.save(chamado);  // A situação será salva automaticamente por causa do cascade
}

    

    // Método para buscar todos os chamados
    public List<Chamado> buscarTodos() {
        return chamadoRepository.findAll();
    }

    // Método para buscar um chamado por ID
    public Chamado buscarPorId(Long id) {
        return chamadoRepository.findById(id).orElse(null);
    }


    // Chamado pelo tombamento

    public List<Chamado> buscarPorTombamento(String tombamento) {
        return chamadoRepository.findByEquipamentoTombamento(tombamento);
    }

}
