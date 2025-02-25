package com.sualoja.loja.domain.entity;

import com.sualoja.loja.api.dto.ProdutoDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    private String descricao;

    private BigDecimal preco;

    private Integer estoque;

    private String categoria;

    public Produto(ProdutoDTO produtoDTO) {
        this.nome = produtoDTO.getNome();
        this.descricao = produtoDTO.getDescricao();
        this.preco = produtoDTO.getPreco();
        this.estoque = produtoDTO.getEstoque();
        this.categoria = produtoDTO.getCategoria();
    }

    // Método para reduzir o estoque
    public boolean reduzirEstoque(int quantidade) {
        if (quantidade <= estoque) {
            estoque -= quantidade;
            return true;
        }
        return false;
    }
}
