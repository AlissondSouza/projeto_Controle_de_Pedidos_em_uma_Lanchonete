package lanchonete;

public class ItemPedido {

    private Produto produto;
    private int     quantidade;

    public ItemPedido(Produto produto, int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("RN07 - A quantidade deve ser maior que zero.");
        }
        this.produto    = produto;
        this.quantidade = quantidade;
    }

    /** RN03 — Subtotal calculado automaticamente. */
    public double getSubtotal() {
        return produto.getPreco() * quantidade;
    }

    public Produto getProduto()    { return produto; }
    public int     getQuantidade() { return quantidade; }

    @Override
    public String toString() {
        return String.format("  %-20s x%d  =  R$ %.2f",
                produto.getNome(), quantidade, getSubtotal());
    }
}