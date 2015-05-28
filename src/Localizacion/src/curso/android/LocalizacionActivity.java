package curso.android;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class LocalizacionActivity extends Activity {

  private Button btnActualizar;
  private Button btnDesactivar;
  private TextView lblLatitud;
  private TextView lblLongitud;
  private TextView lblPrecision;
  TextView lblEstado;

  LocationManager locationManager;
  LocationListener locationListener;

  @Override
  public void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setContentView(R.layout.main);

    this.btnActualizar = (Button) this.findViewById(R.id.BtnActualizar);
    this.btnDesactivar = (Button) this.findViewById(R.id.BtnDesactivar);
    this.lblLatitud = (TextView) this.findViewById(R.id.LblPosLatitud);
    this.lblLongitud = (TextView) this.findViewById(R.id.LblPosLongitud);
    this.lblPrecision = (TextView) this.findViewById(R.id.LblPosPrecision);
    this.lblEstado = (TextView) this.findViewById(R.id.LblEstado);

    this.btnActualizar.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(@SuppressWarnings("unused") final View view) {
        LocalizacionActivity.this.actualizarPosicion();
      }
    });

    this.btnDesactivar.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(@SuppressWarnings("unused") final View view) {
        if (LocalizacionActivity.this.locationManager != null) {
          LocalizacionActivity.this.locationManager.removeUpdates(LocalizacionActivity.this.locationListener);
        }
      }
    });
  }

  void actualizarPosicion() {

    // Obtenemos una referencia al LocationManager
    this.locationManager = (LocationManager) this
        .getSystemService(Context.LOCATION_SERVICE);

    // Obtenemos la última posición conocida
    final Location location = this.locationManager
        .getLastKnownLocation(LocationManager.GPS_PROVIDER);

    // Mostramos la última posición conocida
    this.muestraPosicion(location);

    // Nos registramos para recibir actualizaciones de la posición
    this.locationListener = new LocationListener() {
      public void onLocationChanged(final Location newLocation) {
        LocalizacionActivity.this.muestraPosicion(newLocation);
      }

      public void onProviderDisabled(
          @SuppressWarnings("unused") final String provider) {
        LocalizacionActivity.this.lblEstado.setText("Provider OFF"); //$NON-NLS-1$
      }

      public void onProviderEnabled(
          @SuppressWarnings("unused") final String provider) {
        LocalizacionActivity.this.lblEstado.setText("Provider ON"); //$NON-NLS-1$
      }

      @SuppressWarnings("nls")
      public void onStatusChanged(
          @SuppressWarnings("unused") final String provider, final int status,
          @SuppressWarnings("unused") final Bundle extras) {
        Log.i("LocAndroid", "Provider Status: " + status);
        LocalizacionActivity.this.lblEstado.setText("Provider Status: " + status);
      }
    };

    this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
        15000, 0, this.locationListener);
  }

  @SuppressWarnings("nls")
  void muestraPosicion(final Location loc) {
    if (loc != null) {
      this.lblLatitud.setText("Latitud: " + String.valueOf(loc.getLatitude()));
      this.lblLongitud.setText("Longitud: "
          + String.valueOf(loc.getLongitude()));
      this.lblPrecision.setText("Precision: "
          + String.valueOf(loc.getAccuracy()));
      Log.i("LocAndroid", String.valueOf(loc.getLatitude() + " - "
          + String.valueOf(loc.getLongitude())));
    } else {
      this.lblLatitud.setText("Latitud: (sin_datos)");
      this.lblLongitud.setText("Longitud: (sin_datos)");
      this.lblPrecision.setText("Precision: (sin_datos)");
    }
  }
}