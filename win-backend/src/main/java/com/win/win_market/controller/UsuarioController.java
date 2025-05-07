package com.win.win_market.controller;

import com.win.win_market.entity.Usuario;
import com.win.win_market.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<?> buscarTodosUsuarios() {
        try {
            List<Usuario> usuarios = usuarioService.BuscarTudo();
            if (usuarios.isEmpty()) {
                return ResponseEntity.status(404).body("Nenhum usuário encontrado.");
            }
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao buscar usuários: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarUsuarioPorId(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.BuscarPorId(id);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Erro ao buscar usuário: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> criarUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario salvo = usuarioService.Salvar(usuario);
            return ResponseEntity.status(201).body(salvo);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Erro ao salvar usuário: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioAtualizado) {
        try {
            Usuario existente = usuarioService.BuscarPorId(id);
            existente.setNome(usuarioAtualizado.getNome());
            existente.setEmail(usuarioAtualizado.getEmail());
            existente.setSenha(usuarioAtualizado.getSenha());
            existente.setTelefone(usuarioAtualizado.getTelefone());
            existente.setTipoUsuario(usuarioAtualizado.getTipoUsuario());

            Usuario atualizado = usuarioService.Salvar(existente);
            return ResponseEntity.ok(atualizado);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Erro ao atualizar usuário: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarUsuario(@PathVariable Long id) {
        try {
            usuarioService.Deletar(id);
            return ResponseEntity.ok("Usuário deletado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Erro ao deletar usuário: " + e.getMessage());
        }
    }
}
