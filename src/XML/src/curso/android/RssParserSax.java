package curso.android;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class RssParserSax {

  private URL rssUrl;

  public RssParserSax(final String url) {

    try {
      this.rssUrl = new URL(url);
    } catch (final MalformedURLException e) {
      throw new RuntimeException(e);
    }
  }

  public List<Noticia> parse() {

    final SAXParserFactory factory = SAXParserFactory.newInstance();

    try {

      final SAXParser parser = factory.newSAXParser();
      final RssHandler handler = new RssHandler();
      parser.parse(this.getInputStream(), handler);
      return handler.getNoticias();

    } catch (final Exception e) {
      throw new RuntimeException(e);
    }
  }

  private InputStream getInputStream() {

    try {
      return this.rssUrl.openConnection().getInputStream();
    } catch (final IOException e) {
      throw new RuntimeException(e);
    }
  }
}
