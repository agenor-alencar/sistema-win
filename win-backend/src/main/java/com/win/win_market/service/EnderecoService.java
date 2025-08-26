package com.win.win_market.service;

import com.win.win_market.dto.request.EnderecoRequestDTO;
import com.win.win_market.dto.response.EnderecoResponseDTO;
import com.win.win_market.dto.mapper.EnderecoMapper;
import com.win.win_market.exceptions.ResourceNotFoundException;
import com.win.win_market.model.Endereco;
import com.win.win_market.model.Usuario;
import com.win.win_market.repository.EnderecoRepository;
import com.win.win_market.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EnderecoMapper enderecoMapper;

    @Transactional
    public EnderecoResponseDTO criarEndereco(UUID usuarioId, EnderecoRequestDTO requestDTO) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        Endereco endereco = new Endereco();
        endereco.setUsuario(usuario);
        endereco.setNumero(requestDTO.numero());
        endereco.setComplemento(requestDTO.complemento());
        endereco.setBairro(requestDTO.bairro());
        endereco.setCidade(requestDTO.cidade());
        endereco.setEstado(requestDTO.estado());
        endereco.setCep(requestDTO.cep());
        endereco.setAtivo(true);

        Endereco enderecoSalvo = enderecoRepository.save(endereco);
        return enderecoMapper.toResponseDTO(enderecoSalvo);
    }

    @Transactional(readOnly = true)
    public List<EnderecoResponseDTO> listarEnderecosPorUsuario(UUID usuarioId) {
        return enderecoRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(enderecoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EnderecoResponseDTO> listarEnderecosAtivosPorUsuario(UUID usuarioId) {
        return enderecoRepository.findByUsuarioIdAndAtivoTrue(usuarioId)
                .stream()
                .map(enderecoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EnderecoResponseDTO buscarPorId(UUID id) {
        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado"));
        return enderecoMapper.toResponseDTO(endereco);
    }

    @Transactional
    public EnderecoResponseDTO atualizarEndereco(UUID id, EnderecoRequestDTO requestDTO) {
        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado"));

        endereco.setNumero(requestDTO.numero());
        endereco.setComplemento(requestDTO.complemento());
        endereco.setBairro(requestDTO.bairro());
        endereco.setCidade(requestDTO.cidade());
        endereco.setEstado(requestDTO.estado());
        endereco.setCep(requestDTO.cep());

        Endereco enderecoAtualizado = enderecoRepository.save(endereco);
        return enderecoMapper.toResponseDTO(enderecoAtualizado);
    }

    @Transactional
    public void desativarEndereco(UUID id) {
        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado"));

        endereco.setAtivo(false);
        enderecoRepository.save(endereco);
    }

}
