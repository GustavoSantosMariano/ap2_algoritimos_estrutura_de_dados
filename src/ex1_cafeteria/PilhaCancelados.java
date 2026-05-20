package ex1_cafeteria;

/**
 * Pilha de Pedidos Cancelados implementada com lista simplesmente ligada.
 * O topo da pilha corresponde ao head da lista (LIFO).
 */
public class PilhaCancelados {

    private No topo; // topo da pilha
    private int tamanho;

    public PilhaCancelados() {
        this.topo = null;
        this.tamanho = 0;
    }

    /** Verifica se a pilha está vazia. */
    public boolean isEmpty() {
        return topo == null;
    }

    /** Retorna a quantidade de pedidos cancelados. */
    public int size() {
        return tamanho;
    }

    /**
     * Empilha um pedido cancelado (push).
     *
     * @param no o nó do pedido cancelado
     */
    public void push(No no) {
        no.proximo = topo;
        topo = no;
        tamanho++;
        System.out.println("  ✔ Pedido adicionado à pilha de cancelados: " + no);
    }

    /**
     * Desempilha o pedido cancelado mais recente (pop).
     *
     * @return o nó removido, ou null se a pilha estiver vazia
     */
    public No pop() {
        if (isEmpty()) {
            System.out.println("  ✘ Pilha vazia! Nenhum pedido cancelado para restaurar.");
            return null;
        }
        No removido = topo;
        topo = topo.proximo;
        removido.proximo = null;
        tamanho--;
        return removido;
    }

    /** Imprime todos os pedidos cancelados do mais recente ao mais antigo. */
    public void printStack() {
        if (isEmpty()) {
            System.out.println("  (Pilha vazia — nenhum pedido cancelado)");
            return;
        }
        System.out.println("  --- Pilha de Pedidos Cancelados (" + tamanho + " pedido(s)) ---");
        No atual = topo;
        int posicao = 1;
        while (atual != null) {
            System.out.println("    " + posicao + ". " + atual + " (cancelado mais recente)".repeat(posicao == 1 ? 1 : 0));
            atual = atual.proximo;
            posicao++;
        }
        System.out.println("  --------------------------------------------------");
    }
}
