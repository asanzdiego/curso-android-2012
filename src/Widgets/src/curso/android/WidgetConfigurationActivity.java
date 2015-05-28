package curso.android;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class WidgetConfigurationActivity extends Activity {

  int widgetId = 0;

  @Override
  public void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setContentView(R.layout.widget_configuration);

    // Obtenemos el Intent que ha lanzado esta ventana
    // y recuperamos sus parámetros
    final Intent intentOrigen = this.getIntent();
    final Bundle params = intentOrigen.getExtras();

    // Obtenemos el ID del widget que se está configurando
    this.widgetId = params.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
        AppWidgetManager.INVALID_APPWIDGET_ID);

    // Obtenemos la referencia a los controles de la pantalla
    final Button btnAceptar = (Button) this.findViewById(R.id.BtnAceptar);
    final Button btnCancelar = (Button) this.findViewById(R.id.BtnCancelar);
    final EditText txtMensaje = (EditText) this.findViewById(R.id.TxtMensaje);

    // Establecemos el resultado por defecto (si se pulsa el botón 'Atrás'
    // del teléfono será este el resultado devuelto).
    this.setResult(Activity.RESULT_CANCELED);

    // Implementación del botón "Cancelar"
    btnCancelar.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(@SuppressWarnings("unused") final View view) {
        // Devolvemos como resultado: CANCELAR (RESULT_CANCELED)
        WidgetConfigurationActivity.this.finish();
      }
    });

    // Implementación del botón "Aceptar"
    btnAceptar.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(@SuppressWarnings("unused") final View view) {

        // Guardamos el mensaje personalizado en las preferencias
        final SharedPreferences prefs = WidgetConfigurationActivity.this
            .getSharedPreferences("WidgetPrefs", Context.MODE_PRIVATE); //$NON-NLS-1$
        final SharedPreferences.Editor editor = prefs.edit();
        editor.putString("msg_" + WidgetConfigurationActivity.this.widgetId, //$NON-NLS-1$
            txtMensaje.getText().toString());
        editor.commit();

        // Actualizamos el widget tras la configuración
        final AppWidgetManager appWidgetManager = AppWidgetManager
            .getInstance(WidgetConfigurationActivity.this);
        MiWidget.actualizarWidget(WidgetConfigurationActivity.this,
            appWidgetManager, WidgetConfigurationActivity.this.widgetId);

        // Devolvemos como resultado: ACEPTAR (RESULT_OK)
        final Intent resultado = new Intent();
        resultado.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
            WidgetConfigurationActivity.this.widgetId);
        WidgetConfigurationActivity.this.setResult(Activity.RESULT_OK,
            resultado);
        WidgetConfigurationActivity.this.finish();
      }
    });
  }
}