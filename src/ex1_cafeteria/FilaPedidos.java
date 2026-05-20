package ex1_cafeteria;

/**
 * Fila de Pedidos Pendentes implementada com lista simplesmente ligada.
 * Novos pedidos entram pelo tail (enqueue) e saem pelo head (dequeue).
 */
public class FilaPedidos {

    private No head; // frente da fila
    private No tail; // fim da fila
    private int tamanho;

    public FilaPedidos() {
        this.head = null;
        this.tail = null;
        this.tamanho = 0;
    }

    /** Verifica se a fila está vazia. */
    public boolean isEmpty() {
        return head == null;
    }

    /** Retorna a quantidade de pedidos na fila. */
    public int size() {
        return tamanho;
    }

    /**
     * Adiciona um pedido ao final da fila (enqueue).
     *
     * @param id        identificador único do pedido
     * @param descricao descrição do pedido
     */
    public void enqueue(int id, String descricao) {
        No novoNo = new No(id, descricao);
        if (isEmpty()) {
            head = novoNo;
            tail = novoNo;
        } else {
            tail.proximo = novoNo;
            tail = novoNo;
        }
        tamanho++;
        System.out.println("  ✔ Pedido adicionado à fila: " + novoNo);
    }

    /**
     * Remove e retorna o pedido mais antigo da fila (dequeue).
     *
     * @return o nó removido, ou null se a fila estiver vazia
     */
    public No dequeue() {
        if (isEmpty()) {
            System.out.println("  ✘ Fila vazia! Nenhum pedido para remover.");
            return null;
        }
        No removido = head;
        head = head.proximo;
        if (head == null) {
            // fila ficou vazia: tail também deve ser null
            tail = null;
        }
        removido.proximo = null; // desconecta o nó
        tamanho--;
        return removido;
    }

    /**
     * Insere um nó já existente ao final da fila (usada ao restaurar pedido).
     *
     * @param no nó a ser reinserido
     */
    public void enqueueNo(No no) {
        no.proximo = null;
        if (isEmpty()) {
            head = no;
            tail = no;
        } else {
            tail.proximo = no;
            tail = no;
        }
        tamanho++;
        System.out.println("  ✔ Pedido reinserido na fila: " + no);
    }

    /** Imprime todos os pedidos da fila do mais antigo ao mais recente. */
    public void printQueue() {
        if (isEmpty()) {
            System.out.println("  (Fila vazia)");
            return;
        }
        System.out.println("  --- Fila de Pedidos Pendentes (" + tamanho + " pedido(s)) ---");
        No atual = head;
        int posicao = 1;
        while (atual != null) {
            System.out.println("    " + posicao + ". " + atual);
            atual = atual.proximo;
            posicao++;
        }
        System.out.println("  --------------------------------------------------");
    }
}
