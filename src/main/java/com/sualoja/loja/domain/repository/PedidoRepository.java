package com.sualoja.loja.domain.repository;

import com.sualoja.loja.domain.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    List<Pedido> findAll();  // Buscar todos os pedidos

    List<Pedido> findByClienteId(Integer clienteId);
}
