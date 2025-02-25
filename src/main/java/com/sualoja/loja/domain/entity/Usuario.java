package com.sualoja.loja.domain.entity;

import com.sualoja.loja.api.dto.UsuarioDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    private String email;

    private String senha;

    private String endereco;

    private String telefone;

    @Enumerated(EnumType.STRING)
    private tipoUsuario tipo;

    public Usuario(UsuarioDTO usuarioDTO) {
        this.nome = usuarioDTO.getNome();
        this.email = usuarioDTO.getEmail();
        this.endereco = usuarioDTO.getEndereco();
        this.telefone = usuarioDTO.getTelefone();
        this.tipo = tipoUsuario.valueOf(usuarioDTO.getTipo());
    }
}
