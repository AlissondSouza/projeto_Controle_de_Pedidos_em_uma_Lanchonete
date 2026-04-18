package lanchonete;

import lanchonete.controller.PedidoController;
import lanchonete.controller.ProdutoController;
import lanchonete.model.Pedido;
import lanchonete.model.ProdutoModel;
import lanchonete.repository.PedidoRepository;
import lanchonete.repository.ProdutoRepository;
import lanchonete.view.LanchoneteView;

import java.util.List;
import java.util.Optional;

public class Main {

    private static final LanchoneteView view = new LanchoneteView();

    private static final ProdutoRepository  produtoRepo   = new ProdutoRepository();
    private static final PedidoRepository   pedidoRepo    = new PedidoRepository();
    private static final ProdutoController  produtoCtrl   = new ProdutoController(produtoRepo);
    private static final PedidoController   pedidoCtrl    = new PedidoController(pedidoRepo);

    public static void main(String[] args) {
        view.exibirBanner();
        int opcao;
        do {
            view.exibirMenuPrincipal();
            opcao = view.lerInteiro("Escolha uma opção: ");
            switch (opcao) {
                case 1  -> menuProdutos();
                case 2  -> menuPedidos();
                case 3  -> menuConsultas();
                case 0  -> view.exibirEncerramento();
                default -> view.exibirAviso("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);

        view.fechar();
    }

    private static void menuProdutos() {
        int opcao;
        do {
            view.exibirMenuProdutos();
            opcao = view.lerInteiro("Opção: ");
            switch (opcao) {
                case 1  -> fluxoCadastrarProduto();
                case 2  -> view.exibirCardapio(produtoCtrl.listarTodos());
                case 0  -> {}
                default -> view.exibirAviso("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private static void fluxoCadastrarProduto() {
        view.exibirMensagem("\n── Cadastrar Produto ──");
        String nome      = view.lerTexto("Nome do produto: ");
        String descricao = view.lerTexto("Descrição: ");
        double preco     = view.lerDouble("Preço (ex: 12.50): ");

        try {
            ProdutoModel p = produtoCtrl.cadastrar(nome, descricao, preco);
            view.exibirProdutoCadastrado(p);
        } catch (IllegalArgumentException e) {
            view.exibirErro(e.getMessage());
        }
    }

    private static void menuPedidos() {
        int opcao;
        do {
            view.exibirMenuPedidos();
            opcao = view.lerInteiro("Opção: ");
            switch (opcao) {
                case 1  -> fluxoNovoPedido();
                case 2  -> view.exibirListaPedidos(pedidoCtrl.listarTodos());
                case 0  -> {}
                default -> view.exibirAviso("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private static void fluxoNovoPedido() {
        if (produtoCtrl.listarTodos().isEmpty()) {
            view.exibirAviso("Cadastre pelo menos um produto antes de criar um pedido.");
            return;
        }

        view.exibirMensagem("\n── Novo Pedido ──");
        String data   = view.lerTexto("Data do pedido (dd/MM/yyyy): ");
        Pedido pedido = pedidoCtrl.criar(data);
        view.exibirPedidoCriado(pedido);

        boolean adicionando = true;
        while (adicionando) {
            view.exibirCardapio(produtoCtrl.listarTodos());
            view.exibirMensagem("  0. Parar de adicionar itens");
            int idProduto = view.lerInteiro("ID do produto (0 para parar): ");

            if (idProduto == 0) {
                adicionando = false;
                continue;
            }

            Optional<ProdutoModel> opt = produtoCtrl.buscarPorId(idProduto);
            if (opt.isEmpty()) {
                view.exibirErro("Produto não encontrado.");
                continue;
            }

            ProdutoModel produto   = opt.get();
            int     quantidade = view.lerInteiro("Quantidade: ");

            try {
                pedidoCtrl.adicionarItem(pedido, produto, quantidade);
                view.exibirItemAdicionado(
                        produto.getNome(), quantidade, produto.getPreco() * quantidade);
            } catch (IllegalArgumentException | IllegalStateException e) {
                view.exibirErro(e.getMessage());
            }
        }

        if (!pedido.getItens().isEmpty()) {
            view.exibirResumoPedido(pedido);
            boolean confirmar = view.lerConfirmacao("Deseja finalizar o pedido?");
            if (confirmar) {
                try {
                    pedidoCtrl.finalizar(pedido);
                    view.exibirPedidoFinalizado(pedido);
                } catch (IllegalStateException e) {
                    view.exibirErro(e.getMessage());
                }
            } else {
                view.exibirPedidoEmAberto(pedido.getId());
            }
        } else {
            view.exibirAviso("Nenhum item adicionado.");
            view.exibirMensagem("   Tentando finalizar pedido vazio (RN04)...");
            try {
                pedidoCtrl.finalizar(pedido);
            } catch (IllegalStateException e) {
                view.exibirErro(e.getMessage());
            }
        }
    }


    private static void menuConsultas() {
        int opcao;
        do {
            view.exibirMenuConsultas();
            opcao = view.lerInteiro("Opção: ");
            switch (opcao) {
                case 1  -> fluxoConsultarPorData();
                case 2  -> fluxoFaturamento();
                case 0  -> {}
                default -> view.exibirAviso("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private static void fluxoConsultarPorData() {
        String data = view.lerTexto("Informe a data (dd/MM/yyyy): ");
        List<Pedido> lista = pedidoCtrl.consultarPorData(data);
        if (lista.isEmpty()) {
            view.exibirAviso("Nenhum pedido encontrado para " + data + ".");
        } else {
            view.exibirListaPedidos(lista);
        }
    }

    private static void fluxoFaturamento() {
        String data        = view.lerTexto("Informe a data (dd/MM/yyyy): ");
        double faturamento = pedidoCtrl.calcularFaturamento(data);
        long   qtdFin      = pedidoCtrl.consultarPorData(data)
                .stream()
                .filter(Pedido::isFinalizado)
                .count();
        view.exibirFaturamento(data, (int) qtdFin, faturamento);
    }
}