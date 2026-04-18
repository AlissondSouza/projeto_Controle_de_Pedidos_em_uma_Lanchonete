package lanchonete.view;

import lanchonete.model.Pedido;
import lanchonete.model.ProdutoModel;

import java.util.List;
import java.util.Scanner;

public class LanchoneteView {

    private final Scanner scanner = new Scanner(System.in);



    public void exibirBanner() {
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║  🍔  SISTEMA DE PEDIDOS — LANCHONETE  🥤 ║");
        System.out.println("║         Projeto 2  —  Padrão MVC         ║");
        System.out.println("╚══════════════════════════════════════════╝");
    }

    public void exibirMenuPrincipal() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║        🍔 LANCHONETE — MENU          ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║  1. Gerenciar Produtos               ║");
        System.out.println("║  2. Gerenciar Pedidos                ║");
        System.out.println("║  3. Consultas e Relatórios           ║");
        System.out.println("║  0. Sair                             ║");
        System.out.println("╚══════════════════════════════════════╝");
    }

    public void exibirMenuProdutos() {
        System.out.println("\n┌─── PRODUTOS ───────────────────────┐");
        System.out.println("│  1. Cadastrar produto               │");
        System.out.println("│  2. Listar produtos                 │");
        System.out.println("│  0. Voltar                          │");
        System.out.println("└─────────────────────────────────────┘");
    }

    public void exibirMenuPedidos() {
        System.out.println("\n┌─── PEDIDOS ────────────────────────┐");
        System.out.println("│  1. Novo pedido                     │");
        System.out.println("│  2. Listar todos os pedidos         │");
        System.out.println("│  0. Voltar                          │");
        System.out.println("└─────────────────────────────────────┘");
    }

    public void exibirMenuConsultas() {
        System.out.println("\n┌─── CONSULTAS ──────────────────────┐");
        System.out.println("│  1. Pedidos por data  (RN05)        │");
        System.out.println("│  2. Faturamento do dia (RN06)       │");
        System.out.println("│  0. Voltar                          │");
        System.out.println("└─────────────────────────────────────┘");
    }

    public void exibirCardapio(List<ProdutoModel> produtos) {
        if (produtos.isEmpty()) {
            System.out.println("⚠️  Nenhum produto cadastrado.");
            return;
        }
        System.out.println("\n── Cardápio ──────────────────────────────────────────────");
        produtos.forEach(System.out::println);
        System.out.println("──────────────────────────────────────────────────────────");
    }

    public void exibirProdutoCadastrado(ProdutoModel p) {
        System.out.println("✅ Produto cadastrado: " + p);
    }

    public void exibirPedidoCriado(Pedido pedido) {
        System.out.println("✅ Pedido #" + pedido.getId() + " criado para " + pedido.getData());
    }

    public void exibirItemAdicionado(String nomeProduto, int qtd, double subtotal) {
        System.out.printf("✅ Adicionado: %s x%d = R$ %.2f%n", nomeProduto, qtd, subtotal);
    }

    public void exibirResumoPedido(Pedido pedido) {
        System.out.println("\n── Resumo do Pedido ──");
        System.out.println(pedido);
    }

    public void exibirPedidoFinalizado(Pedido pedido) {
        System.out.printf("✅ Pedido #%d finalizado! Total: R$ %.2f%n",
                pedido.getId(), pedido.getTotal());
    }

    public void exibirPedidoEmAberto(int id) {
        System.out.println("ℹ️  Pedido #" + id + " mantido em aberto.");
    }

    public void exibirListaPedidos(List<Pedido> pedidos) {
        if (pedidos.isEmpty()) {
            System.out.println("⚠️  Nenhum pedido registrado.");
            return;
        }
        System.out.println("\n── Pedidos ────────────────────────────────────────────────");
        pedidos.forEach(p -> {
            System.out.println(p);
            System.out.println("──────────────────────────────────────────────────────────");
        });
    }

    public void exibirFaturamento(String data, int qtdPedidos, double total) {
        System.out.printf("%n── Faturamento em %s ─────────────────────────%n", data);
        System.out.printf("   Pedidos finalizados : %d%n", qtdPedidos);
        System.out.printf("   Total faturado      : R$ %.2f%n", total);
    }

    public void exibirErro(String msg) {
        System.out.println("❌ Erro: " + msg);
    }

    public void exibirAviso(String msg) {
        System.out.println("⚠️  " + msg);
    }

    public void exibirMensagem(String msg) {
        System.out.println(msg);
    }

    public void exibirEncerramento() {
        System.out.println("\n👋 Encerrando o sistema. Até logo!");
    }

    public int lerInteiro(String prompt) {
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

    public double lerDouble(String prompt) {
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

    public String lerTexto(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public boolean lerConfirmacao(String prompt) {
        System.out.print(prompt + " (s/n): ");
        return scanner.nextLine().trim().equalsIgnoreCase("s");
    }

    public void fechar() {
        scanner.close();
    }
}