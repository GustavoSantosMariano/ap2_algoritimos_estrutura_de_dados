package ex2_playlist;

/**
 * Nó da lista duplamente encadeada que representa uma música da playlist.
 */
public class NoMusica {
    String titulo;
    String artista;
    String album;
    int duracao; // duração em segundos

    NoMusica anterior;
    NoMusica proximo;

    public NoMusica(String titulo, String artista, String album, int duracao) {
        this.titulo = titulo;
        this.artista = artista;
        this.album = album;
        this.duracao = duracao;
        this.anterior = null;
        this.proximo = null;
    }

    /** Formata a duração (segundos) como mm:ss. */
    public String duracaoFormatada() {
        int min = duracao / 60;
        int seg = duracao % 60;
        return String.format("%02d:%02d", min, seg);
    }

    @Override
    public String toString() {
        return String.format("%-30s | %-20s | %-20s | %s",
                titulo, artista, album, duracaoFormatada());
    }
}
