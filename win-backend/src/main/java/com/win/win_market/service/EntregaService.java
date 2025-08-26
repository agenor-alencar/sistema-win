package com.win.win_market.service;

import com.win.win_market.dto.request.EntregaRequestDTO;
import com.win.win_market.dto.response.EntregaResponseDTO;
import com.win.win_market.dto.mapper.EntregaMapper;
import com.win.win_market.exceptions.ResourceNotFoundException;
import com.win.win_market.exceptions.BusinessException;
import com.win.win_market.model.Entrega;
import com.win.win_market.model.Pedido;
import com.win.win_market.model.Motorista;
import com.win.win_market.repository.EntregaRepository;
import com.win.win_market.repository.PedidoRepository;
import com.win.win_market.repository.MotoristaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EntregaService {

    private final EntregaRepository entregaRepository;
    private final PedidoRepository pedidoRepository;
    private final MotoristaRepository motoristaRepository;
    private final EntregaMapper entregaMapper;

    @Transactional
    public EntregaResponseDTO criarEntrega(EntregaRequestDTO requestDTO) {
        Pedido pedido = pedidoRepository.findById(requestDTO.pedidoId())
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));

        if (entregaRepository.existsByPedidoId(requestDTO.pedidoId())) {
            throw new BusinessException("Já existe uma entrega para este pedido");
        }

        Motorista motorista = motoristaRepository.findById(requestDTO.motoristaId())
                .orElseThrow(() -> new ResourceNotFoundException("Motorista não encontrado"));

        if (!"DISPONIVEL".equals(motorista.getStatusDisponibilidade())) {
            throw new BusinessException("Motorista não está disponível");
        }

        Entrega entrega = new Entrega();
        entrega.setPedido(pedido);
        entrega.setMotorista(motorista);
        entrega.setStatus("PENDENTE");
        entrega.setEnderecoEntrega(requestDTO.enderecoEntrega());


        // Atualizar disponibilidade do motorista
        motorista.setStatusDisponibilidade("OCUPADO");
        motoristaRepository.save(motorista);

        Entrega entregaSalva = entregaRepository.save(entrega);
        return entregaMapper.toResponseDTO(entregaSalva);
    }

    @Transactional(readOnly = true)
    public List<EntregaResponseDTO> listarEntregas() {
        return entregaRepository.findAll()
                .stream()
                .map(entregaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EntregaResponseDTO> listarEntregasPorMotorista(UUID motoristaId) {
        return entregaRepository.findByMotoristaId(motoristaId)
                .stream()
                .map(entregaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EntregaResponseDTO> listarEntregasPorStatus(String status) {
        return entregaRepository.findByStatus(status)
                .stream()
                .map(entregaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EntregaResponseDTO buscarPorId(UUID id) {
        Entrega entrega = entregaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entrega não encontrada"));
        return entregaMapper.toResponseDTO(entrega);
    }

    @Transactional(readOnly = true)
    public EntregaResponseDTO buscarPorPedidoId(UUID pedidoId) {
        Entrega entrega = entregaRepository.findByPedidoId(pedidoId)
                .orElseThrow(() -> new ResourceNotFoundException("Entrega não encontrada para este pedido"));
        return entregaMapper.toResponseDTO(entrega);
    }

    @Transactional
    public EntregaResponseDTO iniciarEntrega(UUID id) {
        Entrega entrega = entregaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entrega não encontrada"));

        if (!"PENDENTE".equals(entrega.getStatus())) {
            throw new BusinessException("Entrega não está pendente");
        }

        entrega.setStatus("EM_TRANSITO");


        Entrega entregaAtualizada = entregaRepository.save(entrega);
        return entregaMapper.toResponseDTO(entregaAtualizada);
    }

    @Transactional
    public EntregaResponseDTO finalizarEntrega(UUID id) {
        Entrega entrega = entregaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entrega não encontrada"));

        if (!"EM_TRANSITO".equals(entrega.getStatus())) {
            throw new BusinessException("Entrega não está em trânsito");
        }

        entrega.setStatus("ENTREGUE");
        entrega.setDataEntrega(OffsetDateTime.now());

        // Liberar motorista
        Motorista motorista = entrega.getMotorista();
        motorista.setStatusDisponibilidade("DISPONIVEL");
        motoristaRepository.save(motorista);

        Entrega entregaAtualizada = entregaRepository.save(entrega);
        return entregaMapper.toResponseDTO(entregaAtualizada);
    }

    @Transactional
    public EntregaResponseDTO cancelarEntrega(UUID id, String motivoCancelamento) {
        Entrega entrega = entregaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entrega não encontrada"));

        if ("ENTREGUE".equals(entrega.getStatus())) {
            throw new BusinessException("Não é possível cancelar uma entrega já finalizada");
        }

        entrega.setStatus("CANCELADA");
        entrega.setObservacoes(motivoCancelamento);

        // Liberar motorista
        Motorista motorista = entrega.getMotorista();
        motorista.setStatusDisponibilidade("DISPONIVEL");
        motoristaRepository.save(motorista);

        Entrega entregaAtualizada = entregaRepository.save(entrega);
        return entregaMapper.toResponseDTO(entregaAtualizada);
    }

    @Transactional
    public EntregaResponseDTO atualizarObservacoes(UUID id, String observacoes) {
        Entrega entrega = entregaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entrega não encontrada"));

        entrega.setObservacoes(observacoes);

        Entrega entregaAtualizada = entregaRepository.save(entrega);
        return entregaMapper.toResponseDTO(entregaAtualizada);
    }
}
