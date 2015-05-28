package curso.android;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

public class MiWidget extends AppWidgetProvider {

  @Override
  public void onUpdate(final Context context,
      final AppWidgetManager appWidgetManager, final int[] appWidgetIds) {

    // Iteramos la lista de widgets en ejecución
    for (final int widgetId : appWidgetIds) {
      // Actualizamos el widget actual
      MiWidget.actualizarWidget(context, appWidgetManager, widgetId);
    }
  }

  @Override
  public void onDeleted(final Context context, final int[] appWidgetIds) {

    // Accedemos a las preferencias de la aplicación
    final SharedPreferences prefs = context.getSharedPreferences("WidgetPrefs", //$NON-NLS-1$
        Context.MODE_PRIVATE);
    final SharedPreferences.Editor editor = prefs.edit();

    // Eliminamos las preferencias de los widgets borrados
    for (final int widgetId : appWidgetIds) {
      editor.remove("msg_" + widgetId); //$NON-NLS-1$
    }

    // Aceptamos los cambios
    editor.commit();

    super.onDeleted(context, appWidgetIds);
  }

  @Override
  public void onReceive(final Context context, final Intent intent) {

    if (intent.getAction().equals("curso.android.ACTUALIZAR_WIDGET")) { //$NON-NLS-1$

      // Obtenemos el ID del widget a actualizar
      final int widgetId = intent.getIntExtra(
          AppWidgetManager.EXTRA_APPWIDGET_ID,
          AppWidgetManager.INVALID_APPWIDGET_ID);

      // Obtenemos el widget manager de nuestro contexto
      final AppWidgetManager widgetManager = AppWidgetManager
          .getInstance(context);

      // Actualizamos el widget
      if (widgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
        MiWidget.actualizarWidget(context, widgetManager, widgetId);
      }
    }

    super.onReceive(context, intent);
  }

  public static void actualizarWidget(final Context context,
      final AppWidgetManager appWidgetManager, final int widgetId) {

    // Recuperamos el mensaje personalizado para el widget actual
    final SharedPreferences prefs = context.getSharedPreferences("WidgetPrefs", //$NON-NLS-1$
        Context.MODE_PRIVATE);
    final String mensaje = prefs.getString("msg_" + widgetId, //$NON-NLS-1$
        "Hora actual:"); //$NON-NLS-1$

    // Obtenemos la lista de controles del widget actual
    final RemoteViews controles = new RemoteViews(context.getPackageName(),
        R.layout.mi_widget);

    // Asociamos los 'eventos' al widget
    final Intent intent = new Intent("curso.android.ACTUALIZAR_WIDGET"); //$NON-NLS-1$
    intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
    final PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
        widgetId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

    controles.setOnClickPendingIntent(R.id.BtnActualizar, pendingIntent);

    // Actualizamos el mensaje en el control del widget
    controles.setTextViewText(R.id.LblMsg, mensaje);

    // Obtenemos la hora actual
    final Calendar calendario = new GregorianCalendar();
    final String hora = calendario.getTime().toLocaleString();

    // Actualizamos la hora en el control del widget
    controles.setTextViewText(R.id.LblHora, hora);

    // Notificamos al manager de la actualizaci�n del widget actual
    appWidgetManager.updateAppWidget(widgetId, controles);
  }
}
