package com.sualoja.loja.domain.repository;

import com.sualoja.loja.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Usuario findByEmail(String email);
    List<Usuario> findAll();  // Buscar todos os usuários

    Optional<Usuario> findOptionalByEmail(String email); // Renomeado para evitar conflito

    Optional<Usuario> findByNome(String nome);
}

