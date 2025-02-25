package com.sualoja.loja.api.dto;

import com.sualoja.loja.domain.entity.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {

    private Integer id;
    private String nome;
    private String email;
    private String endereco;
    private String telefone;
    private String tipo;

    public UsuarioDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.endereco = usuario.getEndereco();
        this.telefone = usuario.getTelefone();
        this.tipo = usuario.getTipo().name();
    }
}
