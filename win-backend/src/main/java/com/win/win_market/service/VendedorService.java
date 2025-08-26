package com.win.win_market.service;

import com.win.win_market.dto.request.VendedorCreateRequestDTO;
import com.win.win_market.dto.response.VendedorResponseDTO;
import com.win.win_market.dto.mapper.VendedorMapper;
import com.win.win_market.exceptions.ResourceNotFoundException;
import com.win.win_market.exceptions.BusinessException;
import com.win.win_market.model.Vendedor;
import com.win.win_market.model.Usuario;
import com.win.win_market.repository.VendedorRepository;
import com.win.win_market.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VendedorService {

    private final VendedorRepository vendedorRepository;
    private final UsuarioRepository usuarioRepository;
    private final VendedorMapper vendedorMapper;

    @Transactional
    public VendedorResponseDTO criarVendedor(VendedorCreateRequestDTO requestDTO) {
        Usuario usuario = usuarioRepository.findById(requestDTO.usuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        if (vendedorRepository.existsByUsuarioId(requestDTO.usuarioId())) {
            throw new BusinessException("Usuário já é um vendedor");
        }

        Vendedor vendedor = new Vendedor();
        vendedor.setUsuario(usuario);
        vendedor.setNomeFantasia(requestDTO.nomeFantasia());
        vendedor.setRazaoSocial(requestDTO.razaoSocial());
        vendedor.setCnpj(requestDTO.cnpj());
        vendedor.setDataAbertura(requestDTO.dataAbertura());
        vendedor.setHorarioFuncionamento(requestDTO.horarioFuncionamento());

        Vendedor vendedorSalvo = vendedorRepository.save(vendedor);
        return vendedorMapper.toResponseDTO(vendedorSalvo);
    }

    @Transactional(readOnly = true)
    public List<VendedorResponseDTO> listarVendedores() {
        return vendedorRepository.findAll()
                .stream()
                .map(vendedorMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public VendedorResponseDTO buscarPorId(UUID id) {
        Vendedor vendedor = vendedorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vendedor não encontrado"));
        return vendedorMapper.toResponseDTO(vendedor);
    }

    @Transactional(readOnly = true)
    public VendedorResponseDTO buscarPorUsuarioId(UUID usuarioId) {
        Vendedor vendedor = vendedorRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Vendedor não encontrado"));
        return vendedorMapper.toResponseDTO(vendedor);
    }

    @Transactional(readOnly = true)
    public VendedorResponseDTO buscarPorEmail(String email) {
        Vendedor vendedor = vendedorRepository.findByUsuarioEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Vendedor não encontrado"));
        return vendedorMapper.toResponseDTO(vendedor);
    }

    @Transactional(readOnly = true)
    public List<VendedorResponseDTO> buscarPorNome(String nome) {
        return vendedorRepository.findByNomeContaining(nome)
                .stream()
                .map(vendedorMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public VendedorResponseDTO buscarPorCnpj(String cnpj) {
        Vendedor vendedor = vendedorRepository.findByCnpj(cnpj)
                .orElseThrow(() -> new ResourceNotFoundException("Vendedor não encontrado"));
        return vendedorMapper.toResponseDTO(vendedor);
    }

    @Transactional(readOnly = true)
    public List<VendedorResponseDTO> buscarPorNomeFantasia(String nomeFantasia) {
        return vendedorRepository.findByNomeFantasiaContaining(nomeFantasia)
                .stream()
                .map(vendedorMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
