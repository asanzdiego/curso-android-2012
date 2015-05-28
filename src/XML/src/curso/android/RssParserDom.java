package curso.android;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class RssParserDom {
  private URL rssUrl;

  public RssParserDom(final String url) {

    try {
      this.rssUrl = new URL(url);
    } catch (final MalformedURLException e) {
      throw new RuntimeException(e);
    }
  }

  @SuppressWarnings("nls")
  public List<Noticia> parse() {

    final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    final List<Noticia> noticias = new ArrayList<Noticia>();

    try {

      final DocumentBuilder builder = factory.newDocumentBuilder();
      final Document dom = builder.parse(this.getInputStream());
      final Element root = dom.getDocumentElement();
      final NodeList items = root.getElementsByTagName("item");

      for (int i = 0; i < items.getLength(); i++) {

        final Noticia noticia = new Noticia();

        final Node item = items.item(i);
        final NodeList datosNoticia = item.getChildNodes();

        for (int j = 0; j < datosNoticia.getLength(); j++) {

          final Node dato = datosNoticia.item(j);
          final String etiqueta = dato.getNodeName();

          if (etiqueta.equals("title")) {
            noticia.setTitulo(this.obtenerTexto(dato));
          } else if (etiqueta.equals("link")) {
            noticia.setLink(this.obtenerTexto(dato));
          } else if (etiqueta.equals("description")) {
            noticia.setDescripcion(this.obtenerTexto(dato));
          }
        }

        noticias.add(noticia);
      }
    } catch (final Exception ex) {
      throw new RuntimeException(ex);
    }

    return noticias;
  }

  private String obtenerTexto(final Node dato) {

    final StringBuilder texto = new StringBuilder();

    final NodeList fragmentos = dato.getChildNodes();

    for (int k = 0; k < fragmentos.getLength(); k++) {
      texto.append(fragmentos.item(k).getNodeValue());
    }

    return texto.toString();
  }

  private InputStream getInputStream() {

    try {
      return this.rssUrl.openConnection().getInputStream();
    } catch (final IOException e) {
      throw new RuntimeException(e);
    }
  }
}
