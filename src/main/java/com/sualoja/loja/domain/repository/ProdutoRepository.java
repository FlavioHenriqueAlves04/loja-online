package com.sualoja.loja.domain.repository;

import com.sualoja.loja.api.dto.ProdutoDTO;
import com.sualoja.loja.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    Produto findByNome(String nome);
    List<Produto> findAll();


}
