package curso.android;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TitularesAdapter extends ArrayAdapter<Titular> {

  public TitularesAdapter(final Context context, final Titular[] objects) {
    super(context, R.layout.listitem_titular, objects);
  }

  @Override
  public View getView(final int position, final View convertView,
      @SuppressWarnings("unused") final ViewGroup parent) {

    // Recojemos el titular para esa posición
    final Titular titular = this.getItem(position);

    final TextView titulo;
    final TextView subtitulo;

    View listItem = convertView;

    // si la vista no está 'inflada'
    if (listItem == null) {

      // inflamos la vista desde el xml
      final Activity context = (Activity) this.getContext();
      final LayoutInflater inflater = context.getLayoutInflater();
      listItem = inflater.inflate(R.layout.listitem_titular, null);

      // cogemos los elementos de la vista
      titulo = (TextView) listItem.findViewById(R.id.Titulo);
      subtitulo = (TextView) listItem.findViewById(R.id.Subtitulo);

      // guardamos los elementos de la vista en la propia vista
      listItem.setTag(R.id.Titulo, titulo);
      listItem.setTag(R.id.Subtitulo, subtitulo);

    } else {

      // recuperarmos los elementos de la vista (que ya está inflada)
      titulo = (TextView) listItem.getTag(R.id.Titulo);
      subtitulo = (TextView) listItem.getTag(R.id.Subtitulo);
    }

    // modificamos los textos de la vista (el titulo y el el subtitulo)
    titulo.setText(titular.getTitulo());
    subtitulo.setText(titular.getSubtitulo());

    return listItem;
  }

}
