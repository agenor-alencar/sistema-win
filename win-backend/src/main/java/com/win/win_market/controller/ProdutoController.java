package com.win.win_market.controller;

import com.win.win_market.dto.request.ProdutoCreateRequestDTO;
import com.win.win_market.dto.request.ProdutoUpdateRequestDTO;
import com.win.win_market.dto.response.ProdutoResponseDTO;
import com.win.win_market.dto.response.ProdutoSummaryResponseDTO;
import com.win.win_market.service.ProdutoService;
import java.util.List;
import java.util.UUID;
import java.math.BigDecimal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/produto")
public class ProdutoController {
	private final ProdutoService produtoService;

	public ProdutoController(ProdutoService produtoService) {
		this.produtoService = produtoService;
	}

	@PostMapping("/{vendedorId}")
	public ResponseEntity<ProdutoResponseDTO> criarProduto(@PathVariable UUID vendedorId, @RequestBody ProdutoCreateRequestDTO requestDTO) {
		ProdutoResponseDTO response = produtoService.criarProduto(vendedorId, requestDTO);
		return ResponseEntity.ok(response);
	}

	@GetMapping
	public ResponseEntity<List<ProdutoSummaryResponseDTO>> listarProdutos() {
		List<ProdutoSummaryResponseDTO> produtos = produtoService.listarProdutos();
		return ResponseEntity.ok(produtos);
	}

	@GetMapping("/ativos")
	public ResponseEntity<List<ProdutoSummaryResponseDTO>> listarProdutosAtivos() {
		List<ProdutoSummaryResponseDTO> produtos = produtoService.listarProdutosAtivos();
		return ResponseEntity.ok(produtos);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProdutoResponseDTO> buscarPorId(@PathVariable UUID id) {
		ProdutoResponseDTO produto = produtoService.buscarPorId(id);
		return ResponseEntity.ok(produto);
	}

	@GetMapping("/categoria/{categoriaId}")
	public ResponseEntity<List<ProdutoSummaryResponseDTO>> listarPorCategoria(@PathVariable UUID categoriaId) {
		List<ProdutoSummaryResponseDTO> produtos = produtoService.listarProdutosPorCategoria(categoriaId);
		return ResponseEntity.ok(produtos);
	}

	@GetMapping("/vendedor/{vendedorId}")
	public ResponseEntity<List<ProdutoSummaryResponseDTO>> listarPorVendedor(@PathVariable UUID vendedorId) {
		List<ProdutoSummaryResponseDTO> produtos = produtoService.listarProdutosPorVendedor(vendedorId);
		return ResponseEntity.ok(produtos);
	}

	@GetMapping("/buscar")
	public ResponseEntity<List<ProdutoSummaryResponseDTO>> buscarPorNome(@RequestParam String nome) {
		List<ProdutoSummaryResponseDTO> produtos = produtoService.buscarProdutosPorNome(nome);
		return ResponseEntity.ok(produtos);
	}

	@GetMapping("/faixa-preco")
	public ResponseEntity<List<ProdutoSummaryResponseDTO>> buscarPorFaixaPreco(@RequestParam("min") BigDecimal precoMin, @RequestParam("max") BigDecimal precoMax) {
		List<ProdutoSummaryResponseDTO> produtos = produtoService.buscarProdutosPorFaixaPreco(precoMin, precoMax);
		return ResponseEntity.ok(produtos);
	}

@PatchMapping("/{id}/")
public ResponseEntity<Void> atualizarProduto(
    @PathVariable UUID id,
    @RequestBody ProdutoUpdateRequestDTO produtoUpdateRequestDTO
) {
    produtoService.atualizarProduto(id, produtoUpdateRequestDTO);
    return ResponseEntity.noContent().build();
}



	@PatchMapping("/{id}/ativar")
	public ResponseEntity<Void> ativarProduto(@PathVariable UUID id) {
		produtoService.ativarProduto(id);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/{id}/desativar")
	public ResponseEntity<Void> desativarProduto(@PathVariable UUID id) {
		produtoService.desativarProduto(id);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarProduto(@PathVariable UUID id) {
		produtoService.deletarProduto(id);
		return ResponseEntity.noContent().build();
	}
}
