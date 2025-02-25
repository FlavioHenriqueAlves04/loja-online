package com.sualoja.loja.api.dto;

import com.sualoja.loja.domain.entity.Produto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoDTO {

    private Integer id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Integer estoque;
    private String categoria;

    // Construtor que converte de Produto para ProdutoDTO
    public ProdutoDTO(Produto produto) {
        this.id = produto.getId();
        this.nome = produto.getNome();
        this.descricao = produto.getDescricao();
        this.preco = produto.getPreco();
        this.estoque = produto.getEstoque();
        this.categoria = produto.getCategoria(); // Se for um Enum, usa .name()
    }
}
