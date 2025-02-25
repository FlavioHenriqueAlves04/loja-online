package com.sualoja.loja.api.dto;

import com.sualoja.loja.domain.entity.Pedido;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {

    private Integer id;
    private UsuarioDTO cliente;
    private List<ItensPedidoDTO> itens;
    private BigDecimal total;
    private String status;

    public PedidoDTO(Pedido pedido) {
        this.id = pedido.getId();
        this.cliente = new UsuarioDTO(pedido.getCliente());
        this.itens = pedido.getItens().stream()
                .map(ItensPedidoDTO::new)
                .collect(Collectors.toList());
        this.total = pedido.getTotal();
        this.status = pedido.getStatus().name();
    }
}
