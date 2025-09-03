package com.win.win_market.controller;

import com.win.win_market.service.LojaService;
import com.win.win_market.dto.request.LojaCreateRequestDTO;
import com.win.win_market.dto.response.LojaResponseDTO;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loja")
public class LojaController {
	private final LojaService lojaService;

	public LojaController(LojaService lojaService) {
		this.lojaService = lojaService;
	}

	@PostMapping
	public ResponseEntity<LojaResponseDTO> criarLoja(@RequestBody LojaCreateRequestDTO requestDTO) {
		LojaResponseDTO response = lojaService.criarLoja(requestDTO);
		return ResponseEntity.ok(response);
	}

	@GetMapping
	public ResponseEntity<List<LojaResponseDTO>> listarLojas() {
		List<LojaResponseDTO> lojas = lojaService.listarLojas();
		return ResponseEntity.ok(lojas);
	}

	@GetMapping("/ativas")
	public ResponseEntity<List<LojaResponseDTO>> listarLojasAtivas() {
		List<LojaResponseDTO> lojas = lojaService.listarLojasAtivas();
		return ResponseEntity.ok(lojas);
	}

	@GetMapping("/{id}")
	public ResponseEntity<LojaResponseDTO> buscarPorId(@PathVariable UUID id) {
		LojaResponseDTO loja = lojaService.buscarPorId(id);
		return ResponseEntity.ok(loja);
	}

	@GetMapping("/vendedor/{vendedorId}")
	public ResponseEntity<LojaResponseDTO> buscarPorVendedorId(@PathVariable UUID vendedorId) {
		LojaResponseDTO loja = lojaService.buscarPorVendedorId(vendedorId);
		return ResponseEntity.ok(loja);
	}

	@GetMapping("/buscar")
	public ResponseEntity<List<LojaResponseDTO>> buscarPorNome(@RequestParam String nome) {
		List<LojaResponseDTO> lojas = lojaService.buscarPorNome(nome);
		return ResponseEntity.ok(lojas);
	}

	@PutMapping("/{id}")
	public ResponseEntity<LojaResponseDTO> atualizarLoja(@PathVariable UUID id, @RequestBody LojaCreateRequestDTO requestDTO) {
		LojaResponseDTO loja = lojaService.atualizarLoja(id, requestDTO);
		return ResponseEntity.ok(loja);
	}

	@PatchMapping("/{id}/ativar")
	public ResponseEntity<LojaResponseDTO> ativarLoja(@PathVariable UUID id) {
		LojaResponseDTO loja = lojaService.ativarLoja(id);
		return ResponseEntity.ok(loja);
	}

	@PatchMapping("/{id}/desativar")
	public ResponseEntity<LojaResponseDTO> desativarLoja(@PathVariable UUID id) {
		LojaResponseDTO loja = lojaService.desativarLoja(id);
		return ResponseEntity.ok(loja);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarLoja(@PathVariable UUID id) {
		lojaService.deletarLoja(id);
		return ResponseEntity.noContent().build();
	}

}
