package curso.android;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class NoticiasActivity extends Activity {

  @Override
  public void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setContentView(R.layout.noticias);

    // Recuperamos el ID del parser desde el intent
    final Bundle bundle = this.getIntent().getExtras();
    final int parser = bundle.getInt("PARSER"); //$NON-NLS-1$

    final List<Noticia> noticiasRSS;
    final String url = "http://212.170.237.10/rss/rss.aspx"; //$NON-NLS-1$
    final String nombreParser;

    switch (parser) {
      case 1:
        nombreParser = "PARSER = SAX Clásico"; //$NON-NLS-1$
        final RssParserSax saxparser = new RssParserSax(url);
        noticiasRSS = saxparser.parse();
        break;
      case 2:
        nombreParser = "PARSER = SAX Simplificado"; //$NON-NLS-1$
        final RssParserSax2 saxparser2 = new RssParserSax2(url);
        noticiasRSS = saxparser2.parse();
        break;
      case 3:
        nombreParser = "PARSER = DOM"; //$NON-NLS-1$
        final RssParserDom domparser = new RssParserDom(url);
        noticiasRSS = domparser.parse();
        break;
      default:
        nombreParser = "PARSER = XML Pull"; //$NON-NLS-1$
        final RssParserPull pullparser = new RssParserPull(url);
        noticiasRSS = pullparser.parse();
        break;
    }

    // Pintamos el nombre del parser
    final TextView parserView = (TextView) this.findViewById(R.id.Parser);
    parserView.setText(nombreParser);

    // Pintamos las noticias
    final NoticiasAdapter adaptador = new NoticiasAdapter(this, noticiasRSS);
    final ListView noticias = (ListView) this.findViewById(R.id.Noticias);
    noticias.setAdapter(adaptador);
  }
}
