package com.sualoja.loja.domain.service;

import com.sualoja.loja.api.dto.ProdutoDTO;
import com.sualoja.loja.domain.entity.Produto;
import com.sualoja.loja.domain.entity.tipoUsuario;
import com.sualoja.loja.domain.entity.Usuario;
import com.sualoja.loja.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public ProdutoDTO salvar(ProdutoDTO produtoDTO) {
        Produto produto = new Produto(produtoDTO);
        return new ProdutoDTO(produtoRepository.save(produto));
    }

    public ProdutoDTO buscarPorNome(String nome) {
        Produto produto = produtoRepository.findByNome(nome);
        return produto != null ? new ProdutoDTO(produto) : null;
    }

    public List<ProdutoDTO> buscarTodos() {
        return produtoRepository.findAll().stream().map(ProdutoDTO::new).collect(Collectors.toList());
    }

    public Optional<ProdutoDTO> buscarPorId(Integer id) {
        return produtoRepository.findById(id).map(ProdutoDTO::new);
    }

    public ProdutoDTO atualizar(Integer id, ProdutoDTO produtoDTO) {
        return produtoRepository.findById(id).map(produto -> {
            produto.setNome(produtoDTO.getNome());
            produto.setDescricao(produtoDTO.getDescricao());
            produto.setPreco(produtoDTO.getPreco());
            produto.setEstoque(produtoDTO.getEstoque());
            return new ProdutoDTO(produtoRepository.save(produto));
        }).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    public void deletar(Integer id) {
        produtoRepository.deleteById(id);
    }

    public ProdutoDTO atualizarEstoque(Integer id, Integer quantidade, String operacao) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        if (quantidade < 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior ou igual a zero.");
        }

        switch (operacao.toLowerCase()) {
            case "aumentar":
                produto.setEstoque(produto.getEstoque() + quantidade);
                break;
            case "diminuir":
                if (produto.getEstoque() < quantidade) {
                    throw new IllegalArgumentException("Estoque insuficiente para essa redução.");
                }
                produto.setEstoque(produto.getEstoque() - quantidade);
                break;
            case "substituir":
                produto.setEstoque(quantidade);
                break;
            default:
                throw new IllegalArgumentException("Operação inválida. Use 'aumentar', 'diminuir' ou 'substituir'.");
        }

        produtoRepository.save(produto);
        return new ProdutoDTO(produto);
    }




}
