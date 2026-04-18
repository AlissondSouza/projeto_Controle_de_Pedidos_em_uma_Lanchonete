package lanchonete.controller;

import lanchonete.model.ProdutoModel;
import lanchonete.repository.ProdutoRepository;

import java.util.List;
import java.util.Optional;

public class ProdutoController {

    private final ProdutoRepository repository;

    public ProdutoController(ProdutoRepository repository) {
        this.repository = repository;
    }

    public ProdutoModel cadastrar(String nome, String descricao, double preco) {
        ProdutoModel produto = new ProdutoModel(nome, descricao, preco);
        repository.salvar(produto);
        return produto;
    }

    public List<ProdutoModel> listarTodos() {
        return repository.buscarTodos();
    }

    public Optional<ProdutoModel> buscarPorId(int id) {
        return repository.buscarPorId(id);
    }
}