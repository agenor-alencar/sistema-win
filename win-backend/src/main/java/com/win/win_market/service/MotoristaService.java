package com.win.win_market.service;

import com.win.win_market.dto.request.MotoristaCreateRequestDTO;
import com.win.win_market.dto.response.MotoristaResponseDTO;
import com.win.win_market.dto.mapper.MotoristaMapper;
import com.win.win_market.exceptions.ResourceNotFoundException;
import com.win.win_market.exceptions.BusinessException;
import com.win.win_market.model.Motorista;
import com.win.win_market.model.Usuario;
import com.win.win_market.repository.MotoristaRepository;
import com.win.win_market.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MotoristaService {

    private final MotoristaRepository motoristaRepository;
    private final UsuarioRepository usuarioRepository;
    private final MotoristaMapper motoristaMapper;

    @Transactional
    public MotoristaResponseDTO criarMotorista(MotoristaCreateRequestDTO requestDTO) {
        Usuario usuario = usuarioRepository.findById(requestDTO.usuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        if (motoristaRepository.existsByUsuarioId(requestDTO.usuarioId())) {
            throw new BusinessException("Usuário já é um motorista");
        }

        Motorista motorista = new Motorista();
        motorista.setUsuario(usuario);
        motorista.setCnh(requestDTO.cnh());
        motorista.setVeiculo(requestDTO.veiculo());
        motorista.setPlaca(requestDTO.placa());
        motorista.setStatusDisponibilidade("DISPONIVEL");

        Motorista motoristaSalvo = motoristaRepository.save(motorista);
        return motoristaMapper.toResponseDTO(motoristaSalvo);
    }

    @Transactional(readOnly = true)
    public List<MotoristaResponseDTO> listarMotoristas() {
        return motoristaRepository.findAll()
                .stream()
                .map(motoristaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MotoristaResponseDTO> listarMotoristasDisponiveis() {
        return motoristaRepository.findByStatusDisponibilidade("DISPONIVEL")
                .stream()
                .map(motoristaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MotoristaResponseDTO buscarPorId(UUID id) {
        Motorista motorista = motoristaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Motorista não encontrado"));
        return motoristaMapper.toResponseDTO(motorista);
    }

    @Transactional(readOnly = true)
    public MotoristaResponseDTO buscarPorUsuarioId(UUID usuarioId) {
        Motorista motorista = motoristaRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Motorista não encontrado"));
        return motoristaMapper.toResponseDTO(motorista);
    }

    @Transactional
    public MotoristaResponseDTO alterarDisponibilidade(UUID id, String status) {
        Motorista motorista = motoristaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Motorista não encontrado"));

        motorista.setStatusDisponibilidade(status);
        Motorista motoristaAtualizado = motoristaRepository.save(motorista);
        return motoristaMapper.toResponseDTO(motoristaAtualizado);
    }
}
