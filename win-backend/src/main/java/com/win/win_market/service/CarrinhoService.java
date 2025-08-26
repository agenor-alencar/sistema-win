package com.win.win_market.service;

import com.win.win_market.dto.request.CarrinhoItemRequestDTO;
import com.win.win_market.dto.response.CarrinhoResponseDTO;
import com.win.win_market.dto.mapper.CarrinhoMapper;
import com.win.win_market.exceptions.ResourceNotFoundException;
import com.win.win_market.exceptions.BusinessException;
import com.win.win_market.model.Carrinho;
import com.win.win_market.model.Cliente;
import com.win.win_market.model.ItemCarrinho;
import com.win.win_market.model.Produto;
import com.win.win_market.repository.CarrinhoRepository;
import com.win.win_market.repository.ClienteRepository;
import com.win.win_market.repository.ItemCarrinhoRepository;
import com.win.win_market.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CarrinhoService {

    private final CarrinhoRepository carrinhoRepository;
    private final ItemCarrinhoRepository itemCarrinhoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final CarrinhoMapper carrinhoMapper;

    @Transactional
    public CarrinhoResponseDTO obterCarrinho(UUID clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));

        Carrinho carrinho = carrinhoRepository.findByCliente(cliente)
                .orElseGet(() -> criarNovoCarrinho(cliente));

        return carrinhoMapper.toResponseDTO(carrinho);
    }

    @Transactional
    public CarrinhoResponseDTO adicionarItem(UUID clienteId, CarrinhoItemRequestDTO requestDTO) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));

        Produto produto = produtoRepository.findById(requestDTO.produtoId())
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

        if (produto.getQuantidadeEstoque() < requestDTO.quantidade()) {
            throw new BusinessException("Quantidade solicitada maior que o estoque disponível");
        }

        Carrinho carrinho = carrinhoRepository.findByCliente(cliente)
                .orElseGet(() -> criarNovoCarrinho(cliente));

        ItemCarrinho itemExistente = itemCarrinhoRepository
                .findByCarrinhoIdAndProdutoId(carrinho.getId(), produto.getId())
                .orElse(null);

        if (itemExistente != null) {
            int novaQuantidade = itemExistente.getQuantidade() + requestDTO.quantidade();
            if (produto.getQuantidadeEstoque() < novaQuantidade) {
                throw new BusinessException("Quantidade total no carrinho excede o estoque disponível");
            }
            itemExistente.setQuantidade(novaQuantidade);
            itemCarrinhoRepository.save(itemExistente);
        } else {
            ItemCarrinho novoItem = new ItemCarrinho();
            novoItem.setCarrinho(carrinho);
            novoItem.setProduto(produto);
            novoItem.setQuantidade(requestDTO.quantidade());
            novoItem.setPrecoUnitario(produto.getPreco());
            itemCarrinhoRepository.save(novoItem);
        }

        return carrinhoMapper.toResponseDTO(carrinho);
    }

    @Transactional
    public CarrinhoResponseDTO atualizarQuantidadeItem(UUID clienteId, UUID produtoId, Integer quantidade) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));

        Carrinho carrinho = carrinhoRepository.findByCliente(cliente)
                .orElseThrow(() -> new ResourceNotFoundException("Carrinho não encontrado"));

        ItemCarrinho item = itemCarrinhoRepository
                .findByCarrinhoIdAndProdutoId(carrinho.getId(), produtoId)
                .orElseThrow(() -> new ResourceNotFoundException("Item não encontrado no carrinho"));

        if (quantidade <= 0) {
            itemCarrinhoRepository.delete(item);
        } else {
            if (item.getProduto().getQuantidadeEstoque() < quantidade) {
                throw new BusinessException("Quantidade solicitada maior que o estoque disponível");
            }
            item.setQuantidade(quantidade);
            itemCarrinhoRepository.save(item);
        }

        return carrinhoMapper.toResponseDTO(carrinho);
    }

    @Transactional
    public CarrinhoResponseDTO removerItem(UUID clienteId, UUID produtoId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));

        Carrinho carrinho = carrinhoRepository.findByCliente(cliente)
                .orElseThrow(() -> new ResourceNotFoundException("Carrinho não encontrado"));

        ItemCarrinho item = itemCarrinhoRepository
                .findByCarrinhoIdAndProdutoId(carrinho.getId(), produtoId)
                .orElseThrow(() -> new ResourceNotFoundException("Item não encontrado no carrinho"));

        itemCarrinhoRepository.delete(item);
        return carrinhoMapper.toResponseDTO(carrinho);
    }

    @Transactional
    public void limparCarrinho(UUID clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));

        Carrinho carrinho = carrinhoRepository.findByCliente(cliente)
                .orElseThrow(() -> new ResourceNotFoundException("Carrinho não encontrado"));

        itemCarrinhoRepository.deleteByCarrinhoId(carrinho.getId());
    }

    private Carrinho criarNovoCarrinho(Cliente cliente) {
        Carrinho carrinho = new Carrinho();
        carrinho.setCliente(cliente);
        return carrinhoRepository.save(carrinho);
    }
}
