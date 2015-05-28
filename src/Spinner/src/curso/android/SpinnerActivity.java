package curso.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class SpinnerActivity extends Activity {

  @SuppressWarnings("nls")
  final String[] datos = new String[] { "Elem1", "Elem2", "Elem3", "Elem4",
      "Elem5" };

  @Override
  public void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setContentView(R.layout.main);

    final TextView mensaje = (TextView) this.findViewById(R.id.Mensaje);

    final ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
        android.R.layout.simple_spinner_item, this.datos);
    adaptador
        .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    final Spinner opciones = (Spinner) this.findViewById(R.id.Opciones);
    opciones.setAdapter(adaptador);

    opciones.setOnItemSelectedListener(new OnItemSelectedListener() {

      @SuppressWarnings("nls")
      @Override
      public void onItemSelected(
          @SuppressWarnings("unused") final AdapterView<?> parent,
          @SuppressWarnings("unused") final View view, final int position,
          @SuppressWarnings("unused") final long id) {
        mensaje.setText("Seleccionado: " + SpinnerActivity.this.datos[position]);
      }

      @SuppressWarnings("nls")
      @Override
      public void onNothingSelected(
          @SuppressWarnings("unused") final AdapterView<?> parent) {
        mensaje.setText("");
      }

    });
  }
}