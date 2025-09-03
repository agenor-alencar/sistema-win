package com.win.win_market.controller;

import com.win.win_market.dto.response.ImagemProdutoResponseDTO;
import com.win.win_market.service.ImagemProdutoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/imagem-produto")
public class ImagemProdutoController {
    private final ImagemProdutoService imagemProdutoService;

    public ImagemProdutoController(ImagemProdutoService imagemProdutoService) {
        this.imagemProdutoService = imagemProdutoService;
    }

    @PostMapping("/produto/{produtoId}")
    public ResponseEntity<ImagemProdutoResponseDTO> adicionarImagem(@PathVariable UUID produtoId, @RequestParam("arquivo") MultipartFile arquivo, @RequestParam(required = false) Integer ordemExibicao) {
        ImagemProdutoResponseDTO response = imagemProdutoService.adicionarImagem(produtoId, arquivo, ordemExibicao);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/produto/{produtoId}")
    public ResponseEntity<List<ImagemProdutoResponseDTO>> listarImagensPorProduto(@PathVariable UUID produtoId) {
        List<ImagemProdutoResponseDTO> imagens = imagemProdutoService.listarImagensPorProduto(produtoId);
        return ResponseEntity.ok(imagens);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImagemProdutoResponseDTO> buscarPorId(@PathVariable UUID id) {
        ImagemProdutoResponseDTO imagem = imagemProdutoService.buscarPorId(id);
        return ResponseEntity.ok(imagem);
    }

    @GetMapping("/{id}/dados")
    public ResponseEntity<byte[]> obterDadosImagem(@PathVariable UUID id) {
        byte[] dados = imagemProdutoService.obterDadosImagem(id);
        return ResponseEntity.ok(dados);
    }

    @PatchMapping("/{id}/ordem")
    public ResponseEntity<ImagemProdutoResponseDTO> atualizarOrdemExibicao(@PathVariable UUID id, @RequestParam Integer novaOrdem) {
        ImagemProdutoResponseDTO imagem = imagemProdutoService.atualizarOrdemExibicao(id, novaOrdem);
        return ResponseEntity.ok(imagem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarImagem(@PathVariable UUID id) {
        imagemProdutoService.deletarImagem(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/produto/{produtoId}")
    public ResponseEntity<Void> deletarTodasImagensProduto(@PathVariable UUID produtoId) {
        imagemProdutoService.deletarTodasImagensProduto(produtoId);
        return ResponseEntity.noContent().build();
    }
}
