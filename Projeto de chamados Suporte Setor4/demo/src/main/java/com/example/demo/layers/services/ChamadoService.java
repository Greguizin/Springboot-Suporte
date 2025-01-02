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
import com.example.demo.layers.entities.Descricoes;
import com.example.demo.layers.entities.Equipamento;
import com.example.demo.layers.entities.Local;
import com.example.demo.layers.entities.Situacao;
import com.example.demo.layers.repository.ChamadoRepository;
import com.example.demo.layers.repository.ClienteRepository;
import com.example.demo.layers.repository.DescricoesRepository;
import com.example.demo.layers.repository.EquipamentoRepository;
import com.example.demo.layers.repository.LocalRepository;
import com.example.demo.layers.repository.SituacaoRepository;
import com.example.demo.utils.ValidadorTelefone;

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

    
    public Chamado criarChamado(Chamado chamado) {
        try {
            // Verificar e salvar o cliente
            if (chamado.getCliente() != null && chamado.getCliente().getNome() != null && !chamado.getCliente().getNome().trim().isEmpty()) {
                Cliente clienteExistente = clienteRepository.findByNome(chamado.getCliente().getNome());
                if (clienteExistente != null) {
                    chamado.setCliente(clienteExistente);
                } else {
                    // Validar telefone antes de salvar o cliente
                    if (!ValidadorTelefone.validarTelefone(chamado.getCliente().getTelefone())) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Telefone inválido.");
                    }
                    chamado.setCliente(clienteRepository.save(chamado.getCliente()));
                }
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cliente não informado ou nome inválido.");
            }
    
            // Verificar e salvar o equipamento
            if (chamado.getEquipamento() != null && chamado.getEquipamento().getTombamento() != null && !chamado.getEquipamento().getTombamento().trim().isEmpty()) {
                Optional<Equipamento> equipamentoExistente = equipamentoRepository.findByTombamento(chamado.getEquipamento().getTombamento());
                if (equipamentoExistente.isPresent()) {
                    chamado.setEquipamento(equipamentoExistente.get());
                } else {
                    chamado.setEquipamento(equipamentoRepository.save(chamado.getEquipamento()));
                }
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Equipamento não informado ou tombamento inválido.");
            }
    
            // Verificar e salvar o local
            if (chamado.getLocal() != null && chamado.getLocal().getDepartamento() != null && !chamado.getLocal().getDepartamento().trim().isEmpty()) {
                Optional<Local> localExistente = localRepository.findByDepartamento(chamado.getLocal().getDepartamento());
                if (localExistente.isPresent()) {
                    chamado.setLocal(localExistente.get());
                } else {
                    chamado.setLocal(localRepository.save(chamado.getLocal()));
                }
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Local não informado ou departamento inválido.");
            }
    
            // Salvar o chamado no banco de dados
            Chamado chamadoSalvo = chamadoRepository.save(chamado);
    
            // Criar e salvar a situação
            Situacao situacao = new Situacao();
            situacao.setSituacao(Situacao.SituacaoEnum.ABERTO);
            situacao.setDatamodf(LocalDateTime.now());
            situacao.setChamado(chamadoSalvo);
    
            situacao = situacaoRepository.save(situacao);
    
            // Criar e salvar a descrição
            Descricoes descricao = new Descricoes();
            descricao.setDescricao("Chamado em aberto");
            descricao.setSituacao(situacao);
    
            descricoesRepository.save(descricao);
    
            // Associar a situação ao chamado
            chamadoSalvo.setSituacao(List.of(situacao));
    
            return chamadoSalvo; // Retornar o chamado atualizado
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao criar chamado: " + e.getMessage(), e);
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
