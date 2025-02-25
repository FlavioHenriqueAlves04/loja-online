package com.sualoja.loja.api.controller;

import com.sualoja.loja.api.dto.ProdutoDTO;
import com.sualoja.loja.domain.entity.Usuario;
import com.sualoja.loja.domain.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping
    public ProdutoDTO criarProduto(@RequestBody ProdutoDTO produtoDTO) {
        return produtoService.salvar(produtoDTO);
    }

    @GetMapping("/{nome}")
    public ProdutoDTO buscarPorNome(@PathVariable String nome) {
        return produtoService.buscarPorNome(nome);
    }

    @GetMapping
    public List<ProdutoDTO> buscarTodos() {
        return produtoService.buscarTodos();
    }

    @GetMapping("/id/{id}")
    public Optional<ProdutoDTO> buscarPorId(@PathVariable Integer id) {
        return produtoService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public ProdutoDTO atualizarProduto(@PathVariable Integer id, @RequestBody ProdutoDTO produtoDTO) {
        return produtoService.atualizar(id, produtoDTO);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        produtoService.deletar(id);
    }

    @PutMapping("/{id}/estoque")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProdutoDTO> atualizarEstoque(@PathVariable Integer id, @RequestBody Map<String, Object> request) {
        Integer quantidade = (Integer) request.get("quantidade");
        String operacao = (String) request.get("operacao");

        ProdutoDTO produtoAtualizado = produtoService.atualizarEstoque(id, quantidade, operacao);
        return ResponseEntity.ok(produtoAtualizado);
    }



}
