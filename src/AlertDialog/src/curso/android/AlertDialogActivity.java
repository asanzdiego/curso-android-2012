package curso.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AlertDialogActivity extends Activity {

  @Override
  public void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setContentView(R.layout.main);

    final Button mostrarAlerta = (Button) this.findViewById(R.Id.MostrarAlerta);

    mostrarAlerta.setOnClickListener(new OnClickListener() {

      @SuppressWarnings("nls")
      @Override
      public void onClick(@SuppressWarnings("unused") final View view) {

        final AlertDialog.Builder alert = new AlertDialog.Builder(
            AlertDialogActivity.this);
        alert.setIcon(R.drawable.icon);
        alert.setTitle("Alerta!");
        alert.setMessage("Este es el texto de la alerta");
        alert.setPositiveButton(android.R.string.ok, null);
        alert.show();
      }
    });
  }
}