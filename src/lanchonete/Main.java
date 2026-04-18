package lanchonete;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final SistemaLanchonete sistema = new SistemaLanchonete();

    public static void main(String[] args) {
        exibirBanner();
        int opcao;
        do {
            exibirMenuPrincipal();
            opcao = lerInteiro("Escolha uma opção: ");
            switch (opcao) {
                case 1  -> menuProdutos();
                case 2  -> menuPedidos();
                case 3  -> menuConsultas();
                case 0  -> System.out.println("\n👋 Encerrando o sistema. Até logo!");
                default -> System.out.println("⚠️  Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);

        scanner.close();
    }

    private static void exibirMenuPrincipal() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║        🍔 LANCHONETE — MENU          ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║  1. Gerenciar Produtos               ║");
        System.out.println("║  2. Gerenciar Pedidos                ║");
        System.out.println("║  3. Consultas e Relatórios           ║");
        System.out.println("║  0. Sair                             ║");
        System.out.println("╚══════════════════════════════════════╝");
    }

    private static void menuProdutos() {
        int opcao;
        do {
            System.out.println("\n┌─── PRODUTOS ───────────────────────┐");
            System.out.println("│  1. Cadastrar produto               │");
            System.out.println("│  2. Listar produtos                 │");
            System.out.println("│  0. Voltar                          │");
            System.out.println("└─────────────────────────────────────┘");
            opcao = lerInteiro("Opção: ");
            switch (opcao) {
                case 1  -> cadastrarProduto();
                case 2  -> listarProdutos();
                case 0  -> {}
                default -> System.out.println("⚠️  Opção inválida.");
            }
        } while (opcao != 0);
    }

    private static void cadastrarProduto() {
        System.out.println("\n── Cadastrar Produto ──");
        System.out.print("Nome do produto: ");
        String nome = scanner.nextLine().trim();

        System.out.print("Descrição: ");
        String descricao = scanner.nextLine().trim();

        double preco = lerDouble("Preço (ex: 12.50): ");

        try {
            Produto p = sistema.cadastrarProduto(nome, descricao, preco);
            System.out.println("✅ Produto cadastrado: " + p);
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Erro: " + e.getMessage());
        }
    }

    private static void listarProdutos() {
        List<Produto> lista = sistema.listarProdutos();
        if (lista.isEmpty()) {
            System.out.println("⚠️  Nenhum produto cadastrado.");
            return;
        }
        System.out.println("\n── Cardápio ──────────────────────────────────────────────");
        lista.forEach(System.out::println);
        System.out.println("──────────────────────────────────────────────────────────");
    }

    private static void menuPedidos() {
        int opcao;
        do {
            System.out.println("\n┌─── PEDIDOS ────────────────────────┐");
            System.out.println("│  1. Novo pedido                     │");
            System.out.println("│  2. Listar todos os pedidos         │");
            System.out.println("│  0. Voltar                          │");
            System.out.println("└─────────────────────────────────────┘");
            opcao = lerInteiro("Opção: ");
            switch (opcao) {
                case 1  -> fluxoNovoPedido();
                case 2  -> listarTodosPedidos();
                case 0  -> {}
                default -> System.out.println("⚠️  Opção inválida.");
            }
        } while (opcao != 0);
    }

    private static void fluxoNovoPedido() {
        if (sistema.listarProdutos().isEmpty()) {
            System.out.println("⚠️  Cadastre pelo menos um produto antes de criar um pedido.");
            return;
        }

        System.out.println("\n── Novo Pedido ──");
        System.out.print("Data do pedido (dd/MM/yyyy): ");
        String data = scanner.nextLine().trim();

        Pedido pedido = sistema.criarPedido(data);
        System.out.println("✅ Pedido #" + pedido.getId() + " criado para " + data);

        boolean adicionando = true;
        while (adicionando) {
            listarProdutos();
            System.out.println("  0. Parar de adicionar itens");
            int idProduto = lerInteiro("ID do produto (0 para parar): ");

            if (idProduto == 0) {
                adicionando = false;
                continue;
            }

            Produto produto = sistema.buscarProdutoPorId(idProduto);
            if (produto == null) {
                System.out.println("❌ Produto não encontrado.");
                continue;
            }

            int quantidade = lerInteiro("Quantidade: ");
            try {
                sistema.adicionarItemAoPedido(pedido, produto, quantidade);
                System.out.printf("✅ Adicionado: %s x%d = R$ %.2f%n",
                        produto.getNome(), quantidade, produto.getPreco() * quantidade);
            } catch (IllegalArgumentException e) {
                System.out.println("❌ Erro: " + e.getMessage());
            }
        }

        if (!pedido.getItens().isEmpty()) {
            System.out.println("\n── Resumo do Pedido ──");
            System.out.println(pedido);
            System.out.print("\nDeseja finalizar o pedido? (s/n): ");
            String resp = scanner.nextLine().trim().toLowerCase();
            if (resp.equals("s")) {
                try {
                    sistema.finalizarPedido(pedido);
                    System.out.println("✅ Pedido #" + pedido.getId() + " finalizado! Total: R$ " +
                            String.format("%.2f", pedido.getTotal()));
                } catch (IllegalStateException e) {
                    System.out.println("❌ Erro: " + e.getMessage());
                }
            } else {
                System.out.println("ℹ️  Pedido #" + pedido.getId() + " mantido em aberto.");
            }
        } else {
            System.out.println("\n⚠️  Nenhum item foi adicionado ao pedido.");
            System.out.println("   Tentando finalizar pedido vazio (RN04)...");
            try {
                sistema.finalizarPedido(pedido);
            } catch (IllegalStateException e) {
                System.out.println("❌ Erro esperado — " + e.getMessage());
            }
        }
    }

    private static void listarTodosPedidos() {
        List<Pedido> lista = sistema.listarTodosPedidos();
        if (lista.isEmpty()) {
            System.out.println("⚠️  Nenhum pedido registrado.");
            return;
        }
        System.out.println("\n── Todos os Pedidos ───────────────────────────────────────");
        lista.forEach(p -> {
            System.out.println(p);
            System.out.println("──────────────────────────────────────────────────────────");
        });
    }

    private static void menuConsultas() {
        int opcao;
        do {
            System.out.println("\n┌─── CONSULTAS ──────────────────────┐");
            System.out.println("│  1. Pedidos por data  (RN05)        │");
            System.out.println("│  2. Faturamento do dia (RN06)       │");
            System.out.println("│  0. Voltar                          │");
            System.out.println("└─────────────────────────────────────┘");
            opcao = lerInteiro("Opção: ");
            switch (opcao) {
                case 1  -> consultarPedidosPorData();
                case 2  -> consultarFaturamento();
                case 0  -> {}
                default -> System.out.println("⚠️  Opção inválida.");
            }
        } while (opcao != 0);
    }

    private static void consultarPedidosPorData() {
        System.out.print("Informe a data (dd/MM/yyyy): ");
        String data = scanner.nextLine().trim();

        List<Pedido> lista = sistema.consultarPedidosPorData(data);
        if (lista.isEmpty()) {
            System.out.println("⚠️  Nenhum pedido encontrado para " + data + ".");
            return;
        }
        System.out.println("\n── Pedidos em " + data + " ────────────────────────────────");
        lista.forEach(p -> {
            System.out.println(p);
            System.out.println("──────────────────────────────────────────────────────────");
        });
    }

    private static void consultarFaturamento() {
        System.out.print("Informe a data (dd/MM/yyyy): ");
        String data = scanner.nextLine().trim();

        double faturamento = sistema.calcularFaturamentoPorData(data);
        List<Pedido> finalizados = sistema.consultarPedidosPorData(data)
                .stream().filter(Pedido::isFinalizado).collect(java.util.stream.Collectors.toList());

        System.out.printf("%n── Faturamento em %s ─────────────────────────%n", data);
        System.out.printf("   Pedidos finalizados : %d%n", finalizados.size());
        System.out.printf("   Total faturado      : R$ %.2f%n", faturamento);
    }

    private static void exibirBanner() {
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║  🍔  SISTEMA DE PEDIDOS — LANCHONETE  🥤 ║");
        System.out.println("║         Projeto 1  —  Sem MVC            ║");
        System.out.println("╚══════════════════════════════════════════╝");
    }

    private static int lerInteiro(String prompt) {
        while (true) {
            System.out.print(prompt);
            String linha = scanner.nextLine().trim();
            try {
                return Integer.parseInt(linha);
            } catch (NumberFormatException e) {
                System.out.println("⚠️  Digite um número inteiro válido.");
            }
        }
    }

    private static double lerDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String linha = scanner.nextLine().trim().replace(",", ".");
            try {
                return Double.parseDouble(linha);
            } catch (NumberFormatException e) {
                System.out.println("⚠️  Digite um valor numérico válido (ex: 12.50).");
            }
        }
    }
}