package ex2_playlist;

import java.util.Scanner;

/**
 * Player de Música com suporte a múltiplas playlists.
 * Exercício 2 — Lista Duplamente Encadeada.
 */
public class PlayerMusica {

    // Suporte a até 5 playlists simultâneas
    private static final int MAX_PLAYLISTS = 5;
    private static ListaDuplamenteEncadeada[] playlists = new ListaDuplamenteEncadeada[MAX_PLAYLISTS];
    private static int totalPlaylists = 0;
    private static int playlistAtual = 0;

    public static void executar(Scanner scanner) {
        System.out.println("\n╔══════════════════════════════════════════════╗");
        System.out.println("║         PLAYER DE MÚSICA — PLAYLIST          ║");
        System.out.println("╚══════════════════════════════════════════════╝");

        // Cria playlist padrão
        playlists[0] = new ListaDuplamenteEncadeada("Minha Playlist");
        totalPlaylists = 1;
        carregarMusicasDemostracao();

        boolean rodando = true;
        while (rodando) {
            ListaDuplamenteEncadeada pl = playlists[playlistAtual];
            exibirMenu(pl.getNome());
            System.out.print("  Escolha: ");
            int opcao = lerInt(scanner);

            switch (opcao) {
                case 1  -> pl.proximo();
                case 2  -> pl.anterior();
                case 3  -> menuOrdenar(scanner, pl);
                case 4  -> pl.tocar();
                case 5  -> menuAdicionar(scanner, pl);
                case 6  -> menuRemover(scanner, pl);
                case 7  -> pl.listar();
                case 8  -> menuBuscar(scanner, pl);
                case 9  -> pl.embaralhar();
                case 10 -> menuPlaylists(scanner);
                case 0  -> {
                    System.out.println("\n  Saindo do Player...");
                    rodando = false;
                }
                default -> System.out.println("  ✘ Opção inválida.");
            }
        }
    }

    // ─── Menus auxiliares ─────────────────────────────────────────────────────

    private static void exibirMenu(String nomePl) {
        System.out.println("\n┌─────────────────────────────────────────────┐");
        System.out.println("│  Playlist: " + nomePl);
        System.out.println("├─────────────────────────────────────────────┤");
        System.out.println("│  [1] Próxima música                         │");
        System.out.println("│  [2] Música anterior                        │");
        System.out.println("│  [3] Ordenar playlist                       │");
        System.out.println("│  [4] Tocar música atual                     │");
        System.out.println("│  [5] Adicionar música                       │");
        System.out.println("│  [6] Remover música                         │");
        System.out.println("│  [7] Listar músicas                         │");
        System.out.println("│  [8] Buscar música                          │");
        System.out.println("│  [9] Embaralhar playlist                    │");
        System.out.println("│  [10] Gerenciar playlists                   │");
        System.out.println("│  [0] Voltar ao menu principal               │");
        System.out.println("└─────────────────────────────────────────────┘");
    }

    private static void menuOrdenar(Scanner scanner, ListaDuplamenteEncadeada pl) {
        System.out.println("  [1] Por título  [2] Por artista");
        System.out.print("  Critério: ");
        int c = lerInt(scanner);
        if (c == 1) pl.ordenarPorTitulo();
        else if (c == 2) pl.ordenarPorArtista();
        else System.out.println("  ✘ Opção inválida.");
    }

    private static void menuAdicionar(Scanner scanner, ListaDuplamenteEncadeada pl) {
        System.out.print("  Título: ");    String titulo = scanner.nextLine().trim();
        System.out.print("  Artista: ");   String artista = scanner.nextLine().trim();
        System.out.print("  Álbum: ");     String album = scanner.nextLine().trim();
        System.out.print("  Duração (s): "); int dur = lerInt(scanner);

        System.out.println("  Inserir no: [1] Início  [2] Fim  [3] Posição específica");
        System.out.print("  Onde: ");
        int onde = lerInt(scanner);
        switch (onde) {
            case 1 -> pl.adicionarInicio(titulo, artista, album, dur);
            case 2 -> pl.adicionarFim(titulo, artista, album, dur);
            case 3 -> {
                System.out.print("  Posição (1-" + (pl.size() + 1) + "): ");
                int pos = lerInt(scanner);
                pl.adicionarPosicao(pos, titulo, artista, album, dur);
            }
            default -> System.out.println("  ✘ Opção inválida.");
        }
    }

    private static void menuRemover(Scanner scanner, ListaDuplamenteEncadeada pl) {
        System.out.println("  Remover por: [1] Título  [2] Posição");
        System.out.print("  Opção: ");
        int op = lerInt(scanner);
        if (op == 1) {
            System.out.print("  Título: ");
            pl.removerPorTitulo(scanner.nextLine().trim());
        } else if (op == 2) {
            System.out.print("  Posição: ");
            pl.removerPorPosicao(lerInt(scanner));
        } else {
            System.out.println("  ✘ Opção inválida.");
        }
    }

    private static void menuBuscar(Scanner scanner, ListaDuplamenteEncadeada pl) {
        System.out.print("  Termo de busca: ");
        pl.buscar(scanner.nextLine().trim());
    }

    private static void menuPlaylists(Scanner scanner) {
        System.out.println("\n  --- Gerenciar Playlists ---");
        for (int i = 0; i < totalPlaylists; i++) {
            String sel = (i == playlistAtual) ? " ◀ atual" : "";
            System.out.println("    [" + (i + 1) + "] " + playlists[i].getNome() + sel);
        }
        if (totalPlaylists < MAX_PLAYLISTS) {
            System.out.println("    [" + (totalPlaylists + 1) + "] Criar nova playlist");
        }
        System.out.print("  Escolha: ");
        int op = lerInt(scanner);
        if (op >= 1 && op <= totalPlaylists) {
            playlistAtual = op - 1;
            System.out.println("  ✔ Alterado para: " + playlists[playlistAtual].getNome());
        } else if (op == totalPlaylists + 1 && totalPlaylists < MAX_PLAYLISTS) {
            System.out.print("  Nome da nova playlist: ");
            String nome = scanner.nextLine().trim();
            playlists[totalPlaylists] = new ListaDuplamenteEncadeada(nome);
            playlistAtual = totalPlaylists;
            totalPlaylists++;
            System.out.println("  ✔ Playlist criada: " + nome);
        } else {
            System.out.println("  ✘ Opção inválida.");
        }
    }

    // ─── Dados de demonstração ────────────────────────────────────────────────

    private static void carregarMusicasDemostracao() {
        ListaDuplamenteEncadeada pl = playlists[0];
        pl.adicionarFim("Bohemian Rhapsody",    "Queen",          "A Night at the Opera", 354);
        pl.adicionarFim("Hotel California",      "Eagles",         "Hotel California",     391);
        pl.adicionarFim("Stairway to Heaven",    "Led Zeppelin",   "Led Zeppelin IV",      482);
        pl.adicionarFim("Smells Like Teen Spirit","Nirvana",        "Nevermind",            301);
        pl.adicionarFim("Billie Jean",            "Michael Jackson","Thriller",             294);
        System.out.println("\n  ✔ 5 músicas carregadas na playlist de demonstração.");
    }

    // ─── Utilitário ───────────────────────────────────────────────────────────

    private static int lerInt(Scanner scanner) {
        try {
            String linha = scanner.nextLine().trim();
            return Integer.parseInt(linha);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
