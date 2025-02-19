package com.sualoja.loja.domain.repository;

import com.sualoja.loja.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    Produto findByNome(String nome);
    List<Produto> findAll();  // Buscar todos os produtos
}
