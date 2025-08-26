package com.win.win_market.service;

import com.win.win_market.dto.request.ImagemProdutoRequestDTO;
import com.win.win_market.dto.response.ImagemProdutoResponseDTO;
import com.win.win_market.dto.mapper.ImagemProdutoMapper;
import com.win.win_market.exceptions.ResourceNotFoundException;
import com.win.win_market.exceptions.BusinessException;
import com.win.win_market.model.ImagemProduto;
import com.win.win_market.model.Produto;
import com.win.win_market.repository.ImagemProdutoRepository;
import com.win.win_market.repository.ProdutoRepository;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImagemProdutoService {

    private final ImagemProdutoRepository imagemProdutoRepository;
    private final ProdutoRepository produtoRepository;
    private final ImagemProdutoMapper imagemProdutoMapper;

    @Transactional
    public ImagemProdutoResponseDTO adicionarImagem(UUID produtoId, MultipartFile arquivo, Integer ordemExibicao) {
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

        if (arquivo == null || arquivo.isEmpty()) {
            throw new BusinessException("Arquivo de imagem é obrigatório");
        }

        try {
            ImagemProduto imagem = new ImagemProduto();
            imagem.setProduto(produto);
            imagem.setNomeArquivo(arquivo.getOriginalFilename());
            imagem.setTipoArquivo(arquivo.getContentType());
            imagem.setTamanhoArquivo(arquivo.getSize());
            imagem.setDadosImagem(arquivo.getBytes());
            imagem.setOrdemExibicao(ordemExibicao != null ? ordemExibicao : 1);


            ImagemProduto imagemSalva = imagemProdutoRepository.save(imagem);
            return imagemProdutoMapper.toResponseDTO(imagemSalva);
        } catch (IOException e) {
            throw new BusinessException("Erro ao processar arquivo de imagem: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<ImagemProdutoResponseDTO> listarImagensPorProduto(UUID produtoId) {
        return imagemProdutoRepository.findByProdutoIdOrderByOrdemExibicao(produtoId)
                .stream()
                .map(imagemProdutoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ImagemProdutoResponseDTO buscarPorId(UUID id) {
        ImagemProduto imagem = imagemProdutoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Imagem não encontrada"));
        return imagemProdutoMapper.toResponseDTO(imagem);
    }

    @Transactional(readOnly = true)
    public byte[] obterDadosImagem(UUID id) {
        ImagemProduto imagem = imagemProdutoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Imagem não encontrada"));
        return imagem.getDadosImagem();
    }

    @Transactional
    public ImagemProdutoResponseDTO atualizarOrdemExibicao(UUID id, Integer novaOrdem) {
        ImagemProduto imagem = imagemProdutoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Imagem não encontrada"));

        imagem.setOrdemExibicao(novaOrdem);
        ImagemProduto imagemAtualizada = imagemProdutoRepository.save(imagem);
        return imagemProdutoMapper.toResponseDTO(imagemAtualizada);
    }

    @Transactional
    public void deletarImagem(UUID id) {
        ImagemProduto imagem = imagemProdutoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Imagem não encontrada"));

        imagem.setAtivo(false);

        imagemProdutoRepository.save(imagem);
    }

    @Transactional
    public void deletarTodasImagensProduto(UUID produtoId) {
        imagemProdutoRepository.deleteByProdutoId(produtoId);
    }
}
