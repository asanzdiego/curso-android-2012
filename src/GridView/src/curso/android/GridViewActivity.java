package curso.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class GridViewActivity extends Activity {

  final String[] datos = new String[25];

  @Override
  public void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setContentView(R.layout.main);

    for (int i = 0; i < 25; i++) {
      this.datos[i] = "Dato " + (i + 1); //$NON-NLS-1$
    }

    final ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
        android.R.layout.simple_list_item_1, this.datos);

    final TextView mensaje = (TextView) this.findViewById(R.id.Mensaje);

    final GridView opciones = (GridView) this.findViewById(R.id.Opciones);

    opciones.setAdapter(adaptador);
    opciones.setOnItemClickListener(new OnItemClickListener() {

      @SuppressWarnings("nls")
      @Override
      public void onItemClick(
          @SuppressWarnings("unused") final AdapterView<?> parent,
          @SuppressWarnings("unused") final View view, final int position,
          @SuppressWarnings("unused") final long id) {
        mensaje.setText("Seleccionado: " + GridViewActivity.this.datos[position]);
        opciones.setSelection(position);

      }
    });

  }
}
