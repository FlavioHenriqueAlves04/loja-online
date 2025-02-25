package com.sualoja.loja.api.controller;

import com.sualoja.loja.api.dto.PedidoDTO;
import com.sualoja.loja.domain.entity.Pedido;
import com.sualoja.loja.domain.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public Pedido criarPedido(@RequestBody PedidoDTO pedidoDTO) {
        return pedidoService.salvar(pedidoDTO);
    }

    @GetMapping
    public List<PedidoDTO> buscarTodos() {
        return pedidoService.buscarTodos();
    }

    @GetMapping("/id/{id}")
    public Optional<PedidoDTO> buscarPorId(@PathVariable Integer id) {
        return pedidoService.buscarPorId(id);
    }

    @GetMapping("/cliente/{nomeCliente:.+}")
    public ResponseEntity<List<PedidoDTO>> buscarPorCliente(@PathVariable String nomeCliente) {

        nomeCliente = nomeCliente.replace("+", " ").replace("_", " ");
        List<PedidoDTO> pedidos = pedidoService.buscarClientePorNome(nomeCliente);
        return ResponseEntity.ok(pedidos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoDTO> atualizarPedido(@PathVariable Integer id, @RequestBody PedidoDTO pedidoDTO) {
        PedidoDTO pedidoAtualizado = pedidoService.atualizar(id, pedidoDTO);
        return ResponseEntity.ok(pedidoAtualizado);
    }

    @DeleteMapping("/{id}/cancelar")
    public ResponseEntity<String> cancelarPedido(@PathVariable Integer id) {
        pedidoService.cancelar(id);
        return ResponseEntity.ok("Pedido cancelado com sucesso.");
    }

}
