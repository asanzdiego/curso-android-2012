package curso.android;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PreferenciasActivity extends Activity {

  private static final String PREFERENCES_NAME = "MIS PREFERENCIAS"; //$NON-NLS-1$
  private static final String PREFERENCES_KEY = "Preferencia"; //$NON-NLS-1$
  private static final String DEFAULT_VALUE = "No se ha introducido ningún valor aún."; //$NON-NLS-1$

  @Override
  public void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setContentView(R.layout.main);

    // Localizamos los controles
    final EditText preferencia = (EditText) this.findViewById(R.id.Preferencia);
    final Button guardar = (Button) this.findViewById(R.id.Guardar);
    final Button ver = (Button) this.findViewById(R.id.Ver);
    final TextView texto = (TextView) this.findViewById(R.id.Texto);

    // Accedemos a las preferencias
    final SharedPreferences sharedPreferences = this.getSharedPreferences(
        PreferenciasActivity.PREFERENCES_NAME, Context.MODE_PRIVATE);

    // añadimos un 'Listener' al botón 'Guardar'
    guardar.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(@SuppressWarnings("unused") final View view) {

        // recojo el texto escrito en el cuadro de texto
        final String valorPreferencia = preferencia.getText().toString();

        // guardo el valor de la prefencia con una clave
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor
            .putString(PreferenciasActivity.PREFERENCES_KEY, valorPreferencia);
        editor.commit();
      }
    });

    // añadimos un 'Listener' al botón 'Ver'
    ver.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(@SuppressWarnings("unused") final View view) {

        // recojo el valor de la prefencia con una clave
        final String valorPreferencia = sharedPreferences.getString(
            PreferenciasActivity.PREFERENCES_KEY,
            PreferenciasActivity.DEFAULT_VALUE);

        // imprimo por pantalla dicho valor
        texto.setText(valorPreferencia);
      }
    });
  }
}