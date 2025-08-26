package com.win.win_market.service;

import com.win.win_market.dto.request.PedidoCreateRequestDTO;
import com.win.win_market.dto.response.PedidoResponseDTO;
import com.win.win_market.dto.mapper.PedidoMapper;
import com.win.win_market.exceptions.ResourceNotFoundException;
import com.win.win_market.exceptions.BusinessException;
import com.win.win_market.model.*;
import com.win.win_market.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final EnderecoRepository enderecoRepository;
    private final PedidoMapper pedidoMapper;

    @Transactional
    public PedidoResponseDTO criarPedido(UUID clienteId, PedidoCreateRequestDTO requestDTO) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));

        Endereco enderecoEntrega = enderecoRepository.findById(requestDTO.enderecoEntregaId())
                .orElseThrow(() -> new ResourceNotFoundException("Endereço de entrega não encontrado"));

        // Validar se o endereço pertence ao cliente
        if (!enderecoEntrega.getUsuario().getId().equals(cliente.getUsuario().getId())) {
            throw new BusinessException("Endereço não pertence ao cliente");
        }

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setEnderecoEntrega(enderecoEntrega);
        pedido.setStatus("PENDENTE");
        pedido.setObservacoes(requestDTO.observacoes());

        // Calcular valor total e criar itens do pedido
        BigDecimal valorTotal = BigDecimal.ZERO;

        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        for (var itemRequest : requestDTO.itens()) {
            Produto produto = produtoRepository.findById(itemRequest.produtoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado: " + itemRequest.produtoId()));

            // Verificar estoque
            if (produto.getQuantidadeEstoque() < itemRequest.quantidade()) {
                throw new BusinessException("Estoque insuficiente para o produto: " + produto.getNome());
            }

            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setPedido(pedidoSalvo);
            itemPedido.setProduto(produto);
            itemPedido.setQuantidade(itemRequest.quantidade());
            itemPedido.setPrecoUnitario(produto.getPreco());

            BigDecimal subtotal = produto.getPreco().multiply(BigDecimal.valueOf(itemRequest.quantidade()));
            valorTotal = valorTotal.add(subtotal);

            itemPedidoRepository.save(itemPedido);

            // Diminuir estoque
            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - itemRequest.quantidade());
            produtoRepository.save(produto);
        }

        pedidoSalvo.setValorTotal(valorTotal);
        pedidoSalvo = pedidoRepository.save(pedidoSalvo);

        return pedidoMapper.toResponseDTO(pedidoSalvo);
    }

    @Transactional(readOnly = true)
    public List<PedidoResponseDTO> listarPedidos() {
        return pedidoRepository.findAll()
                .stream()
                .map(pedidoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PedidoResponseDTO> listarPedidosPorCliente(UUID clienteId) {
        return pedidoRepository.findByClienteIdOrderByDataPedidoDesc(clienteId)
                .stream()
                .map(pedidoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PedidoResponseDTO> listarPedidosPorStatus(String status) {
        return pedidoRepository.findByStatusOrderByDataPedidoDesc(status)
                .stream()
                .map(pedidoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PedidoResponseDTO buscarPorId(UUID id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));
        return pedidoMapper.toResponseDTO(pedido);
    }

    @Transactional
    public PedidoResponseDTO confirmarPedido(UUID id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));

        if (!"PENDENTE".equals(pedido.getStatus())) {
            throw new BusinessException("Apenas pedidos pendentes podem ser confirmados");
        }

        pedido.setStatus("CONFIRMADO");
        Pedido pedidoAtualizado = pedidoRepository.save(pedido);
        return pedidoMapper.toResponseDTO(pedidoAtualizado);
    }

    @Transactional
    public PedidoResponseDTO prepararPedido(UUID id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));

        if (!"CONFIRMADO".equals(pedido.getStatus())) {
            throw new BusinessException("Apenas pedidos confirmados podem ser preparados");
        }

        pedido.setStatus("PREPARANDO");
        Pedido pedidoAtualizado = pedidoRepository.save(pedido);
        return pedidoMapper.toResponseDTO(pedidoAtualizado);
    }

    @Transactional
    public PedidoResponseDTO marcarProntoParaEntrega(UUID id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));

        if (!"PREPARANDO".equals(pedido.getStatus())) {
            throw new BusinessException("Apenas pedidos em preparação podem ficar prontos para entrega");
        }

        pedido.setStatus("PRONTO_PARA_ENTREGA");
        Pedido pedidoAtualizado = pedidoRepository.save(pedido);
        return pedidoMapper.toResponseDTO(pedidoAtualizado);
    }

    @Transactional
    public PedidoResponseDTO marcarEmTransito(UUID id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));

        if (!"PRONTO_PARA_ENTREGA".equals(pedido.getStatus())) {
            throw new BusinessException("Apenas pedidos prontos para entrega podem ir para trânsito");
        }

        pedido.setStatus("EM_TRANSITO");
        Pedido pedidoAtualizado = pedidoRepository.save(pedido);
        return pedidoMapper.toResponseDTO(pedidoAtualizado);
    }

    @Transactional
    public PedidoResponseDTO finalizarPedido(UUID id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));

        if (!"EM_TRANSITO".equals(pedido.getStatus())) {
            throw new BusinessException("Apenas pedidos em trânsito podem ser finalizados");
        }

        pedido.setStatus("ENTREGUE");
        Pedido pedidoAtualizado = pedidoRepository.save(pedido);
        return pedidoMapper.toResponseDTO(pedidoAtualizado);
    }

    @Transactional
    public PedidoResponseDTO cancelarPedido(UUID id, String motivoCancelamento) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));

        if ("ENTREGUE".equals(pedido.getStatus()) || "CANCELADO".equals(pedido.getStatus())) {
            throw new BusinessException("Não é possível cancelar pedidos entregues ou já cancelados");
        }

        // Devolver itens ao estoque
        List<ItemPedido> itens = itemPedidoRepository.findByPedidoId(id);
        for (ItemPedido item : itens) {
            Produto produto = item.getProduto();
            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + item.getQuantidade());
            produtoRepository.save(produto);
        }

        pedido.setStatus("CANCELADO");
        pedido.setObservacoes(motivoCancelamento);
        Pedido pedidoAtualizado = pedidoRepository.save(pedido);
        return pedidoMapper.toResponseDTO(pedidoAtualizado);
    }
}
