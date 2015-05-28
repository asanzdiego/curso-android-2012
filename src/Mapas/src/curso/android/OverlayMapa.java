package curso.android;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class OverlayMapa extends Overlay {

  @Override
  public void draw(final Canvas canvas, final MapView mapView,
      final boolean shadow) {
    final Projection projection = mapView.getProjection();
    final GeoPoint geoPoint = new GeoPoint(MapaActivity.LATITUD.intValue(),
        MapaActivity.LONGITUD.intValue());

    if (shadow == false) {
      final Point centro = new Point();
      projection.toPixels(geoPoint, centro);

      // Definimos el pincel de dibujo
      final Paint p = new Paint();
      p.setColor(Color.BLUE);

      // Marca Ejemplo 1: Círculo y Texto
      canvas.drawCircle(centro.x, centro.y, 5, p);
      canvas.drawText("Sevilla", centro.x + 10, centro.y + 5, p); //$NON-NLS-1$

      // Marca Ejemplo 2: Bitmap
      final Bitmap bm = BitmapFactory.decodeResource(mapView.getResources(),
          R.drawable.marcador_google_maps);

      canvas.drawBitmap(bm, centro.x - bm.getWidth(),
          centro.y - bm.getHeight(), p);
    }
  }

  @SuppressWarnings("nls")
  @Override
  public boolean onTap(final GeoPoint point, final MapView mapView) {

    final Context contexto = mapView.getContext();
    final String msg = "Lat: " + point.getLatitudeE6() / 1E6 + " - " + "Lon: "
        + point.getLongitudeE6() / 1E6;

    final Toast toast = Toast.makeText(contexto, msg, Toast.LENGTH_SHORT);
    toast.show();

    return true;
  }
}
