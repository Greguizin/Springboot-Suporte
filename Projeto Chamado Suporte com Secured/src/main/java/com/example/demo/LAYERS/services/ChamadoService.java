package com.example.demo.LAYERS.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.LAYERS.entities.Chamado;
import com.example.demo.LAYERS.entities.Cliente;
import com.example.demo.LAYERS.entities.Descricoes;
import com.example.demo.LAYERS.entities.Equipamento;
import com.example.demo.LAYERS.entities.Local;
import com.example.demo.LAYERS.entities.Situacao;
import com.example.demo.LAYERS.repositories.ChamadoRepository;
import com.example.demo.LAYERS.repositories.ClienteRepository;
import com.example.demo.LAYERS.repositories.DescricoesRepository;
import com.example.demo.LAYERS.repositories.EquipamentoRepository;
import com.example.demo.LAYERS.repositories.LocalRepository;
import com.example.demo.LAYERS.repositories.SituacaoRepository;
import com.example.demo.utils.ValidadorTelefone;

import jakarta.transaction.Transactional;

@Service
public class ChamadoService {

    @Autowired
    private ChamadoRepository chamadoRepository;
    @Autowired
    private SituacaoRepository situacaoRepository;
    
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EquipamentoRepository equipamentoRepository;

    @Autowired
    private LocalRepository localRepository;
    @Autowired
    private DescricoesRepository descricoesRepository;

@Transactional
public Chamado criarChamado(Chamado chamado) {
    try {
        // Validar cliente
        if (chamado.getCliente() == null || chamado.getCliente().getNome() == null || chamado.getCliente().getNome().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cliente não informado ou nome inválido.");
        }
        if (!ValidadorTelefone.validarTelefone(chamado.getCliente().getTelefone())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Telefone inválido.");
        }

        // Persistir cliente se não existe
        Cliente cliente = clienteRepository.findByNome(chamado.getCliente().getNome());
        if (cliente == null) {
            cliente = clienteRepository.save(chamado.getCliente());
        } else {
            // Não alterar o cliente se já existe
            chamado.setCliente(cliente); // Apenas associar o cliente existente
        }

        // Validar e persistir equipamento 
        if (chamado.getEquipamento() == null || chamado.getEquipamento().getTombamento() == null || chamado.getEquipamento().getTombamento().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Equipamento não informado ou tombamento inválido.");
        }
        Optional<Equipamento> equipamento = equipamentoRepository.findByTombamento(chamado.getEquipamento().getTombamento());
        if (equipamento.isPresent()) {
            chamado.setEquipamento(equipamento.get());
        } else {
            Equipamento novoEquipamento = equipamentoRepository.save(chamado.getEquipamento());
            chamado.setEquipamento(novoEquipamento);
        }

        // Validar e persistir local 
        if (chamado.getLocal() == null || chamado.getLocal().getDepartamento() == null || chamado.getLocal().getDepartamento().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Local não informado ou departamento inválido.");
        }
        Optional<Local> local = localRepository.findByDepartamento(chamado.getLocal().getDepartamento());
        if (local.isPresent()) {
            chamado.setLocal(local.get());
        } else {
            Local novoLocal = localRepository.save(chamado.getLocal());
            chamado.setLocal(novoLocal);
        }

        // Salvar o chamado inicialmente
        chamado = chamadoRepository.save(chamado);

        // Criar e salvar a situação inicial
        Situacao situacao = new Situacao();
        situacao.setSituacao(Situacao.SituacaoEnum.ABERTO);
        situacao.setDatamodf(LocalDateTime.now());
        situacao.setChamado(chamado); 
        situacao = situacaoRepository.save(situacao);

        // Criar e salvar a descrição inicial
        Descricoes descricao = new Descricoes();
        descricao.setDescricao("Chamado em aberto");
        descricao.setSituacao(situacao);
        descricoesRepository.save(descricao);

        // Adicionar a nova situação à lista de situações do chamado
        if (chamado.getSituacao() == null) {
            chamado.setSituacao(new ArrayList<>());
        }
        chamado.getSituacao().add(situacao);

        // Salvar o chamado com a nova situação
        return chamadoRepository.save(chamado);
    
    }
    catch (DataIntegrityViolationException e) {
        throw new ResponseStatusException(HttpStatus.CONFLICT, "Erro de unicidade: " + e.getMostSpecificCause().getMessage(), e);
    }
     catch (Exception e) {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao criar chamado: " + e.getMessage(), e);
    }
}
    

    
@Transactional
public Chamado atualizarChamado(Long id, Chamado chamadoAtualizado) {
    try {
        Chamado chamadoExistente = chamadoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Chamado não encontrado"));

        if (chamadoAtualizado.getCliente() != null && chamadoAtualizado.getCliente().getNome() != null) {
            Cliente cliente = clienteRepository.findByNome(chamadoAtualizado.getCliente().getNome());
            if (cliente == null) {
                cliente = clienteRepository.save(chamadoAtualizado.getCliente());
            }
            chamadoExistente.setCliente(cliente);
        }

        if (chamadoAtualizado.getEquipamento() != null && chamadoAtualizado.getEquipamento().getTombamento() != null) {
            Optional<Equipamento> equipamento = equipamentoRepository.findByTombamento(chamadoAtualizado.getEquipamento().getTombamento());
            if (equipamento.isPresent()) {
                chamadoExistente.setEquipamento(equipamento.get());
            } else {
                Equipamento novoEquipamento = equipamentoRepository.save(chamadoAtualizado.getEquipamento());
                chamadoExistente.setEquipamento(novoEquipamento);
            }
        }

        // Atualizar local se necessário
        if (chamadoAtualizado.getLocal() != null && chamadoAtualizado.getLocal().getDepartamento() != null) {
            Optional<Local> local = localRepository.findByDepartamento(chamadoAtualizado.getLocal().getDepartamento());
            if (local.isPresent()) {
                chamadoExistente.setLocal(local.get());
            } else {
                Local novoLocal = localRepository.save(chamadoAtualizado.getLocal());
                chamadoExistente.setLocal(novoLocal);
            }
        }

        // Persistir as alterações
        return chamadoRepository.save(chamadoExistente);

    }   
    catch (DataIntegrityViolationException e) {
        throw new ResponseStatusException(HttpStatus.CONFLICT, "Erro de unicidade: " + e.getMostSpecificCause().getMessage(), e);
    } 
    catch (Exception e) {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao atualizar chamado: " + e.getMessage(), e);
    }
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
