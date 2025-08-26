package com.win.win_market.service;

import com.win.win_market.dto.request.LojaCreateRequestDTO;
import com.win.win_market.dto.response.LojaResponseDTO;
import com.win.win_market.dto.mapper.LojaMapper;
import com.win.win_market.exceptions.ResourceNotFoundException;
import com.win.win_market.exceptions.ResourceAlreadyExistsException;
import com.win.win_market.model.Loja;
import com.win.win_market.model.Vendedor;
import com.win.win_market.repository.LojaRepository;
import com.win.win_market.repository.VendedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LojaService {

    private final LojaRepository lojaRepository;
    private final VendedorRepository vendedorRepository;
    private final LojaMapper lojaMapper;

    @Transactional
    public LojaResponseDTO criarLoja(LojaCreateRequestDTO requestDTO) {
        Vendedor vendedor = vendedorRepository.findById(requestDTO.vendedorId())
                .orElseThrow(() -> new ResourceNotFoundException("Vendedor não encontrado"));

        if (lojaRepository.existsByVendedorId(requestDTO.vendedorId())) {
            throw new ResourceAlreadyExistsException("Vendedor já possui uma loja");
        }

        Loja loja = new Loja();
        loja.setNomeFantasia(requestDTO.nomeFantasia());
        loja.setDescricao(requestDTO.descricao());
        loja.setAtivo(true);

        Loja lojaSalva = lojaRepository.save(loja);
        return lojaMapper.toResponseDTO(lojaSalva);
    }

    @Transactional(readOnly = true)
    public List<LojaResponseDTO> listarLojas() {
        return lojaRepository.findAll()
                .stream()
                .map(lojaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<LojaResponseDTO> listarLojasAtivas() {
        return lojaRepository.findByAtivaTrue()
                .stream()
                .map(lojaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public LojaResponseDTO buscarPorId(UUID id) {
        Loja loja = lojaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loja não encontrada"));
        return lojaMapper.toResponseDTO(loja);
    }

    @Transactional(readOnly = true)
    public LojaResponseDTO buscarPorVendedorId(UUID vendedorId) {
        Loja loja = lojaRepository.findByVendedorId(vendedorId)
                .orElseThrow(() -> new ResourceNotFoundException("Loja não encontrada para este vendedor"));
        return lojaMapper.toResponseDTO(loja);
    }

    @Transactional(readOnly = true)
    public List<LojaResponseDTO> buscarPorNome(String nome) {
        return lojaRepository.findByNomeFantasiaContainingIgnoreCase(nome)
                .stream()
                .map(lojaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public LojaResponseDTO atualizarLoja(UUID id, LojaCreateRequestDTO requestDTO) {
        Loja loja = lojaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loja não encontrada"));

        loja.setNomeFantasia(requestDTO.nomeFantasia());
        loja.setDescricao(requestDTO.descricao());

        Loja lojaAtualizada = lojaRepository.save(loja);
        return lojaMapper.toResponseDTO(lojaAtualizada);
    }

    @Transactional
    public LojaResponseDTO ativarLoja(UUID id) {
        Loja loja = lojaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loja não encontrada"));

        loja.setAtivo(true);
        Loja lojaAtualizada = lojaRepository.save(loja);
        return lojaMapper.toResponseDTO(lojaAtualizada);
    }

    @Transactional
    public LojaResponseDTO desativarLoja(UUID id) {
        Loja loja = lojaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loja não encontrada"));

        loja.setAtivo(false);
        Loja lojaAtualizada = lojaRepository.save(loja);
        return lojaMapper.toResponseDTO(lojaAtualizada);
    }

    @Transactional
    public void deletarLoja(UUID id) {
        Loja loja = lojaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loja não encontrada"));

        loja.setAtivo(false);

        lojaRepository.save(loja);
    }
}