package com.sualoja.loja.domain.repository;

import com.sualoja.loja.domain.entity.ItensPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItensPedidoRepository extends JpaRepository<ItensPedido, Integer> {
    List<ItensPedido> findAll();  // Buscar todos os itens do pedido
}
