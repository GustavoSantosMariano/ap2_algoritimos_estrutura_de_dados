package ex1_cafeteria;

/**
 * Nó da lista simplesmente ligada utilizado tanto na Fila quanto na Pilha.
 */
public class No {
    int id;
    String descricao;
    No proximo;

    public No(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
        this.proximo = null;
    }

    @Override
    public String toString() {
        return "[Pedido #" + id + "] " + descricao;
    }
}
