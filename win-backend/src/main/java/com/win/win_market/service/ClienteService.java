package com.win.win_market.service;

import com.win.win_market.dto.response.ClienteResponseDTO;
import com.win.win_market.dto.mapper.ClienteMapper;
import com.win.win_market.exceptions.ResourceNotFoundException;
import com.win.win_market.model.Cliente;
import com.win.win_market.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> listarClientes() {
        return clienteRepository.findAll()
                .stream()
                .map(clienteMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ClienteResponseDTO buscarPorId(UUID id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
        return clienteMapper.toResponseDTO(cliente);
    }

    @Transactional(readOnly = true)
    public ClienteResponseDTO buscarPorUsuarioId(UUID usuarioId) {
        Cliente cliente = clienteRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
        return clienteMapper.toResponseDTO(cliente);
    }

    @Transactional(readOnly = true)
    public ClienteResponseDTO buscarPorEmail(String email) {
        Cliente cliente = clienteRepository.findByUsuarioEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
        return clienteMapper.toResponseDTO(cliente);
    }

    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> buscarPorNome(String nome) {
        return clienteRepository.findByNomeContaining(nome)
                .stream()
                .map(clienteMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
