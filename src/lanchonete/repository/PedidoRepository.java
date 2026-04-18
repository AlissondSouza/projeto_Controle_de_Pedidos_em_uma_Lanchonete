package lanchonete.repository;

import lanchonete.model.Pedido;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PedidoRepository {

    private final List<Pedido> pedidos = new ArrayList<>();

    public void salvar(Pedido pedido) {
        pedidos.add(pedido);
    }

    public List<Pedido> buscarTodos() {
        return new ArrayList<>(pedidos);
    }

    public List<Pedido> buscarPorData(String data) {
        return pedidos.stream()
                .filter(p -> p.getData().equals(data))
                .collect(Collectors.toList());
    }


    public List<Pedido> buscarFinalizadosPorData(String data) {
        return pedidos.stream()
                .filter(p -> p.getData().equals(data) && p.isFinalizado())
                .collect(Collectors.toList());
    }
}