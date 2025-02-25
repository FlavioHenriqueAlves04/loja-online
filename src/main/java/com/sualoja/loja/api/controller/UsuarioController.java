package com.sualoja.loja.api.controller;

import com.sualoja.loja.api.dto.UsuarioDTO;
import com.sualoja.loja.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public UsuarioDTO criarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        return usuarioService.salvar(usuarioDTO);
    }

    @GetMapping("/{email}")
    public UsuarioDTO buscarPorEmail(@PathVariable String email) {
        return usuarioService.buscarPorEmail(email);
    }

    @GetMapping
    public List<UsuarioDTO> buscarTodos() {
        return usuarioService.buscarTodos();
    }

    @GetMapping("/id/{id}")
    public Optional<UsuarioDTO> buscarPorId(@PathVariable Integer id) {
        return usuarioService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public UsuarioDTO atualizarUsuario(@PathVariable Integer id, @RequestBody UsuarioDTO usuarioDTO) {
        return usuarioService.atualizar(id, usuarioDTO);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        usuarioService.deletar(id);
    }
}
