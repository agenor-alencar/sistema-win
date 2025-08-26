package com.win.win_market.service;

import com.win.win_market.dto.request.AvaliacaoProdutoRequestDTO;
import com.win.win_market.dto.response.AvaliacaoResponseDTO;
import com.win.win_market.dto.mapper.AvaliacaoProdutoMapper;
import com.win.win_market.exceptions.ResourceNotFoundException;
import com.win.win_market.exceptions.BusinessException;
import com.win.win_market.model.AvaliacaoProduto;
import com.win.win_market.model.Cliente;
import com.win.win_market.model.Produto;
import com.win.win_market.model.Pedido;
import com.win.win_market.repository.AvaliacaoProdutoRepository;
import com.win.win_market.repository.ClienteRepository;
import com.win.win_market.repository.ProdutoRepository;
import com.win.win_market.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvaliacaoProdutoService {

    private final AvaliacaoProdutoRepository avaliacaoProdutoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final PedidoRepository pedidoRepository;
    private final AvaliacaoProdutoMapper avaliacaoProdutoMapper;

    @Transactional
    public AvaliacaoResponseDTO criarAvaliacao(UUID clienteId, AvaliacaoProdutoRequestDTO requestDTO) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));

        Produto produto = produtoRepository.findById(requestDTO.produtoId())
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

        Pedido pedido = pedidoRepository.findById(requestDTO.pedidoId())
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));

        if (avaliacaoProdutoRepository.existsByClienteIdAndProdutoIdAndPedidoId(
                clienteId, requestDTO.produtoId(), requestDTO.pedidoId())) {
            throw new BusinessException("Cliente já avaliou este produto neste pedido");
        }

        AvaliacaoProduto avaliacao = new AvaliacaoProduto();
        avaliacao.setCliente(cliente);
        avaliacao.setProduto(produto);
        avaliacao.setPedido(pedido);
        avaliacao.setNota(requestDTO.nota());
        avaliacao.setComentario(requestDTO.comentario());

        AvaliacaoProduto avaliacaoSalva = avaliacaoProdutoRepository.save(avaliacao);
        return avaliacaoProdutoMapper.toResponseDTO(avaliacaoSalva);
    }

    @Transactional(readOnly = true)
    public List<AvaliacaoResponseDTO> listarAvaliacoesPorProduto(UUID produtoId) {
        return avaliacaoProdutoRepository.findByProdutoId(produtoId)
                .stream()
                .map(avaliacaoProdutoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AvaliacaoResponseDTO> listarAvaliacoesPorCliente(UUID clienteId) {
        return avaliacaoProdutoRepository.findByClienteId(clienteId)
                .stream()
                .map(avaliacaoProdutoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Double obterMediaAvaliacoesProduto(UUID produtoId) {
        Double media = avaliacaoProdutoRepository.findAverageNotaByProdutoId(produtoId);
        return media != null ? media : 0.0;
    }

    @Transactional(readOnly = true)
    public Long contarAvaliacoesProduto(UUID produtoId) {
        return avaliacaoProdutoRepository.countByProdutoId(produtoId);
    }

    @Transactional
    public AvaliacaoResponseDTO atualizarAvaliacao(UUID id, AvaliacaoProdutoRequestDTO requestDTO) {
        AvaliacaoProduto avaliacao = avaliacaoProdutoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avaliação não encontrada"));

        avaliacao.setNota(requestDTO.nota());
        avaliacao.setComentario(requestDTO.comentario());

        AvaliacaoProduto avaliacaoAtualizada = avaliacaoProdutoRepository.save(avaliacao);
        return avaliacaoProdutoMapper.toResponseDTO(avaliacaoAtualizada);
    }

    @Transactional
    public void deletarAvaliacao(UUID id) {
        AvaliacaoProduto avaliacao = avaliacaoProdutoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avaliação não encontrada"));

        avaliacaoProdutoRepository.delete(avaliacao);
    }
}
