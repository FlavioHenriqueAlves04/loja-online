package com.sualoja.loja.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItensPedido> itens = new ArrayList<>();

    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    private StatusPedido status;

    public void calcularTotal() {
        this.total = itens.stream()
                .map(ItensPedido::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
