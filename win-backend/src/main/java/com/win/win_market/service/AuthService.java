package com.win.win_market.service;

import com.win.win_market.dto.request.LoginRequestDTO;
import com.win.win_market.dto.request.RegisterRequestDTO;
import com.win.win_market.dto.response.UsuarioResponseDTO;
import com.win.win_market.exceptions.AuthenticationException;
import com.win.win_market.exceptions.ResourceAlreadyExistsException;
import com.win.win_market.model.Usuario;
import com.win.win_market.model.Cliente;
import com.win.win_market.repository.UsuarioRepository;
import com.win.win_market.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository;
    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UsuarioResponseDTO registrarCliente(RegisterRequestDTO requestDTO) {
        // Verificar se o email já existe
        if (usuarioRepository.existsByEmail(requestDTO.email())) {
            throw new ResourceAlreadyExistsException("Email já está em uso");
        }

        // Criar usuário
        UsuarioResponseDTO usuarioResponse = usuarioService.criarUsuario(requestDTO);

        // Buscar o usuário criado para criar o cliente
        Usuario usuario = usuarioRepository.findByEmail(requestDTO.email())
                .orElseThrow(() -> new AuthenticationException("Erro ao criar usuário"));

        // Criar perfil de cliente
        Cliente cliente = new Cliente();
        cliente.setUsuario(usuario);
        clienteRepository.save(cliente);

        return usuarioResponse;
    }

    @Transactional(readOnly = true)
    public Usuario autenticarUsuario(LoginRequestDTO requestDTO) {
        Usuario usuario = usuarioRepository.findByEmail(requestDTO.email())
                .orElseThrow(() -> new AuthenticationException("Credenciais inválidas"));

        if (!passwordEncoder.matches(requestDTO.senha(), usuario.getSenha())) {
            throw new AuthenticationException("Credenciais inválidas");
        }

        // Atualizar último acesso
        usuarioService.atualizarUltimoAcesso(requestDTO.email());

        return usuario;
    }

    @Transactional(readOnly = true)
    public boolean verificarCredenciais(String email, String senha) {
        try {
            Usuario usuario = usuarioRepository.findByEmail(email)
                    .orElse(null);

            if (usuario == null) {
                return false;
            }

            return passwordEncoder.matches(senha, usuario.getSenha());
        } catch (Exception e) {
            return false;
        }
    }

    @Transactional(readOnly = true)
    public boolean emailJaExiste(String email) {
        return usuarioRepository.existsByEmail(email);
    }
}
