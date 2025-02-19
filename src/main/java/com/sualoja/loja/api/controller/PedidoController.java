package com.sualoja.loja.api.controller;

import com.sualoja.loja.domain.entity.ItensPedido;
import com.sualoja.loja.domain.entity.Pedido;
import com.sualoja.loja.domain.entity.Produto;
import com.sualoja.loja.domain.entity.Usuario;
import com.sualoja.loja.domain.repository.ProdutoRepository;
import com.sualoja.loja.domain.repository.UsuarioRepository;
import com.sualoja.loja.domain.service.PedidoService;
import com.sualoja.loja.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public Pedido criarPedido(@RequestBody Pedido pedido) {
        return pedidoService.salvar(pedido);
    }

    @PostMapping("/{pedidoId}/itens")
    public Pedido adicionarItemAoPedido(@PathVariable Integer pedidoId, @RequestBody ItensPedido item) {
        Pedido pedido = new Pedido(); // Buscar pedido por ID
        pedido.setId(pedidoId);
        return pedidoService.adicionarItemAoPedido(pedido, item);
    }

    @GetMapping
    public List<Pedido> buscarTodos() {
        return pedidoService.buscarTodos();
    }

    @GetMapping("/id/{id}")
    public Optional<Pedido> buscarPorId(@PathVariable Integer id) {
        return pedidoService.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        pedidoService.deletar(id);
    }
}
