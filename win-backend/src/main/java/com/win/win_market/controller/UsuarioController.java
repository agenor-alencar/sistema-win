package com.win.win_market.controller;

import com.win.win_market.dto.request.RegisterRequestDTO;
import com.win.win_market.dto.response.UsuarioResponseDTO;
import com.win.win_market.model.Usuario;
import com.win.win_market.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {
	private final UsuarioService usuarioService;

	public UsuarioController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	@PostMapping
	public ResponseEntity<UsuarioResponseDTO> criarUsuario(@RequestBody RegisterRequestDTO requestDTO) {
		UsuarioResponseDTO response = usuarioService.criarUsuario(requestDTO);
		return ResponseEntity.ok(response);
	}

	@GetMapping
	public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {
		List<UsuarioResponseDTO> usuarios = usuarioService.listarUsuarios();
		return ResponseEntity.ok(usuarios);
	}

	@GetMapping("/{id}")
	public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable UUID id) {
		UsuarioResponseDTO usuario = usuarioService.buscarPorId(id);
		return ResponseEntity.ok(usuario);
	}

	@GetMapping("/email/{email}")
	public ResponseEntity<UsuarioResponseDTO> buscarPorEmail(@PathVariable String email) {
		UsuarioResponseDTO usuario = usuarioService.buscarPorEmail(email);
		return ResponseEntity.ok(usuario);
	}

	@PutMapping("/{id}")
	public ResponseEntity<UsuarioResponseDTO> atualizarUsuario(@PathVariable UUID id, @RequestBody RegisterRequestDTO requestDTO) {
		UsuarioResponseDTO usuario = usuarioService.atualizarUsuario(id, requestDTO);
		return ResponseEntity.ok(usuario);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarUsuario(@PathVariable UUID id) {
		usuarioService.deletarUsuario(id);
		return ResponseEntity.noContent().build();
	}
}
