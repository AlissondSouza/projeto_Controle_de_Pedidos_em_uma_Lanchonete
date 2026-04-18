package lanchonete.repository;

import lanchonete.model.ProdutoModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProdutoRepository {

    private final List<ProdutoModel> produtos = new ArrayList<>();

    public void salvar(ProdutoModel produto) {
        produtos.add(produto);
    }

    public List<ProdutoModel> buscarTodos() {
        return new ArrayList<>(produtos);
    }

    public Optional<ProdutoModel> buscarPorId(int id) {
        return produtos.stream()
                .filter(p -> p.getId() == id)
                .findFirst();
    }
}