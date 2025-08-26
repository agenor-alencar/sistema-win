package com.win.win_market.service;

import com.win.win_market.dto.request.PagamentoRequestDTO;
import com.win.win_market.dto.response.PagamentoResponseDTO;
import com.win.win_market.dto.mapper.PagamentoMapper;
import com.win.win_market.exceptions.ResourceNotFoundException;
import com.win.win_market.exceptions.BusinessException;
import com.win.win_market.model.Pagamento;
import com.win.win_market.model.Pedido;
import com.win.win_market.repository.PagamentoRepository;
import com.win.win_market.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;
    private final PedidoRepository pedidoRepository;
    private final PagamentoMapper pagamentoMapper;

    @Transactional
    public PagamentoResponseDTO criarPagamento(PagamentoRequestDTO requestDTO) {
        Pedido pedido = pedidoRepository.findById(requestDTO.pedidoId())
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));

        if (pagamentoRepository.existsByPedidoId(requestDTO.pedidoId())) {
            throw new BusinessException("Já existe um pagamento para este pedido");
        }

        Pagamento pagamento = new Pagamento();
        pagamento.setPedido(pedido);
        pagamento.setValor(requestDTO.valor());
        pagamento.setMetodoPagamento(requestDTO.metodoPagamento());
        pagamento.setStatus("PENDENTE");

        Pagamento pagamentoSalvo = pagamentoRepository.save(pagamento);
        return pagamentoMapper.toResponseDTO(pagamentoSalvo);
    }

    @Transactional(readOnly = true)
    public List<PagamentoResponseDTO> listarPagamentos() {
        return pagamentoRepository.findAll()
                .stream()
                .map(pagamentoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PagamentoResponseDTO> listarPagamentosPorStatus(String status) {
        return pagamentoRepository.findByStatus(status)
                .stream()
                .map(pagamentoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PagamentoResponseDTO> listarPagamentosPorMetodo(String metodo) {
        return pagamentoRepository.findByMetodoPagamento(metodo)
                .stream()
                .map(pagamentoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PagamentoResponseDTO buscarPorId(UUID id) {
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pagamento não encontrado"));
        return pagamentoMapper.toResponseDTO(pagamento);
    }

    @Transactional(readOnly = true)
    public PagamentoResponseDTO buscarPorPedidoId(UUID pedidoId) {
        Pagamento pagamento = pagamentoRepository.findByPedidoId(pedidoId)
                .orElseThrow(() -> new ResourceNotFoundException("Pagamento não encontrado para este pedido"));
        return pagamentoMapper.toResponseDTO(pagamento);
    }

    @Transactional
    public PagamentoResponseDTO confirmarPagamento(UUID id) {
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pagamento não encontrado"));

        if (!"PENDENTE".equals(pagamento.getStatus())) {
            throw new BusinessException("Pagamento não está pendente");
        }

        pagamento.setStatus("CONFIRMADO");
        pagamento.setDataPagamento(OffsetDateTime.now());

        Pagamento pagamentoAtualizado = pagamentoRepository.save(pagamento);
        return pagamentoMapper.toResponseDTO(pagamentoAtualizado);
    }

    @Transactional
    public PagamentoResponseDTO cancelarPagamento(UUID id, String motivoCancelamento) {
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pagamento não encontrado"));

        if ("CONFIRMADO".equals(pagamento.getStatus())) {
            throw new BusinessException("Não é possível cancelar um pagamento já confirmado");
        }

        pagamento.setStatus("CANCELADO");
        pagamento.setObservacoes(motivoCancelamento);

        Pagamento pagamentoAtualizado = pagamentoRepository.save(pagamento);
        return pagamentoMapper.toResponseDTO(pagamentoAtualizado);
    }

    @Transactional
    public PagamentoResponseDTO processarReembolso(UUID id) {
        Pagamento pagamento = pagamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pagamento não encontrado"));

        if (!"CONFIRMADO".equals(pagamento.getStatus())) {
            throw new BusinessException("Apenas pagamentos confirmados podem ser reembolsados");
        }

        pagamento.setStatus("REEMBOLSADO");
        pagamento.setDataReembolso(OffsetDateTime.now());

        Pagamento pagamentoAtualizado = pagamentoRepository.save(pagamento);
        return pagamentoMapper.toResponseDTO(pagamentoAtualizado);
    }
}
