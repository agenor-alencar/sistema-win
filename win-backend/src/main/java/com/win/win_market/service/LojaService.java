package com.win.win_market.service;

import com.win.win_market.entity.Loja;
import com.win.win_market.repository.LojaRepository; // Certifique-se de ter este repositório
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LojaService {

    @Autowired
    private LojaRepository lojaRepository; // Injeção de dependência do LojaRepository

    private static final Logger logger = LoggerFactory.getLogger(LojaService.class);

    public List<Loja> buscarTudo() {
        try {
            return lojaRepository.findAll();
        } catch (Exception e) {
            logger.error("Erro ao buscar todas as lojas", e);
            throw new RuntimeException("Erro ao recuperar lojas do banco de dados", e);
        }
    }

    public Loja buscarPorId(Long id) {
        try {
            return lojaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Loja não encontrada com ID: " + id));
        } catch (Exception e) {
            logger.error("Erro ao buscar loja por ID: {}", id, e);
            throw new RuntimeException("Erro ao recuperar loja do banco de dados", e);
        }
    }

    public Loja salvar(Loja loja) {
        try {
            return lojaRepository.save(loja);
        } catch (Exception e) {
            logger.error("Erro ao salvar loja: {}", loja.getNomeFantasia(), e);
            throw new RuntimeException("Erro ao persistir loja no banco de dados", e);
        }
    }

    public void deletar(Long id) {
        try {
            if (!lojaRepository.existsById(id)) {
                throw new RuntimeException("Loja não encontrada com ID: " + id);
            }
            lojaRepository.deleteById(id);
        } catch (Exception e) {
            logger.error("Erro ao deletar loja com ID: {}", id, e);
            throw new RuntimeException("Erro ao deletar loja do banco de dados", e);
        }
    }
}