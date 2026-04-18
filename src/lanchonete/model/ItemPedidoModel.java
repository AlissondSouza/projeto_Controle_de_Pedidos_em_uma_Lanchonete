package lanchonete.model;

public class ItemPedidoModel {

    private ProdutoModel produto;
    private int     quantidade;

    public ItemPedidoModel(ProdutoModel produto, int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("RN07 - A quantidade deve ser maior que zero.");
        }
        this.produto    = produto;
        this.quantidade = quantidade;
    }

    public double getSubtotal() {
        return produto.getPreco() * quantidade;
    }

    public ProdutoModel getProduto()    { return produto; }
    public int     getQuantidade() { return quantidade; }

    @Override
    public String toString() {
        return String.format("  %-20s x%d  =  R$ %.2f",
                produto.getNome(), quantidade, getSubtotal());
    }
}