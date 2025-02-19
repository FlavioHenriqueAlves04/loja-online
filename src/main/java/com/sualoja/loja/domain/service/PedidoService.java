package com.sualoja.loja.domain.service;

import com.sualoja.loja.domain.entity.ItensPedido;
import com.sualoja.loja.domain.entity.Pedido;
import com.sualoja.loja.domain.entity.Produto;
import com.sualoja.loja.domain.entity.Usuario;
import com.sualoja.loja.domain.repository.PedidoRepository;
import com.sualoja.loja.domain.repository.ItensPedidoRepository;
import com.sualoja.loja.domain.repository.ProdutoRepository;
import com.sualoja.loja.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Pedido salvar(Pedido pedido) {
        // Verificar se o cliente é nulo
        if (pedido.getCliente() == null || pedido.getCliente().getId() == null) {
            throw new RuntimeException("Cliente ID não pode ser nulo");
        }

        // Carregar o cliente completo
        Usuario cliente = usuarioRepository.findById(pedido.getCliente().getId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        pedido.setCliente(cliente); // Definir o cliente completo no pedido

        // Atualizar os itens do pedido e descontar do estoque
        for (ItensPedido item : pedido.getItens()) {
            Produto produto = produtoRepository.findById(item.getProduto().getId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
            item.setProduto(produto);
            item.setPrecoUnitario(produto.getPreco()); // Atribuir o preço do produto
            item.calcularTotal(); // Calcular o total do item

            // Verificar se há estoque suficiente
            if (produto.getEstoque() >= item.getQuantidade()) {
                produto.setEstoque(produto.getEstoque() - item.getQuantidade()); // Descontar do estoque
                produtoRepository.save(produto); // Salvar a atualização do estoque
            } else {
                throw new RuntimeException("Estoque insuficiente para o produto: " + produto.getNome());
            }
        }

        pedido.calcularTotal(); // Calcular o total do pedido
        return pedidoRepository.save(pedido); // Salvar o pedido
    }


    public Pedido adicionarItemAoPedido(Pedido pedido, ItensPedido item) {
        item.calcularTotal();
        pedido.getItens().add(item);
        pedido.calcularTotal();
        pedidoRepository.save(pedido);
        itensPedidoRepository.save(item);
        return pedido;
    }

    public List<Pedido> buscarTodos() {
        return pedidoRepository.findAll();
    }

    public Optional<Pedido> buscarPorId(Integer id) {
        return pedidoRepository.findById(id);
    }

    public void deletar(Integer id) {
        pedidoRepository.deleteById(id);
    }
}
