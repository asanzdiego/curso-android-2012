package curso.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class MainActivity extends Activity {

  @SuppressWarnings("nls")
  final String[] parseos = new String[] { "Volver", "SAX Clásico",
      "SAX Simplificado", "DOM", "XML Pull" };

  @Override
  public void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setContentView(R.layout.main);

    // montamos el spinner con las opciones del parser
    final ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
        android.R.layout.simple_spinner_item, this.parseos);
    adaptador
        .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

    final Spinner opciones = (Spinner) this.findViewById(R.id.Opciones);
    opciones.setAdapter(adaptador);

    // añadimos un Listener al spinner
    opciones.setOnItemSelectedListener(new OnItemSelectedListener() {

      @Override
      public void onItemSelected(
          @SuppressWarnings("unused") final AdapterView<?> parent,
          @SuppressWarnings("unused") final View view, final int position,
          @SuppressWarnings("unused") final long id) {

        if (position > 0) {

          // Creamos el Intent
          final Intent intent = new Intent(MainActivity.this,
              NoticiasActivity.class);

          // Creamos la información a pasar entre actividades
          final Bundle bundle = new Bundle();
          bundle.putInt("PARSER", position); //$NON-NLS-1$

          // Añadimos la información al intent
          intent.putExtras(bundle);

          // Iniciamos la nueva actividad
          MainActivity.this.startActivity(intent);
        }

      }

      @Override
      public void onNothingSelected(
          @SuppressWarnings("unused") final AdapterView<?> parent) {
        // TODO Auto-generated method stub
      }

    });

  }
}