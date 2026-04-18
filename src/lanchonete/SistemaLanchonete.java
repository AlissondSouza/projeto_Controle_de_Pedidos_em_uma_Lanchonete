package lanchonete;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SistemaLanchonete {

    private List<Produto> produtos = new ArrayList<>();
    private List<Pedido>  pedidos  = new ArrayList<>();


    public Produto cadastrarProduto(String nome, String descricao, double preco) {
        Produto p = new Produto(nome, descricao, preco);
        produtos.add(p);
        return p;
    }

    public List<Produto> listarProdutos() {
        return new ArrayList<>(produtos);
    }

    public Produto buscarProdutoPorId(int id) {
        return produtos.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }


    public Pedido criarPedido(String data) {
        Pedido pedido = new Pedido(data);
        pedidos.add(pedido);
        return pedido;
    }

    public void adicionarItemAoPedido(Pedido pedido, Produto produto, int quantidade) {
        ItemPedido item = new ItemPedido(produto, quantidade);
        pedido.adicionarItem(item);
    }

    public void finalizarPedido(Pedido pedido) {
        pedido.finalizar();
    }

    public List<Pedido> consultarPedidosPorData(String data) {
        return pedidos.stream()
                .filter(p -> p.getData().equals(data))
                .collect(Collectors.toList());
    }

    public double calcularFaturamentoPorData(String data) {
        return pedidos.stream()
                .filter(p -> p.getData().equals(data) && p.isFinalizado())
                .mapToDouble(Pedido::getTotal)
                .sum();
    }

    public List<Pedido> listarTodosPedidos() {
        return new ArrayList<>(pedidos);
    }
}