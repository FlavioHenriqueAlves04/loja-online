package com.sualoja.loja.api.controller;


import com.sualoja.loja.api.dto.UsuarioLoginDto;
import com.sualoja.loja.domain.entity.Usuario;
import com.sualoja.loja.domain.repository.UsuarioRepository;
import com.sualoja.loja.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UsuarioLoginDto loginDto) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findOptionalByEmail(loginDto.getEmail());

        if (usuarioOpt.isPresent() && passwordEncoder.matches(loginDto.getSenha(), usuarioOpt.get().getSenha())) {
            String role = usuarioOpt.get().getTipo().name();
            String token = jwtService.gerarToken(usuarioOpt.get().getEmail(), role);
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(401).body("Credenciais inválidas");
    }


    @PostMapping("/register")
    public ResponseEntity<Usuario> register(@RequestBody Usuario usuario) {
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        Usuario novoUsuario = usuarioRepository.save(usuario);
        return ResponseEntity.ok(novoUsuario);
    }
}