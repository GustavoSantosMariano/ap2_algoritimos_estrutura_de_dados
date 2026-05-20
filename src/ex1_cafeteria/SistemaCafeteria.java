package ex1_cafeteria;

import java.util.Scanner;

/**
 * Sistema de Gerenciamento de Pedidos para Cafeteria.
 * Exercício 1 — Pilha e Fila com Lista Simplesmente Ligada.
 */
public class SistemaCafeteria {

    private static final FilaPedidos filaPendentes = new FilaPedidos();
    private static final PilhaCancelados pilhaCancelados = new PilhaCancelados();
    private static int proximoId = 1;

    public static void executar(Scanner scanner) {
        System.out.println("\n╔══════════════════════════════════════════════╗");
        System.out.println("║     SISTEMA DE PEDIDOS — CAFETERIA           ║");
        System.out.println("╚══════════════════════════════════════════════╝");

        boolean rodando = true;
        while (rodando) {
            exibirMenu();
            System.out.print("  Escolha: ");
            int opcao = lerInt(scanner);

            switch (opcao) {
                case 1 -> adicionarPedido(scanner);
                case 2 -> atenderPedido();
                case 3 -> cancelarPedido();
                case 4 -> restaurarPedido();
                case 5 -> filaPendentes.printQueue();
                case 6 -> pilhaCancelados.printStack();
                case 0 -> {
                    System.out.println("\n  Saindo do Sistema de Cafeteria...");
                    rodando = false;
                }
                default -> System.out.println("  ✘ Opção inválida. Tente novamente.");
            }
        }
    }

    // ─── Menu ────────────────────────────────────────────────────────────────

    private static void exibirMenu() {
        System.out.println("\n┌─────────────────────────────────────────────┐");
        System.out.println("│  [1] Adicionar novo pedido                  │");
        System.out.println("│  [2] Atender pedido (dequeue)               │");
        System.out.println("│  [3] Cancelar pedido (dequeue → push)       │");
        System.out.println("│  [4] Restaurar pedido (pop → enqueue)       │");
        System.out.println("│  [5] Imprimir pedidos pendentes             │");
        System.out.println("│  [6] Imprimir pedidos cancelados            │");
        System.out.println("│  [0] Voltar ao menu principal               │");
        System.out.println("└─────────────────────────────────────────────┘");
    }

    // ─── Operações ───────────────────────────────────────────────────────────

    private static void adicionarPedido(Scanner scanner) {
        System.out.print("  Descrição do pedido: ");
        String desc = scanner.nextLine().trim();
        if (desc.isEmpty()) {
            System.out.println("  ✘ Descrição não pode ser vazia.");
            return;
        }
        filaPendentes.enqueue(proximoId++, desc);
    }

    private static void atenderPedido() {
        No pedido = filaPendentes.dequeue();
        if (pedido != null) {
            System.out.println("  ✔ Pedido ATENDIDO: " + pedido);
        }
    }

    private static void cancelarPedido() {
        No pedido = filaPendentes.dequeue();
        if (pedido != null) {
            pilhaCancelados.push(pedido);
            System.out.println("  ✔ Pedido CANCELADO: " + pedido);
        }
    }

    private static void restaurarPedido() {
        No pedido = pilhaCancelados.pop();
        if (pedido != null) {
            filaPendentes.enqueueNo(pedido);
            System.out.println("  ✔ Pedido RESTAURADO: " + pedido);
        }
    }

    // ─── Utilitário ──────────────────────────────────────────────────────────

    private static int lerInt(Scanner scanner) {
        try {
            String linha = scanner.nextLine().trim();
            return Integer.parseInt(linha);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
