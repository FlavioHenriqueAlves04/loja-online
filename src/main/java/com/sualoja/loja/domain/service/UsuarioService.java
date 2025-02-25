package com.sualoja.loja.domain.service;

import com.sualoja.loja.api.dto.UsuarioDTO;
import com.sualoja.loja.domain.entity.Usuario;
import com.sualoja.loja.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioDTO salvar(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario(usuarioDTO);
        return new UsuarioDTO(usuarioRepository.save(usuario));
    }

    public UsuarioDTO buscarPorEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        return usuario != null ? new UsuarioDTO(usuario) : null;
    }

    public List<UsuarioDTO> buscarTodos() {
        return usuarioRepository.findAll().stream().map(UsuarioDTO::new).collect(Collectors.toList());
    }

    public Optional<UsuarioDTO> buscarPorId(Integer id) {
        return usuarioRepository.findById(id).map(UsuarioDTO::new);
    }

    public UsuarioDTO atualizar(Integer id, UsuarioDTO usuarioDTO) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setNome(usuarioDTO.getNome());
            usuario.setEmail(usuarioDTO.getEmail());
            usuario.setEndereco(usuarioDTO.getEndereco());
            usuario.setTelefone(usuarioDTO.getTelefone());
            return new UsuarioDTO(usuarioRepository.save(usuario));
        }).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public void deletar(Integer id) {
        usuarioRepository.deleteById(id);
    }
}
