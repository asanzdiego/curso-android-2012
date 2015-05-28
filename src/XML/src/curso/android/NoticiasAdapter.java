package curso.android;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class NoticiasAdapter extends ArrayAdapter<Noticia> {

  public NoticiasAdapter(final Context context, final List<Noticia> noticias) {
    super(context, R.layout.listitem_noticia, noticias);
  }

  @SuppressWarnings("nls")
  @Override
  public View getView(final int position, final View convertView,
      @SuppressWarnings("unused") final ViewGroup parent) {

    // Recojemos la noticia para esa posición
    final Noticia noticia = this.getItem(position);

    final TextView titulo;
    final TextView descripcion;

    View listItem = convertView;

    // si la vista no está 'inflada'
    if (listItem == null) {

      // inflamos la vista desde el xml
      final Activity context = (Activity) this.getContext();
      final LayoutInflater inflater = context.getLayoutInflater();
      listItem = inflater.inflate(R.layout.listitem_noticia, null);

      // cogemos los elementos de la vista
      titulo = (TextView) listItem.findViewById(R.id.Titulo);
      descripcion = (TextView) listItem.findViewById(R.id.Descripcion);

      // guardamos los elementos de la vista en la propia vista
      listItem.setTag(R.id.Titulo, titulo);
      listItem.setTag(R.id.Descripcion, descripcion);

    } else {

      // recuperarmos los elementos de la vista (que ya está inflada)
      titulo = (TextView) listItem.getTag(R.id.Titulo);
      descripcion = (TextView) listItem.getTag(R.id.Descripcion);
    }

    // modificamos los textos de la vista (el titulo y el el subtitulo)
    final String enlace = "<a href='" + noticia.getLink() + "'>"
        + noticia.getTitulo() + "</a>";
    titulo.setText(Html.fromHtml(enlace));
    titulo.setMovementMethod(LinkMovementMethod.getInstance());
    descripcion.setText(noticia.getDescripcion());

    return listItem;
  }

}
