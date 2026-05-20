package ex2_playlist;

/**
 * Lista Duplamente Encadeada que representa uma Playlist de músicas.
 * Suporta inserção em qualquer posição, remoção, navegação bidirecional,
 * ordenação (bubble sort) e busca linear.
 */
public class ListaDuplamenteEncadeada {

    private NoMusica head;
    private NoMusica tail;
    private NoMusica atual;   // música em destaque / reproduzindo
    private int tamanho;
    private String nomePlaylist;

    public ListaDuplamenteEncadeada(String nome) {
        this.nomePlaylist = nome;
        this.head = null;
        this.tail = null;
        this.atual = null;
        this.tamanho = 0;
    }

    public String getNome() { return nomePlaylist; }
    public boolean isEmpty() { return head == null; }
    public int size() { return tamanho; }

    // ─── Inserção ─────────────────────────────────────────────────────────────

    /** Insere uma nova música no início da lista. */
    public void adicionarInicio(String titulo, String artista, String album, int duracao) {
        NoMusica novo = new NoMusica(titulo, artista, album, duracao);
        if (isEmpty()) {
            head = novo;
            tail = novo;
            atual = novo;
        } else {
            novo.proximo = head;
            head.anterior = novo;
            head = novo;
        }
        tamanho++;
        System.out.println("  ✔ Música adicionada no início: " + titulo);
    }

    /** Insere uma nova música no final da lista. */
    public void adicionarFim(String titulo, String artista, String album, int duracao) {
        NoMusica novo = new NoMusica(titulo, artista, album, duracao);
        if (isEmpty()) {
            head = novo;
            tail = novo;
            atual = novo;
        } else {
            novo.anterior = tail;
            tail.proximo = novo;
            tail = novo;
        }
        tamanho++;
        System.out.println("  ✔ Música adicionada no final: " + titulo);
    }

    /**
     * Insere uma música em uma posição específica (1-based).
     * Posição 1 equivale ao início, posição > tamanho equivale ao final.
     */
    public void adicionarPosicao(int posicao, String titulo, String artista, String album, int duracao) {
        if (posicao <= 1) {
            adicionarInicio(titulo, artista, album, duracao);
            return;
        }
        if (posicao > tamanho) {
            adicionarFim(titulo, artista, album, duracao);
            return;
        }
        // Percorre até o nó na posição-1
        NoMusica anterior = head;
        for (int i = 1; i < posicao - 1; i++) {
            anterior = anterior.proximo;
        }
        NoMusica novo = new NoMusica(titulo, artista, album, duracao);
        NoMusica posterior = anterior.proximo;

        novo.anterior = anterior;
        novo.proximo = posterior;
        anterior.proximo = novo;
        if (posterior != null) {
            posterior.anterior = novo;
        }
        tamanho++;
        System.out.println("  ✔ Música adicionada na posição " + posicao + ": " + titulo);
    }

    // ─── Remoção ──────────────────────────────────────────────────────────────

    /** Remove a música pelo título (primeira ocorrência). Retorna true se removida. */
    public boolean removerPorTitulo(String titulo) {
        if (isEmpty()) {
            System.out.println("  ✘ Playlist vazia.");
            return false;
        }
        NoMusica p = head;
        while (p != null) {
            if (p.titulo.equalsIgnoreCase(titulo)) {
                return removerNo(p);
            }
            p = p.proximo;
        }
        System.out.println("  ✘ Música não encontrada: " + titulo);
        return false;
    }

    /** Remove a música pela posição (1-based). Retorna true se removida. */
    public boolean removerPorPosicao(int posicao) {
        if (isEmpty() || posicao < 1 || posicao > tamanho) {
            System.out.println("  ✘ Posição inválida.");
            return false;
        }
        NoMusica p = head;
        for (int i = 1; i < posicao; i++) {
            p = p.proximo;
        }
        return removerNo(p);
    }

    /** Remove um nó da lista, atualizando todos os ponteiros. */
    private boolean removerNo(NoMusica no) {
        // Se o nó removido é o atual, avança para o próximo (ou anterior)
        if (no == atual) {
            if (no.proximo != null) {
                atual = no.proximo;
            } else if (no.anterior != null) {
                atual = no.anterior;
            } else {
                atual = null; // lista ficará vazia
            }
        }

        if (no.anterior != null) {
            no.anterior.proximo = no.proximo;
        } else {
            head = no.proximo; // era o head
        }

        if (no.proximo != null) {
            no.proximo.anterior = no.anterior;
        } else {
            tail = no.anterior; // era o tail
        }

        no.anterior = null;
        no.proximo = null;
        tamanho--;
        System.out.println("  ✔ Música removida: " + no.titulo);
        return true;
    }

    // ─── Navegação ────────────────────────────────────────────────────────────

    /** Avança para a próxima música. */
    public void proximo() {
        if (isEmpty()) {
            System.out.println("  ✘ Playlist vazia.");
            return;
        }
        if (atual != null && atual.proximo != null) {
            atual = atual.proximo;
            System.out.println("  ▶ Avançando para: " + atual.titulo);
        } else {
            System.out.println("  ✘ Já está na última música da playlist.");
        }
    }

    /** Volta para a música anterior. */
    public void anterior() {
        if (isEmpty()) {
            System.out.println("  ✘ Playlist vazia.");
            return;
        }
        if (atual != null && atual.anterior != null) {
            atual = atual.anterior;
            System.out.println("  ◀ Voltando para: " + atual.titulo);
        } else {
            System.out.println("  ✘ Já está na primeira música da playlist.");
        }
    }

    // ─── Reprodução ───────────────────────────────────────────────────────────

    /** Exibe os detalhes da música atual e simula a reprodução. */
    public void tocar() {
        if (isEmpty() || atual == null) {
            System.out.println("  ✘ Nenhuma música selecionada.");
            return;
        }
        System.out.println("\n  ♪ ─────────── REPRODUZINDO ────────────── ♪");
        System.out.println("    Título  : " + atual.titulo);
        System.out.println("    Artista : " + atual.artista);
        System.out.println("    Álbum   : " + atual.album);
        System.out.println("    Duração : " + atual.duracaoFormatada());
        System.out.println("  ♪ ─────────────────────────────────────── ♪");
    }

    // ─── Ordenação (Bubble Sort) ──────────────────────────────────────────────

    /** Ordena a playlist por título (bubble sort — troca apenas os dados, não os nós). */
    public void ordenarPorTitulo() {
        if (tamanho <= 1) return;
        boolean trocou;
        do {
            trocou = false;
            NoMusica p = head;
            while (p != null && p.proximo != null) {
                if (p.titulo.compareToIgnoreCase(p.proximo.titulo) > 0) {
                    trocarDados(p, p.proximo);
                    trocou = true;
                }
                p = p.proximo;
            }
        } while (trocou);
        System.out.println("  ✔ Playlist ordenada por título.");
    }

    /** Ordena a playlist por artista (bubble sort). */
    public void ordenarPorArtista() {
        if (tamanho <= 1) return;
        boolean trocou;
        do {
            trocou = false;
            NoMusica p = head;
            while (p != null && p.proximo != null) {
                if (p.artista.compareToIgnoreCase(p.proximo.artista) > 0) {
                    trocarDados(p, p.proximo);
                    trocou = true;
                }
                p = p.proximo;
            }
        } while (trocou);
        System.out.println("  ✔ Playlist ordenada por artista.");
    }

    /** Troca apenas os campos de dado entre dois nós adjacentes (sem mover ponteiros). */
    private void trocarDados(NoMusica a, NoMusica b) {
        // Se 'atual' aponta para um dos nós, mantemos apontando para o mesmo nó (sem perda)
        String tmpTitulo = a.titulo;   a.titulo = b.titulo;   b.titulo = tmpTitulo;
        String tmpArtista = a.artista; a.artista = b.artista; b.artista = tmpArtista;
        String tmpAlbum = a.album;     a.album = b.album;     b.album = tmpAlbum;
        int tmpDuracao = a.duracao;    a.duracao = b.duracao; b.duracao = tmpDuracao;
    }

    // ─── Busca ────────────────────────────────────────────────────────────────

    /**
     * Busca músicas pelo termo informado, procurando em título, artista e álbum.
     *
     * @param termo palavra-chave a buscar
     */
    public void buscar(String termo) {
        if (isEmpty()) {
            System.out.println("  ✘ Playlist vazia.");
            return;
        }
        String termoBusca = termo.toLowerCase();
        boolean encontrou = false;
        NoMusica p = head;
        int pos = 1;
        System.out.println("  --- Resultados para \"" + termo + "\" ---");
        while (p != null) {
            if (p.titulo.toLowerCase().contains(termoBusca)
                    || p.artista.toLowerCase().contains(termoBusca)
                    || p.album.toLowerCase().contains(termoBusca)) {
                System.out.println("    [" + pos + "] " + p);
                encontrou = true;
            }
            p = p.proximo;
            pos++;
        }
        if (!encontrou) {
            System.out.println("  (Nenhum resultado encontrado)");
        }
    }

    // ─── Listagem ─────────────────────────────────────────────────────────────

    /** Lista todas as músicas da playlist com seus detalhes. */
    public void listar() {
        if (isEmpty()) {
            System.out.println("  (Playlist vazia)");
            return;
        }
        System.out.println("\n  Playlist: " + nomePlaylist + " (" + tamanho + " música(s))");
        System.out.println("  " + "─".repeat(85));
        System.out.printf("  %-4s %-30s %-20s %-20s %s%n", "#", "Título", "Artista", "Álbum", "Duração");
        System.out.println("  " + "─".repeat(85));
        NoMusica p = head;
        int pos = 1;
        while (p != null) {
            String marcador = (p == atual) ? " ▶" : "  ";
            System.out.printf("  %-4s%-30s %-20s %-20s %s%n",
                    marcador + pos, p.titulo, p.artista, p.album, p.duracaoFormatada());
            p = p.proximo;
            pos++;
        }
        System.out.println("  " + "─".repeat(85));
        if (atual != null) {
            System.out.println("  ▶ Música atual: " + atual.titulo);
        }
    }

    // ─── Modo Aleatório (Bônus) ───────────────────────────────────────────────

    /**
     * Embaralha a ordem das músicas da playlist usando Fisher-Yates adaptado
     * para lista ligada (coleta array de nós, embaralha dados, reassina).
     */
    public void embaralhar() {
        if (tamanho <= 1) return;
        // Coleta todos os nós em array
        NoMusica[] nos = new NoMusica[tamanho];
        NoMusica p = head;
        for (int i = 0; i < tamanho; i++) {
            nos[i] = p;
            p = p.proximo;
        }
        // Fisher-Yates com java.util.Random (apenas para índice — não é estrutura de dados)
        java.util.Random rng = new java.util.Random();
        for (int i = tamanho - 1; i > 0; i--) {
            int j = rng.nextInt(i + 1);
            // Troca dados (não nós)
            trocarDados(nos[i], nos[j]);
        }
        System.out.println("  ✔ Playlist embaralhada aleatoriamente.");
    }
}
