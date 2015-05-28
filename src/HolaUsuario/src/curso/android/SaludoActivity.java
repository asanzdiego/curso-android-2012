package curso.android;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class SaludoActivity extends Activity {

  @Override
  public void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setContentView(R.layout.saludo);

    // Localizamos los controles
    final TextView txtSaludo = (TextView) this.findViewById(R.id.TxtSaludo);

    // Recuperamos la informacion pasada en el intent
    final Bundle bundle = this.getIntent().getExtras();

    // Construimos el mensaje a mostrar
    final String mensaje = this.getString(R.string.hola) + " " //$NON-NLS-1$
        + bundle.getString("NOMBRE"); //$NON-NLS-1$

    // Mostramos el mensaje
    txtSaludo.setText(mensaje);
  }
}
