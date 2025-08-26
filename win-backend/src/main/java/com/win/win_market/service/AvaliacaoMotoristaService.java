package com.win.win_market.service;

import com.win.win_market.dto.request.AvaliacaoMotoristaRequestDTO;
import com.win.win_market.dto.response.AvaliacaoResponseDTO;
import com.win.win_market.dto.mapper.AvaliacaoMotoristaMapper;
import com.win.win_market.exceptions.ResourceNotFoundException;
import com.win.win_market.exceptions.BusinessException;
import com.win.win_market.model.AvaliacaoMotorista;
import com.win.win_market.model.Cliente;
import com.win.win_market.model.Motorista;
import com.win.win_market.model.Pedido;
import com.win.win_market.repository.AvaliacaoMotoristaRepository;
import com.win.win_market.repository.ClienteRepository;
import com.win.win_market.repository.MotoristaRepository;
import com.win.win_market.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvaliacaoMotoristaService {

    private final AvaliacaoMotoristaRepository avaliacaoMotoristaRepository;
    private final ClienteRepository clienteRepository;
    private final MotoristaRepository motoristaRepository;
    private final PedidoRepository pedidoRepository;
    private final AvaliacaoMotoristaMapper avaliacaoMotoristaMapper;

    @Transactional
    public AvaliacaoResponseDTO criarAvaliacao(UUID clienteId, AvaliacaoMotoristaRequestDTO requestDTO) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));

        Motorista motorista = motoristaRepository.findById(requestDTO.motoristaId())
                .orElseThrow(() -> new ResourceNotFoundException("Motorista não encontrado"));

        Pedido pedido = pedidoRepository.findById(requestDTO.pedidoId())
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));

        if (avaliacaoMotoristaRepository.existsByClienteIdAndMotoristaIdAndPedidoId(
                clienteId, requestDTO.motoristaId(), requestDTO.pedidoId())) {
            throw new BusinessException("Cliente já avaliou este motorista neste pedido");
        }

        AvaliacaoMotorista avaliacao = new AvaliacaoMotorista();
        avaliacao.setCliente(cliente);
        avaliacao.setMotorista(motorista);
        avaliacao.setPedido(pedido);
        avaliacao.setNota(requestDTO.nota());
        avaliacao.setComentario(requestDTO.comentario());

        AvaliacaoMotorista avaliacaoSalva = avaliacaoMotoristaRepository.save(avaliacao);
        return avaliacaoMotoristaMapper.toResponseDTO(avaliacaoSalva);
    }

    @Transactional(readOnly = true)
    public List<AvaliacaoResponseDTO> listarAvaliacoesPorMotorista(UUID motoristaId) {
        return avaliacaoMotoristaRepository.findByMotoristaId(motoristaId)
                .stream()
                .map(avaliacaoMotoristaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AvaliacaoResponseDTO> listarAvaliacoesPorCliente(UUID clienteId) {
        return avaliacaoMotoristaRepository.findByClienteId(clienteId)
                .stream()
                .map(avaliacaoMotoristaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Double obterMediaAvaliacoesMotorista(UUID motoristaId) {
        Double media = avaliacaoMotoristaRepository.findAverageNotaByMotoristaId(motoristaId);
        return media != null ? media : 0.0;
    }

    @Transactional(readOnly = true)
    public Long contarAvaliacoesMotorista(UUID motoristaId) {
        return avaliacaoMotoristaRepository.countByMotoristaId(motoristaId);
    }

    @Transactional
    public AvaliacaoResponseDTO atualizarAvaliacao(UUID id, AvaliacaoMotoristaRequestDTO requestDTO) {
        AvaliacaoMotorista avaliacao = avaliacaoMotoristaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avaliação não encontrada"));

        avaliacao.setNota(requestDTO.nota());
        avaliacao.setComentario(requestDTO.comentario());

        AvaliacaoMotorista avaliacaoAtualizada = avaliacaoMotoristaRepository.save(avaliacao);
        return avaliacaoMotoristaMapper.toResponseDTO(avaliacaoAtualizada);
    }

    @Transactional
    public void deletarAvaliacao(UUID id) {
        AvaliacaoMotorista avaliacao = avaliacaoMotoristaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avaliação não encontrada"));

        avaliacaoMotoristaRepository.delete(avaliacao);
    }
}
