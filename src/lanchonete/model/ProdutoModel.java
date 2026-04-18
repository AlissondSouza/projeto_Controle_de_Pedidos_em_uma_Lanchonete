package lanchonete.model;

public class ProdutoModel {

    private static int contadorId = 1;

    private int    id;
    private String nome;
    private String descricao;
    private double preco;

    public ProdutoModel(String nome, String descricao, double preco) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("RN01 - O nome do produto não pode ser vazio.");
        }
        if (preco <= 0) {
            throw new IllegalArgumentException("RN02 - O preço do produto deve ser maior que zero.");
        }
        this.id        = contadorId++;
        this.nome      = nome.trim();
        this.descricao = (descricao != null) ? descricao.trim() : "";
        this.preco     = preco;
    }

    public int    getId()        { return id; }
    public String getNome()      { return nome; }
    public String getDescricao() { return descricao; }
    public double getPreco()     { return preco; }

    @Override
    public String toString() {
        return String.format("[%d] %-20s | R$ %7.2f | %s",
                id, nome, preco, descricao);
    }
}