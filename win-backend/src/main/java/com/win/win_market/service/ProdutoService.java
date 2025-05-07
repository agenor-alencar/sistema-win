package com.win.win_market.service;

import com.win.win_market.entity.Produto;
import com.win.win_market.repository.ProdutoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    private static final Logger logger = LoggerFactory.getLogger(ProdutoService.class);

    public List<Produto> BuscarTudo() {
        try {
            return produtoRepository.findAll();
        } catch (Exception e) {
            logger.error("Erro ao buscar todos os produtos", e);
            throw new RuntimeException("Erro ao recuperar usuários do banco de dados", e);
        }
    }

    public Produto BuscarPorId(Long id) {
        try {
            return produtoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + id));
        } catch (Exception e) {
            logger.error("Erro ao buscar produto por ID: {}", id, e);
            throw new RuntimeException("Erro ao recuperar produto do banco de dados", e);
        }
    }

    public Produto Salvar(Produto produto) {
        try {
            return produtoRepository.save(produto);
        } catch (Exception e) {
            logger.error("Erro ao salvar produto: {}", produto.getNome(), e);
            throw new RuntimeException("Erro ao persistir produto no banco de dados", e);
        }
    }

    public void Deletar(Long id) {
        try {
            if (!produtoRepository.existsById(id)) {
                throw new RuntimeException("Produto não encontrado com ID: " + id);
            }
            produtoRepository.deleteById(id);
        } catch (Exception e) {
            logger.error("Erro ao deletar produto com ID: {}", id, e);
            throw new RuntimeException("Erro ao deletar produto do banco de dados", e);
        }
    }


}


