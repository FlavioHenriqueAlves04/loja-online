package com.sualoja.loja.domain.service;

import com.sualoja.loja.api.dto.PedidoDTO;
import com.sualoja.loja.domain.entity.Pedido;

import com.sualoja.loja.domain.entity.*;
import com.sualoja.loja.domain.repository.PedidoRepository;
import com.sualoja.loja.domain.repository.ItensPedidoRepository;
import com.sualoja.loja.domain.repository.ProdutoRepository;
import com.sualoja.loja.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ItensPedidoRepository itensPedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Pedido salvar(PedidoDTO pedidoDTO) {
        Usuario cliente = usuarioRepository.findById(pedidoDTO.getCliente().getId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.PENDENTE); // Definindo o status inicial

        List<ItensPedido> itens = pedidoDTO.getItens().stream().map(itemDTO -> {
            Produto produto = produtoRepository.findById(itemDTO.getProduto().getId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

            if (produto.getEstoque() < itemDTO.getQuantidade()) {
                throw new RuntimeException("Estoque insuficiente para o produto: " + produto.getNome());
            }

            if (itemDTO.getQuantidade() < 1) {
                throw new RuntimeException("Quantidade mínima de produtos é 1.");
            }

            produto.setEstoque(produto.getEstoque() - itemDTO.getQuantidade());
            produtoRepository.save(produto);

            ItensPedido item = new ItensPedido();
            item.setProduto(produto);
            item.setQuantidade(itemDTO.getQuantidade());
            item.setPrecoUnitario(produto.getPreco());
            item.calcularTotal();


            item.setPedido(pedido);
            return item;
        }).collect(Collectors.toList());

        pedido.setItens(itens);
        pedido.calcularTotal();
        return pedidoRepository.save(pedido);
    }

    public List<PedidoDTO> buscarTodos() {
        return pedidoRepository.findAll().stream().map(pedido -> new PedidoDTO(pedido)).collect(Collectors.toList());
    }

    public Optional<PedidoDTO> buscarPorId(Integer id) {
        return pedidoRepository.findById(id).map(PedidoDTO::new);
    }

    public List<PedidoDTO> buscarClientePorNome(String nomeCliente) {

        Usuario cliente = usuarioRepository.findByNome(nomeCliente)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));


        return pedidoRepository.findByClienteId(cliente.getId()).stream()
                .map(PedidoDTO::new)
                .collect(Collectors.toList());
    }

    public PedidoDTO atualizar(Integer id, PedidoDTO pedidoDTO) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        if (!pedido.getStatus().equals(StatusPedido.PENDENTE)) {
            throw new RuntimeException("A atualização só é permitida para pedidos com status PENDENTE.");
        }

        Usuario cliente = usuarioRepository.findById(pedidoDTO.getCliente().getId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        pedido.setCliente(cliente);


        List<ItensPedido> itens = pedidoDTO.getItens().stream().map(itemDTO -> {
            Produto produto = produtoRepository.findById(itemDTO.getProduto().getId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

            if (produto.getEstoque() < itemDTO.getQuantidade()) {
                throw new RuntimeException("Estoque insuficiente para o produto: " + produto.getNome());
            }

            ItensPedido item = new ItensPedido();
            item.setProduto(produto);
            item.setQuantidade(itemDTO.getQuantidade());
            item.setPrecoUnitario(produto.getPreco());
            item.calcularTotal();
            return item;
        }).collect(Collectors.toList());

        pedido.setItens(itens);
        pedido.calcularTotal();
        return new PedidoDTO(pedidoRepository.save(pedido));
    }

    public void cancelar(Integer id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        if (!pedido.getStatus().equals(StatusPedido.PENDENTE)) {
            throw new RuntimeException("Só é possível cancelar pedidos com status PENDENTE.");
        }

        pedido.setStatus(StatusPedido.CANCELADO);
        pedidoRepository.save(pedido);
    }

}
