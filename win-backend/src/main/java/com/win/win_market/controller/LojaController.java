package com.win.win_market.controller;

import com.win.win_market.model.Loja;
import com.win.win_market.service.LojaService; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loja")
public class LojaController {

    @Autowired
    private LojaService lojaService; 

    @GetMapping
    public ResponseEntity<?> buscarTodasLojas() {
        try {
            List<Loja> lojas = lojaService.buscarTudo(); // Mesmo metodo usado no ProdutoService
            if (lojas.isEmpty()) {
                return ResponseEntity.status(404).body("Nenhuma loja encontrada.");
            }
            return ResponseEntity.ok(lojas);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao buscar lojas: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarLojaPorId(@PathVariable Long id) {
        try {
            Loja loja = lojaService.buscarPorId(id); 
            return ResponseEntity.ok(loja);
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Erro ao buscar loja: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> criarLoja(@RequestBody Loja loja) {
        try {
            Loja salvo = lojaService.salvar(loja); // Método para salvar uma nova loja
            return ResponseEntity.status(201).body(salvo);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Erro ao salvar loja: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarLoja(@PathVariable Long id, @RequestBody Loja lojaAtualizada) {
        try {
            Loja existente = lojaService.buscarPorId(id);
          
            existente.setNomeFantasia(lojaAtualizada.getNomeFantasia());
            existente.setRazaoSocial(lojaAtualizada.getRazaoSocial());
            existente.setCnpj(lojaAtualizada.getCnpj());
            existente.setTelefone(lojaAtualizada.getTelefone());
            existente.setEmail(lojaAtualizada.getEmail());
            existente.setHorarioFuncionamento(lojaAtualizada.getHorarioFuncionamento());
        

            Loja atualizada = lojaService.salvar(existente);
            return ResponseEntity.ok(atualizada);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Erro ao atualizar loja: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarLoja(@PathVariable Long id) {
        try {
            lojaService.deletar(id); 
            return ResponseEntity.ok("Loja deletada com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Erro ao deletar loja: " + e.getMessage());
        }
    }
}
