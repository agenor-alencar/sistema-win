package com.win.win_market.service;

import com.win.win_market.entity.Usuario;
import com.win.win_market.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    public List<Usuario> BuscarTudo() {
        try {
            return usuarioRepository.findAll();

        } catch (Exception e) {
            logger.error("Erro ao buscar todos os usuários", e);
            throw new RuntimeException("Erro ao recuperar usuários do banco de dados", e);
        }
    }

    public Usuario BuscarPorId(Long id) {
        try {
            return usuarioRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));
        } catch (Exception e) {
            logger.error("Erro ao buscar usuário por ID: {}", id, e);
            throw new RuntimeException("Erro ao recuperar usuário do banco de dados", e);
        }
    }

    public Usuario Salvar(Usuario usuario) {
        try {
            return usuarioRepository.save(usuario);
        } catch (Exception e) {
            logger.error("Erro ao salvar usuário: {}", usuario.getEmail(), e);
            throw new RuntimeException("Erro ao persistir usuário no banco de dados", e);
        }
    }

    public void Deletar(Long id) {
        try {
            if (!usuarioRepository.existsById(id)) {
                throw new RuntimeException("Usuário não encontrado com ID: " + id);
            }
            usuarioRepository.deleteById(id);
        } catch (Exception e) {
            logger.error("Erro ao deletar usuário com ID: {}", id, e);
            throw new RuntimeException("Erro ao deletar usuário do banco de dados", e);
        }
    }
}
