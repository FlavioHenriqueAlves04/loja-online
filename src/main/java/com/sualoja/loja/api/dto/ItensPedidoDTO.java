package com.sualoja.loja.api.dto;

import com.sualoja.loja.domain.entity.ItensPedido;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItensPedidoDTO {

    private Integer id;
    private ProdutoDTO produto;
    private Integer quantidade;
    private BigDecimal precoUnitario;
    private BigDecimal total;

    public ItensPedidoDTO(ItensPedido item) {
        this.id = item.getId();
        this.produto = new ProdutoDTO(item.getProduto());
        this.quantidade = item.getQuantidade();
        this.precoUnitario = item.getPrecoUnitario();
        this.total = item.getTotal();
    }

}
