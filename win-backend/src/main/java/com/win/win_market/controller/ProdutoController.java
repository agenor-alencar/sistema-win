package com.win.win_market.controller;

import com.win.win_market.model.Produto;
import com.win.win_market.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<?> buscarTodosProdutos() {
        try{
            List<Produto> produtos = produtoService.BuscarTudo();
            if (produtos.isEmpty()) {
                return ResponseEntity.status(404).body("Nenhum produto encontrado.");
            }
            return ResponseEntity.ok(produtos);

        }catch (Exception e){
            return ResponseEntity.status(500).body("Erro ao buscar produtos: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarProdutoPorId(@PathVariable Long id) {
        try {
            Produto produto = produtoService.BuscarPorId(id);
            return ResponseEntity.ok(produto);
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Erro ao buscar produto: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> criarProduto(Produto produto) {
        try {
            Produto salvo = produtoService.Salvar(produto);
            return ResponseEntity.status(201).body(salvo);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Erro ao salvar produto: " + e.getMessage());
        }


}
}
