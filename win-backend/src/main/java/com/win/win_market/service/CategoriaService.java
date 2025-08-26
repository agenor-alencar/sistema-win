package com.win.win_market.service;

import com.win.win_market.dto.request.CategoriaCreateRequestDTO;
import com.win.win_market.dto.response.CategoriaResponseDTO;
import com.win.win_market.dto.mapper.CategoriaMapper;
import com.win.win_market.exceptions.ResourceNotFoundException;
import com.win.win_market.exceptions.ResourceAlreadyExistsException;
import com.win.win_market.model.Categoria;
import com.win.win_market.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;

    @Transactional
    public CategoriaResponseDTO criarCategoria(CategoriaCreateRequestDTO requestDTO) {
        if (categoriaRepository.existsByNome(requestDTO.nome())) {
            throw new ResourceAlreadyExistsException("Categoria com este nome já existe");
        }

        Categoria categoria = new Categoria();
        categoria.setNome(requestDTO.nome());
        categoria.setDescricao(requestDTO.descricao());

        Categoria categoriaSalva = categoriaRepository.save(categoria);
        return categoriaMapper.toResponseDTO(categoriaSalva);
    }

    @Transactional
    public CategoriaResponseDTO criarSubCategoria(UUID categoriaPaiId, CategoriaCreateRequestDTO requestDTO) {
        if (categoriaRepository.existsByNome(requestDTO.nome())) {
            throw new ResourceAlreadyExistsException("Categoria com este nome já existe");
        }

        Categoria categoriaPai = categoriaRepository.findById(categoriaPaiId)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria pai não encontrada"));

        Categoria categoria = new Categoria();
        categoria.setNome(requestDTO.nome());
        categoria.setDescricao(requestDTO.descricao());
        categoria.setCategoriaPai(categoriaPai);

        Categoria categoriaSalva = categoriaRepository.save(categoria);
        return categoriaMapper.toResponseDTO(categoriaSalva);
    }

    @Transactional(readOnly = true)
    public List<CategoriaResponseDTO> listarCategorias() {
        return categoriaRepository.findAll()
                .stream()
                .map(categoriaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CategoriaResponseDTO> listarCategoriasPrincipais() {
        return categoriaRepository.findByCategoriaPaiIsNull()
                .stream()
                .map(categoriaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CategoriaResponseDTO> listarSubCategorias(UUID categoriaPaiId) {
        return categoriaRepository.findByCategoriaPaiId(categoriaPaiId)
                .stream()
                .map(categoriaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoriaResponseDTO buscarPorId(UUID id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));
        return categoriaMapper.toResponseDTO(categoria);
    }

    @Transactional(readOnly = true)
    public List<CategoriaResponseDTO> buscarPorNome(String nome) {
        return categoriaRepository.findByNomeContaining(nome)
                .stream()
                .map(categoriaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public CategoriaResponseDTO atualizarCategoria(UUID id, CategoriaCreateRequestDTO requestDTO) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));

        if (!categoria.getNome().equals(requestDTO.nome()) &&
            categoriaRepository.existsByNome(requestDTO.nome())) {
            throw new ResourceAlreadyExistsException("Categoria com este nome já existe");
        }

        categoria.setNome(requestDTO.nome());
        categoria.setDescricao(requestDTO.descricao());

        Categoria categoriaAtualizada = categoriaRepository.save(categoria);
        return categoriaMapper.toResponseDTO(categoriaAtualizada);
    }

    @Transactional
    public void deletarCategoria(UUID id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));

        categoria.setAtivo(false);
        categoriaRepository.save(categoria);
    }
}
