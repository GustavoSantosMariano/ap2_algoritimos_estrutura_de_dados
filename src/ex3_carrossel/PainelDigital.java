package ex3_carrossel;

import java.util.Scanner;

/**
 * Painel Digital — sistema de controle do carrossel de anúncios.
 * Exercício 3 — Lista Circular Simplesmente Ligada.
 */
public class PainelDigital {

    private static final CarrosselAnuncios carrossel = new CarrosselAnuncios();

    public static void executar(Scanner scanner) {
        System.out.println("\n╔══════════════════════════════════════════════╗");
        System.out.println("║       PAINEL DIGITAL — CARROSSEL             ║");
        System.out.println("╚══════════════════════════════════════════════╝");

        // Carrega anúncios de demonstração
        carregarAnunciosDemostracao();

        boolean rodando = true;
        while (rodando) {
            exibirMenu();
            System.out.print("  Escolha: ");
            int opcao = lerInt(scanner);

            switch (opcao) {
                case 1 -> carrossel.exibirEAvancar();
                case 2 -> menuAdicionar(scanner);
                case 3 -> menuRemover(scanner);
                case 4 -> carrossel.listarCiclo();
                case 5 -> menuExibicaoAutomatica(scanner);
                case 0 -> {
                    System.out.println("\n  Encerrando o Painel Digital...");
                    rodando = false;
                }
                default -> System.out.println("  ✘ Opção inválida. Tente novamente.");
            }
        }
    }

    // ─── Menu ────────────────────────────────────────────────────────────────

    private static void exibirMenu() {
        System.out.println("\n┌─────────────────────────────────────────────┐");
        System.out.println("│  [1] Exibir e Avançar                       │");
        System.out.println("│  [2] Adicionar Anúncio                      │");
        System.out.println("│  [3] Remover Anúncio (por ID)               │");
        System.out.println("│  [4] Listar Ciclo Completo                  │");
        System.out.println("│  [5] Exibição Automática (N anúncios)       │");
        System.out.println("│  [0] Voltar ao menu principal               │");
        System.out.println("└─────────────────────────────────────────────┘");
    }

    // ─── Operações ───────────────────────────────────────────────────────────

    private static void menuAdicionar(Scanner scanner) {
        int id = carrossel.gerarId();
        System.out.print("  Empresa: ");
        String empresa = scanner.nextLine().trim();
        System.out.print("  Descrição: ");
        String descricao = scanner.nextLine().trim();

        System.out.println("  Inserir: [1] Após o atual (próximo a exibir)  [2] No final do ciclo");
        System.out.print("  Onde: ");
        int onde = lerInt(scanner);
        if (onde == 1) {
            carrossel.inserirAposAtual(id, empresa, descricao);
        } else {
            carrossel.inserirNoFinal(id, empresa, descricao);
        }
    }

    private static void menuRemover(Scanner scanner) {
        carrossel.listarCiclo();
        System.out.print("  ID do anúncio a remover: ");
        int id = lerInt(scanner);
        carrossel.removerPorId(id);
    }

    private static void menuExibicaoAutomatica(Scanner scanner) {
        System.out.print("  Quantos anúncios exibir automaticamente? ");
        int n = lerInt(scanner);
        if (n <= 0) {
            System.out.println("  ✘ Valor inválido.");
            return;
        }
        System.out.print("  Pausa entre anúncios (ms, ex: 1000 = 1s): ");
        int pausa = lerInt(scanner);
        carrossel.exibirN(n, Math.max(0, pausa));
    }

    // ─── Dados de demonstração ────────────────────────────────────────────────

    private static void carregarAnunciosDemostracao() {
        carrossel.inserirAposAtual(carrossel.gerarId(), "TechCorp",    "Smartphones de última geração!");
        carrossel.inserirNoFinal(carrossel.gerarId(),   "FoodExpress", "Delivery em 30 minutos ou grátis.");
        carrossel.inserirNoFinal(carrossel.gerarId(),   "MegaStore",   "Liquidação: até 70% de desconto.");
        carrossel.inserirNoFinal(carrossel.gerarId(),   "AutoPeças",   "Revisão completa por R$ 199,90.");
        System.out.println("  ✔ 4 anúncios carregados no carrossel de demonstração.\n");
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
