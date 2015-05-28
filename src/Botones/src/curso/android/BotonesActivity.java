package curso.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;

public class BotonesActivity extends Activity {

  @Override
  public void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setContentView(R.layout.main);

    // Localizamos los controles
    final TextView mensaje = (TextView) this.findViewById(R.id.Mensaje);
    final Button boton1 = (Button) this.findViewById(R.id.Boton1);
    final ImageButton boton2 = (ImageButton) this.findViewById(R.id.Boton2);
    final ToggleButton boton3 = (ToggleButton) this.findViewById(R.id.Boton3);
    final ToggleButton boton4 = (ToggleButton) this.findViewById(R.id.Boton4);

    // Añadimos los Listener
    this.addBoton1Listener(mensaje, boton1);
    this.addBoton2Listener(mensaje, boton2);
    this.addBoton3Listener(mensaje, boton3);
    this.addBoton4Listener(mensaje, boton4);
  }

  private void addBoton1Listener(final TextView mensaje, final Button boton1) {
    boton1.setOnClickListener(new View.OnClickListener() {
      @SuppressWarnings("nls")
      @Override
      public void onClick(@SuppressWarnings("unused") final View view) {
        mensaje.setText("Botón 1 pulsado!");
      }
    });
  }

  private void addBoton2Listener(final TextView mensaje,
      final ImageButton boton2) {
    boton2.setOnClickListener(new View.OnClickListener() {
      @SuppressWarnings("nls")
      @Override
      public void onClick(@SuppressWarnings("unused") final View arg0) {
        mensaje.setText("Botón 2 pulsado!");
      }
    });
  }

  private void addBoton3Listener(final TextView mensaje,
      final ToggleButton boton3) {
    boton3.setOnClickListener(new View.OnClickListener() {
      @SuppressWarnings("nls")
      @Override
      public void onClick(@SuppressWarnings("unused") final View view) {
        if (boton3.isChecked()) {
          mensaje.setText("Botón 3: ON");
        } else {
          mensaje.setText("Botón 3: OFF");
        }
      }
    });
  }

  private void addBoton4Listener(final TextView mensaje,
      final ToggleButton boton4) {
    boton4.setOnClickListener(new View.OnClickListener() {
      @SuppressWarnings("nls")
      @Override
      public void onClick(@SuppressWarnings("unused") final View arg0) {
        if (boton4.isChecked()) {
          mensaje.setText("Botón 4: ON");
        } else {
          mensaje.setText("Botón 4: OFF");
        }
      }
    });
  }
}