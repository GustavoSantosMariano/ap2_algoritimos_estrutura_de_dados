package ex3_carrossel;

/**
 * Carrossel de Anúncios implementado com Lista Circular Simplesmente Ligada.
 *
 * Invariante: o último nó da lista aponta para o primeiro (proximo != null),
 * exceto quando a lista está vazia (anuncioAtual == null).
 */
public class CarrosselAnuncios {

    private NoAnuncio anuncioAtual; // ponteiro de exibição atual
    private int tamanho;
    private int proximoId;

    public CarrosselAnuncios() {
        this.anuncioAtual = null;
        this.tamanho = 0;
        this.proximoId = 1;
    }

    public boolean isEmpty() { return anuncioAtual == null; }
    public int size() { return tamanho; }
    public int gerarId() { return proximoId++; }

    // ─── Exibir e Avançar ────────────────────────────────────────────────────

    /**
     * Exibe os dados do anúncio atual, incrementa o contador de exibições
     * e avança o ponteiro para o próximo nó do ciclo.
     */
    public void exibirEAvancar() {
        if (isEmpty()) {
            System.out.println("  ✘ Nenhum anúncio cadastrado.");
            return;
        }
        System.out.println("\n  ┌──────────────────────────────────────────┐");
        System.out.println("  │  PAINEL DIGITAL — ANÚNCIO EM EXIBIÇÃO   │");
        System.out.println("  ├──────────────────────────────────────────┤");
        System.out.println("  │  ID       : " + anuncioAtual.id);
        System.out.println("  │  Empresa  : " + anuncioAtual.empresa);
        System.out.println("  │  Descrição: " + anuncioAtual.descricao);
        System.out.println("  └──────────────────────────────────────────┘");

        anuncioAtual.contadorExibicoes++;
        anuncioAtual = anuncioAtual.proximo; // avança no ciclo
    }

    // ─── Inserção ─────────────────────────────────────────────────────────────

    /**
     * Insere um novo anúncio logo após o anúncio atual.
     * Caso especial: lista vazia → nó aponta para si mesmo.
     */
    public void inserirAposAtual(int id, String empresa, String descricao) {
        NoAnuncio novo = new NoAnuncio(id, empresa, descricao);
        if (isEmpty()) {
            novo.proximo = novo; // aponta para si mesmo — fechando o ciclo
            anuncioAtual = novo;
        } else {
            novo.proximo = anuncioAtual.proximo;
            anuncioAtual.proximo = novo;
        }
        tamanho++;
        System.out.println("  ✔ Anúncio inserido após o atual: " + novo);
    }

    /**
     * Insere um novo anúncio no "final" lógico da lista circular,
     * ou seja, logo antes do anúncio atual (de modo que o novo seja
     * o último a ser exibido antes do ciclo reiniciar).
     */
    public void inserirNoFinal(int id, String empresa, String descricao) {
        NoAnuncio novo = new NoAnuncio(id, empresa, descricao);
        if (isEmpty()) {
            novo.proximo = novo;
            anuncioAtual = novo;
            tamanho++;
            System.out.println("  ✔ Anúncio inserido (lista vazia): " + novo);
            return;
        }
        // Percorre até o nó cujo proximo == anuncioAtual (predecessor do atual)
        NoAnuncio predecessor = anuncioAtual;
        while (predecessor.proximo != anuncioAtual) {
            predecessor = predecessor.proximo;
        }
        novo.proximo = anuncioAtual;
        predecessor.proximo = novo;
        tamanho++;
        System.out.println("  ✔ Anúncio inserido no final do ciclo: " + novo);
    }

    // ─── Remoção ──────────────────────────────────────────────────────────────

    /**
     * Remove o anúncio com o ID especificado.
     * Casos críticos:
     * - Único anúncio: deixa a lista vazia.
     * - Anúncio sendo exibido (anuncioAtual): redireciona para o próximo.
     *
     * @param id identificador do anúncio a remover
     * @return true se removido, false se não encontrado
     */
    public boolean removerPorId(int id) {
        if (isEmpty()) {
            System.out.println("  ✘ Lista vazia.");
            return false;
        }

        // Caso com um único nó
        if (tamanho == 1) {
            if (anuncioAtual.id == id) {
                System.out.println("  ✔ Anúncio removido (era o único): " + anuncioAtual);
                anuncioAtual = null;
                tamanho--;
                return true;
            } else {
                System.out.println("  ✘ Anúncio ID " + id + " não encontrado.");
                return false;
            }
        }

        // Busca o predecessor do nó a remover percorrendo o ciclo
        // Começa do anuncioAtual e dá no máximo 'tamanho' passos
        NoAnuncio predecessor = anuncioAtual;
        for (int i = 0; i < tamanho; i++) {
            if (predecessor.proximo.id == id) {
                // predecessor.proximo é o nó a remover
                NoAnuncio alvo = predecessor.proximo;
                predecessor.proximo = alvo.proximo; // reconecta o ciclo

                // Se o nó removido era o atual, redireciona para o próximo
                if (alvo == anuncioAtual) {
                    anuncioAtual = alvo.proximo;
                }
                alvo.proximo = null;
                tamanho--;
                System.out.println("  ✔ Anúncio removido: " + alvo);
                return true;
            }
            predecessor = predecessor.proximo;
        }
        System.out.println("  ✘ Anúncio ID " + id + " não encontrado.");
        return false;
    }

    // ─── Listar Ciclo Completo ────────────────────────────────────────────────

    /**
     * Percorre o ciclo exatamente uma vez a partir do anúncio atual,
     * imprimindo cada nó e parando ao retornar ao ponto de partida.
     */
    public void listarCiclo() {
        if (isEmpty()) {
            System.out.println("  (Nenhum anúncio cadastrado)");
            return;
        }
        System.out.println("\n  --- Ciclo Completo de Anúncios (" + tamanho + " anúncio(s)) ---");
        NoAnuncio p = anuncioAtual;
        int pos = 1;
        do {
            String marcador = (pos == 1) ? " ◀ exibindo agora" : "";
            System.out.println("    " + pos + ". " + p + marcador);
            p = p.proximo;
            pos++;
        } while (p != anuncioAtual);
        System.out.println("  --- (volta ao início do ciclo) ---");
    }

    // ─── Exibição automática de N anúncios (Bônus) ───────────────────────────

    /**
     * Exibe N anúncios consecutivos automaticamente com uma pausa simulada.
     *
     * @param n       número de exibições consecutivas
     * @param pausaMs pausa em milissegundos entre exibições
     */
    public void exibirN(int n, int pausaMs) {
        if (isEmpty()) {
            System.out.println("  ✘ Nenhum anúncio cadastrado.");
            return;
        }
        System.out.println("\n  ► Exibição automática de " + n + " anúncio(s)...");
        for (int i = 0; i < n; i++) {
            System.out.println("\n  [" + (i + 1) + "/" + n + "]");
            exibirEAvancar();
            if (i < n - 1 && pausaMs > 0) {
                try {
                    Thread.sleep(pausaMs);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
        System.out.println("\n  ► Exibição automática concluída.");
    }
}
