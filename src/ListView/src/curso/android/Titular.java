package curso.android;

public class Titular {

  private final String titulo;

  private final String subtitulo;

  public Titular(final String titulo, final String subtitulo) {
    this.titulo = titulo;
    this.subtitulo = subtitulo;
  }

  public String getTitulo() {
    return this.titulo;
  }

  public String getSubtitulo() {
    return this.subtitulo;
  }
}
