package com.win.win_market.service;

import com.win.win_market.dto.request.RegisterRequestDTO;
import com.win.win_market.dto.response.UsuarioResponseDTO;
import com.win.win_market.dto.mapper.UsuarioMapper;
import com.win.win_market.exceptions.ResourceNotFoundException;
import com.win.win_market.exceptions.ResourceAlreadyExistsException;
import com.win.win_market.model.Usuario;
import com.win.win_market.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UsuarioResponseDTO criarUsuario(RegisterRequestDTO requestDTO) {
        if (usuarioRepository.existsByEmail(requestDTO.email())) {
            throw new ResourceAlreadyExistsException("Email já está em uso");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(requestDTO.nome());
        usuario.setEmail(requestDTO.email());
        usuario.setSenha(passwordEncoder.encode(requestDTO.senha()));
        usuario.setTelefone(requestDTO.telefone());

        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        return usuarioMapper.toResponseDTO(usuarioSalvo);
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> listarUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuarioMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UsuarioResponseDTO buscarPorId(UUID id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        return usuarioMapper.toResponseDTO(usuario);
    }

    @Transactional(readOnly = true)
    public UsuarioResponseDTO buscarPorEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        return usuarioMapper.toResponseDTO(usuario);
    }

    @Transactional(readOnly = true)
    public Optional<Usuario> buscarUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Transactional
    public UsuarioResponseDTO atualizarUsuario(UUID id, RegisterRequestDTO requestDTO) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        if (!usuario.getEmail().equals(requestDTO.email()) &&
            usuarioRepository.existsByEmail(requestDTO.email())) {
            throw new ResourceAlreadyExistsException("Email já está em uso");
        }

        usuario.setNome(requestDTO.nome());
        usuario.setEmail(requestDTO.email());
        usuario.setTelefone(requestDTO.telefone());

        if (requestDTO.senha() != null && !requestDTO.senha().isEmpty()) {
            usuario.setSenha(passwordEncoder.encode(requestDTO.senha()));
        }

        Usuario usuarioAtualizado = usuarioRepository.save(usuario);
        return usuarioMapper.toResponseDTO(usuarioAtualizado);
    }

    @Transactional
    public void deletarUsuario(UUID id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        usuario.setAtivo(false);

        usuarioRepository.save(usuario);
    }

    @Transactional
    public void atualizarUltimoAcesso(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        usuario.setUltimoAcesso(java.time.OffsetDateTime.now());
        usuarioRepository.save(usuario);
    }
}
