package ex3_carrossel;

/**
 * Nó da lista circular simplesmente ligada que representa um anúncio.
 * O ponteiro 'proximo' nunca aponta para null; no último nó ele aponta
 * de volta para o primeiro, fechando o ciclo.
 */
public class NoAnuncio {
    int id;
    String empresa;
    String descricao;
    int contadorExibicoes; // bônus: quantas vezes este anúncio foi exibido
    NoAnuncio proximo;

    public NoAnuncio(int id, String empresa, String descricao) {
        this.id = id;
        this.empresa = empresa;
        this.descricao = descricao;
        this.contadorExibicoes = 0;
        this.proximo = null;
    }

    @Override
    public String toString() {
        return String.format("[ID:%d | %s] %s (exibido %d vez(es))",
                id, empresa, descricao, contadorExibicoes);
    }
}
