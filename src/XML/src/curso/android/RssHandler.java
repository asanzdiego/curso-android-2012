package curso.android;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class RssHandler extends DefaultHandler {

  private List<Noticia> noticias;
  private Noticia noticiaActual;
  private StringBuilder sbTexto;

  public List<Noticia> getNoticias() {
    return this.noticias;
  }

  @Override
  public void characters(final char[] ch, final int start, final int length)
      throws SAXException {

    super.characters(ch, start, length);

    if (this.noticiaActual != null) {
      this.sbTexto.append(ch, start, length);
    }
  }

  @SuppressWarnings("nls")
  @Override
  public void endElement(final String uri, final String localName,
      final String name) throws SAXException {

    super.endElement(uri, localName, name);

    if (this.noticiaActual != null) {

      if (localName.equals("title")) {
        this.noticiaActual.setTitulo(this.sbTexto.toString());
      } else if (localName.equals("link")) {
        this.noticiaActual.setLink(this.sbTexto.toString());
      } else if (localName.equals("description")) {
        this.noticiaActual.setDescripcion(this.sbTexto.toString());
      } else if (localName.equals("item")) {
        this.noticias.add(this.noticiaActual);
      }

      this.sbTexto.setLength(0);
    }
  }

  @Override
  public void startDocument() throws SAXException {

    super.startDocument();

    this.noticias = new ArrayList<Noticia>();
    this.sbTexto = new StringBuilder();
  }

  @Override
  public void startElement(final String uri, final String localName,
      final String name, final Attributes attributes) throws SAXException {

    super.startElement(uri, localName, name, attributes);

    if (localName.equals("item")) { //$NON-NLS-1$
      this.noticiaActual = new Noticia();
    }
  }
}
