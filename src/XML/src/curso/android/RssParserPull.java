package curso.android;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

public class RssParserPull {

  private URL rssUrl;

  public RssParserPull(final String url) {

    try {
      this.rssUrl = new URL(url);
    } catch (final MalformedURLException e) {
      throw new RuntimeException(e);
    }
  }

  @SuppressWarnings("nls")
  public List<Noticia> parse() {

    List<Noticia> noticias = new ArrayList<Noticia>();
    final XmlPullParser parser = Xml.newPullParser();

    try {
      parser.setInput(this.getInputStream(), null);

      int evento = parser.getEventType();

      Noticia noticiaActual = null;

      while (evento != XmlPullParser.END_DOCUMENT) {
        String etiqueta = null;

        switch (evento) {
          case XmlPullParser.START_DOCUMENT:

            noticias = new ArrayList<Noticia>();
            break;

          case XmlPullParser.START_TAG:

            etiqueta = parser.getName();

            if (etiqueta.equals("item")) {
              noticiaActual = new Noticia();
            } else if (noticiaActual != null) {
              if (etiqueta.equals("link")) {
                noticiaActual.setLink(parser.nextText());
              } else if (etiqueta.equals("description")) {
                noticiaActual.setDescripcion(parser.nextText());
              } else if (etiqueta.equals("title")) {
                noticiaActual.setTitulo(parser.nextText());
              }
            }
            break;

          case XmlPullParser.END_TAG:

            etiqueta = parser.getName();

            if (etiqueta.equals("item") && (noticiaActual != null)) {
              noticias.add(noticiaActual);
            }
            break;
        }

        evento = parser.next();
      }
    } catch (final Exception ex) {

      throw new RuntimeException(ex);
    }

    return noticias;
  }

  private InputStream getInputStream() {

    try {
      return this.rssUrl.openConnection().getInputStream();
    } catch (final IOException e) {
      throw new RuntimeException(e);
    }
  }
}
