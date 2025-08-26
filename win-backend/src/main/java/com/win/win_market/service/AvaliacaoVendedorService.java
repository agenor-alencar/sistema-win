package com.win.win_market.service;

import com.win.win_market.dto.request.AvaliacaoVendedorRequestDTO;
import com.win.win_market.dto.response.AvaliacaoResponseDTO;
import com.win.win_market.dto.mapper.AvaliacaoVendedorMapper;
import com.win.win_market.exceptions.ResourceNotFoundException;
import com.win.win_market.exceptions.BusinessException;
import com.win.win_market.model.AvaliacaoVendedor;
import com.win.win_market.model.Cliente;
import com.win.win_market.model.Vendedor;
import com.win.win_market.model.Pedido;
import com.win.win_market.repository.AvaliacaoVendedorRepository;
import com.win.win_market.repository.ClienteRepository;
import com.win.win_market.repository.VendedorRepository;
import com.win.win_market.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvaliacaoVendedorService {

    private final AvaliacaoVendedorRepository avaliacaoVendedorRepository;
    private final ClienteRepository clienteRepository;
    private final VendedorRepository vendedorRepository;
    private final PedidoRepository pedidoRepository;
    private final AvaliacaoVendedorMapper avaliacaoVendedorMapper;

    @Transactional
    public AvaliacaoResponseDTO criarAvaliacao(UUID clienteId, AvaliacaoVendedorRequestDTO requestDTO) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));

        Vendedor vendedor = vendedorRepository.findById(requestDTO.vendedorId())
                .orElseThrow(() -> new ResourceNotFoundException("Vendedor não encontrado"));

        Pedido pedido = pedidoRepository.findById(requestDTO.pedidoId())
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));

        if (avaliacaoVendedorRepository.existsByClienteIdAndVendedorIdAndPedidoId(
                clienteId, requestDTO.vendedorId(), requestDTO.pedidoId())) {
            throw new BusinessException("Cliente já avaliou este vendedor neste pedido");
        }

        AvaliacaoVendedor avaliacao = new AvaliacaoVendedor();
        avaliacao.setCliente(cliente);
        avaliacao.setVendedor(vendedor);
        avaliacao.setPedido(pedido);
        avaliacao.setNota(requestDTO.nota());
        avaliacao.setComentario(requestDTO.comentario());

        AvaliacaoVendedor avaliacaoSalva = avaliacaoVendedorRepository.save(avaliacao);
        return avaliacaoVendedorMapper.toResponseDTO(avaliacaoSalva);
    }

    @Transactional(readOnly = true)
    public List<AvaliacaoResponseDTO> listarAvaliacoesPorVendedor(UUID vendedorId) {
        return avaliacaoVendedorRepository.findByVendedorId(vendedorId)
                .stream()
                .map(avaliacaoVendedorMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AvaliacaoResponseDTO> listarAvaliacoesPorCliente(UUID clienteId) {
        return avaliacaoVendedorRepository.findByClienteId(clienteId)
                .stream()
                .map(avaliacaoVendedorMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Double obterMediaAvaliacoesVendedor(UUID vendedorId) {
        Double media = avaliacaoVendedorRepository.findAverageNotaByVendedorId(vendedorId);
        return media != null ? media : 0.0;
    }

    @Transactional(readOnly = true)
    public Long contarAvaliacoesVendedor(UUID vendedorId) {
        return avaliacaoVendedorRepository.countByVendedorId(vendedorId);
    }

    @Transactional
    public AvaliacaoResponseDTO atualizarAvaliacao(UUID id, AvaliacaoVendedorRequestDTO requestDTO) {
        AvaliacaoVendedor avaliacao = avaliacaoVendedorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avaliação não encontrada"));

        avaliacao.setNota(requestDTO.nota());
        avaliacao.setComentario(requestDTO.comentario());

        AvaliacaoVendedor avaliacaoAtualizada = avaliacaoVendedorRepository.save(avaliacao);
        return avaliacaoVendedorMapper.toResponseDTO(avaliacaoAtualizada);
    }

    @Transactional
    public void deletarAvaliacao(UUID id) {
        AvaliacaoVendedor avaliacao = avaliacaoVendedorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avaliação não encontrada"));

        avaliacaoVendedorRepository.delete(avaliacao);
    }
}
