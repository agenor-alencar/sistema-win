package com.win.win_market.service;

import com.win.win_market.dto.request.ProdutoCreateRequestDTO;
import com.win.win_market.dto.request.ProdutoUpdateRequestDTO;
import com.win.win_market.dto.response.ProdutoResponseDTO;
import com.win.win_market.dto.response.ProdutoSummaryResponseDTO;
import com.win.win_market.dto.mapper.ProdutoMapper;
import com.win.win_market.exceptions.ResourceNotFoundException;
import com.win.win_market.exceptions.BusinessException;
import com.win.win_market.model.Produto;
import com.win.win_market.model.Categoria;
import com.win.win_market.model.Vendedor;
import com.win.win_market.repository.ProdutoRepository;
import com.win.win_market.repository.CategoriaRepository;
import com.win.win_market.repository.VendedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;
    private final VendedorRepository vendedorRepository;
    private final ProdutoMapper produtoMapper;

    @Transactional
    public ProdutoResponseDTO criarProduto(UUID vendedorId, ProdutoCreateRequestDTO requestDTO) {
        Vendedor vendedor = vendedorRepository.findById(vendedorId)
                .orElseThrow(() -> new ResourceNotFoundException("Vendedor não encontrado"));

        Categoria categoria = categoriaRepository.findById(requestDTO.categoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));

        Produto produto = new Produto();
        produto.setNome(requestDTO.nome());
        produto.setDescricao(requestDTO.descricao());
        produto.setPreco(requestDTO.preco());
        produto.setQuantidadeEstoque(requestDTO.quantidadeEstoque());
        produto.setAtivo(true);
        produto.setVendedor(vendedor);
        produto.setCategoria(categoria);

        Produto produtoSalvo = produtoRepository.save(produto);
        return produtoMapper.toResponseDTO(produtoSalvo);
    }

    @Transactional(readOnly = true)
    public List<ProdutoSummaryResponseDTO> listarProdutos() {
        return produtoRepository.findAll()
                .stream()
                .map(produtoMapper::toSummaryDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<ProdutoSummaryResponseDTO> listarProdutosPaginados(Pageable pageable) {
        return produtoRepository.findAll(pageable)
                .map(produtoMapper::toSummaryDTO);
    }

    @Transactional(readOnly = true)
    public List<ProdutoSummaryResponseDTO> listarProdutosAtivos() {
        return produtoRepository.findByAtivoTrue()
                .stream()
                .map(produtoMapper::toSummaryDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProdutoSummaryResponseDTO> listarProdutosPorCategoria(UUID categoriaId) {
        return produtoRepository.findByCategoriaId(categoriaId)
                .stream()
                .map(produtoMapper::toSummaryDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProdutoSummaryResponseDTO> listarProdutosPorVendedor(UUID vendedorId) {
        return produtoRepository.findByVendedorId(vendedorId)
                .stream()
                .map(produtoMapper::toSummaryDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProdutoSummaryResponseDTO> buscarProdutosPorNome(String nome) {
        return produtoRepository.findByNomeContainingIgnoreCase(nome)
                .stream()
                .map(produtoMapper::toSummaryDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProdutoSummaryResponseDTO> buscarProdutosPorFaixaPreco(BigDecimal precoMin, BigDecimal precoMax) {
        return produtoRepository.findByPrecoBetween(precoMin, precoMax)
                .stream()
                .map(produtoMapper::toSummaryDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProdutoResponseDTO buscarPorId(UUID id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
        return produtoMapper.toResponseDTO(produto);
    }

    @Transactional
    public ProdutoResponseDTO atualizarProduto(UUID id, ProdutoUpdateRequestDTO requestDTO) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

        if (requestDTO.categoriaId() != null) {
            Categoria categoria = categoriaRepository.findById(requestDTO.categoriaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));
            produto.setCategoria(categoria);
        }

        if (requestDTO.nome() != null) produto.setNome(requestDTO.nome());
        if (requestDTO.descricao() != null) produto.setDescricao(requestDTO.descricao());
        if (requestDTO.preco() != null) produto.setPreco(requestDTO.preco());
        if (requestDTO.quantidadeEstoque() != null) produto.setQuantidadeEstoque(requestDTO.quantidadeEstoque());

        Produto produtoAtualizado = produtoRepository.save(produto);
        return produtoMapper.toResponseDTO(produtoAtualizado);
    }

    @Transactional
    public void ativarProduto(UUID id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

        produto.setAtivo(true);
        produtoRepository.save(produto);
    }

    @Transactional
    public void desativarProduto(UUID id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

        produto.setAtivo(false);
        produtoRepository.save(produto);
    }

    @Transactional
    public void atualizarEstoque(UUID id, Integer novaQuantidade) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

        if (novaQuantidade < 0) {
            throw new BusinessException("Quantidade de estoque não pode ser negativa");
        }

        produto.setQuantidadeEstoque(novaQuantidade);
        produtoRepository.save(produto);
    }

    @Transactional
    public void diminuirEstoque(UUID id, Integer quantidade) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

        if (produto.getQuantidadeEstoque() < quantidade) {
            throw new BusinessException("Estoque insuficiente");
        }

        produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - quantidade);
        produtoRepository.save(produto);
    }

    @Transactional
    public void deletarProduto(UUID id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

        produtoRepository.delete(produto);
    }
}
