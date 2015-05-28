package curso.android;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;

import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.sax.StartElementListener;
import android.util.Xml;

public class RssParserSax2 {

  private URL rssUrl;
  Noticia noticiaActual;

  public RssParserSax2(final String url) {

    try {
      this.rssUrl = new URL(url);
    } catch (final MalformedURLException e) {
      throw new RuntimeException(e);
    }
  }

  @SuppressWarnings("nls")
  public List<Noticia> parse() {

    final List<Noticia> noticias = new ArrayList<Noticia>();

    final RootElement root = new RootElement("rss");
    final Element channel = root.getChild("channel");
    final Element item = channel.getChild("item");

    item.setStartElementListener(new StartElementListener() {
      public void start(@SuppressWarnings("unused") final Attributes attrs) {
        RssParserSax2.this.noticiaActual = new Noticia();
      }
    });

    item.setEndElementListener(new EndElementListener() {
      public void end() {
        noticias.add(RssParserSax2.this.noticiaActual);
      }
    });

    item.getChild("title").setEndTextElementListener(
        new EndTextElementListener() {
          public void end(final String body) {
            RssParserSax2.this.noticiaActual.setTitulo(body);
          }
        });

    item.getChild("link").setEndTextElementListener(
        new EndTextElementListener() {
          public void end(final String body) {
            RssParserSax2.this.noticiaActual.setLink(body);
          }
        });

    item.getChild("description").setEndTextElementListener(
        new EndTextElementListener() {
          public void end(final String body) {
            RssParserSax2.this.noticiaActual.setDescripcion(body);
          }
        });

    try {

      Xml.parse(this.getInputStream(), Xml.Encoding.UTF_8, root
          .getContentHandler());
    } catch (final Exception e) {
      throw new RuntimeException(e);
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
