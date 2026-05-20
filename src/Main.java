import ex1_cafeteria.SistemaCafeteria;
import ex2_playlist.PlayerMusica;
import ex3_carrossel.PainelDigital;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        boolean rodando = true;
        while (rodando) {
            System.out.println("  MENU PRINCIPAL                                  ");
            System.out.println("  [1] Sistema de Cafeteria (Pilha + Fila)         ");
            System.out.println("      → Lista Simplesmente Ligada                 ");
            System.out.println("  [2] Player de Música (Playlist)                ");
            System.out.println("      → Lista Duplamente Encadeada               ");
            System.out.println("  [3] Painel Digital (Carrossel de Anúncios)     ");
            System.out.println("      → Lista Circular Simplesmente Ligada       ");
            System.out.println("  [0] Sair                                        ");
            System.out.print("  Escolha: ");

            int opcao;
            try {
                opcao = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                opcao = -1;
            }

            switch (opcao) {
                case 1 -> SistemaCafeteria.executar(scanner);
                case 2 -> PlayerMusica.executar(scanner);
                case 3 -> PainelDigital.executar(scanner);
                case 0 -> {
                    System.out.println("\n  Encerrando o programa. Até mais!");
                    rodando = false;
                }
                default -> System.out.println("  ✘ Opção inválida. Tente novamente.");
            }
        }
        scanner.close();
    }
}
