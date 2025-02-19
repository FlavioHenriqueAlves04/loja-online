package com.sualoja.loja.domain.service;

import com.sualoja.loja.domain.entity.Produto;
import com.sualoja.loja.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }

    public Produto buscarPorNome(String nome) {
        return produtoRepository.findByNome(nome);
    }

    public List<Produto> buscarTodos() {
        return produtoRepository.findAll();
    }

    public Optional<Produto> buscarPorId(Integer id) {
        return produtoRepository.findById(id);
    }

    public Produto atualizar(Integer id, Produto produto) {
        return produtoRepository.findById(id).map(p -> {
            p.setNome(produto.getNome());
            p.setDescricao(produto.getDescricao());
            p.setPreco(produto.getPreco());
            p.setEstoque(produto.getEstoque());
            return produtoRepository.save(p);
        }).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    public void deletar(Integer id) {
        produtoRepository.deleteById(id);
    }
}
