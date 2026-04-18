package lanchonete.controller;

import lanchonete.model.ItemPedidoModel;
import lanchonete.model.Pedido;
import lanchonete.model.ProdutoModel;
import lanchonete.repository.PedidoRepository;

import java.util.List;

public class PedidoController {

    private final PedidoRepository repository;

    public PedidoController(PedidoRepository repository) {
        this.repository = repository;
    }

    public Pedido criar(String data) {
        Pedido pedido = new Pedido(data);
        repository.salvar(pedido);
        return pedido;
    }

    public void adicionarItem(Pedido pedido, ProdutoModel produto, int quantidade) {
        ItemPedidoModel item = new ItemPedidoModel(produto, quantidade);
        pedido.adicionarItem(item);
    }

    public void finalizar(Pedido pedido) {
        pedido.finalizar();
    }

    public List<Pedido> consultarPorData(String data) {
        return repository.buscarPorData(data);
    }

    public double calcularFaturamento(String data) {
        return repository.buscarFinalizadosPorData(data)
                .stream()
                .mapToDouble(Pedido::getTotal)
                .sum();
    }

    public List<Pedido> listarTodos() {
        return repository.buscarTodos();
    }
}