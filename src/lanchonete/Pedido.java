package lanchonete;

import java.util.ArrayList;
import java.util.List;

public class Pedido {

    private static int contadorId = 1;

    private int            id;
    private String         data;
    private List<ItemPedido> itens;
    private boolean        finalizado;

    public Pedido(String data) {
        this.id         = contadorId++;
        this.data       = data;
        this.itens      = new ArrayList<>();
        this.finalizado = false;
    }

    public void adicionarItem(ItemPedido item) {
        if (finalizado) {
            throw new IllegalStateException("Não é possível adicionar itens a um pedido já finalizado.");
        }
        itens.add(item);
    }

    /** RN03 — Soma automática dos subtotais. */
    public double getTotal() {
        return itens.stream().mapToDouble(ItemPedido::getSubtotal).sum();
    }

    /** RN04 — Pedido só pode ser finalizado com pelo menos um item. */
    public void finalizar() {
        if (itens.isEmpty()) {
            throw new IllegalStateException("RN04 - O pedido não pode ser finalizado sem itens.");
        }
        this.finalizado = true;
    }

    public int              getId()         { return id; }
    public String           getData()       { return data; }
    public List<ItemPedido> getItens()      { return new ArrayList<>(itens); }
    public boolean          isFinalizado()  { return finalizado; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Pedido #%d | Data: %s | Status: %s%n",
                id, data, finalizado ? "Finalizado" : "Aberto"));
        for (ItemPedido item : itens) {
            sb.append(item).append("\n");
        }
        sb.append(String.format("  %-24s        R$ %.2f", "TOTAL:", getTotal()));
        return sb.toString();
    }
}