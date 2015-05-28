package curso.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class HolaUsuarioActivity extends Activity {

  @Override
  public void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setContentView(R.layout.main);

    // Localizamos los controles
    final EditText txtNombre = (EditText) this.findViewById(R.id.TxtNombre);
    final Button btnHola = (Button) this.findViewById(R.id.BtnHola);

    // Añadimos un Listener
    btnHola.setOnClickListener(new OnClickListener() {

      public void onClick(@SuppressWarnings("unused") final View view) {
        // Creamos el Intent
        final Intent intent = new Intent(HolaUsuarioActivity.this, SaludoActivity.class);

        // Creamos la información a pasar entre actividades
        final Bundle bundle = new Bundle();
        bundle.putString("NOMBRE", txtNombre.getText().toString()); //$NON-NLS-1$

        // Añadimos la información al intent
        intent.putExtras(bundle);

        // Iniciamos la nueva actividad
        HolaUsuarioActivity.this.startActivity(intent);
      }
    });
  }
}