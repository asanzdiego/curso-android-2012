package curso.android;

import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class MapaActivity extends MapActivity {

  final static Double LATITUD = new Double(37.40 * 1E6);
  final static Double LONGITUD = new Double(-5.99 * 1E6);

  private Button btnSatelite = null;
  private Button btnCentrar = null;
  private Button btnAnimar = null;
  private Button btnMover = null;

  MapView mapa = null;
  MapController controlMapa = null;

  @Override
  public void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setContentView(R.layout.main);

    // Obtenemos una referencia a los controles
    this.mapa = (MapView) this.findViewById(R.id.mapa);
    this.btnSatelite = (Button) this.findViewById(R.id.BtnSatelite);
    this.btnCentrar = (Button) this.findViewById(R.id.BtnCentrar);
    this.btnAnimar = (Button) this.findViewById(R.id.BtnAnimar);
    this.btnMover = (Button) this.findViewById(R.id.BtnMover);

    // Controlador del mapa
    this.controlMapa = this.mapa.getController();

    // Mostramos los controles de zoom sobre el mapa
    this.mapa.setBuiltInZoomControls(true);

    // A�adimos la capa de marcas
    final List<Overlay> capas = this.mapa.getOverlays();
    final OverlayMapa om = new OverlayMapa();
    capas.add(om);
    this.mapa.postInvalidate();

    this.btnSatelite.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(@SuppressWarnings("unused") final View view) {
        if (MapaActivity.this.mapa.isSatellite()) {
          MapaActivity.this.mapa.setSatellite(false);
        } else {
          MapaActivity.this.mapa.setSatellite(true);
        }
      }
    });

    this.btnCentrar.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(@SuppressWarnings("unused") final View view) {

        final GeoPoint loc = new GeoPoint(MapaActivity.LATITUD.intValue(),
            MapaActivity.LONGITUD.intValue());

        MapaActivity.this.controlMapa.setCenter(loc);
        MapaActivity.this.controlMapa.setZoom(10);
      }
    });

    this.btnAnimar.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(@SuppressWarnings("unused") final View view) {

        final GeoPoint loc = new GeoPoint(MapaActivity.LATITUD.intValue(),
            MapaActivity.LONGITUD.intValue());

        MapaActivity.this.controlMapa.animateTo(loc);

        final int zoomActual = MapaActivity.this.mapa.getZoomLevel();
        for (int i = zoomActual; i < 10; i++) {
          MapaActivity.this.controlMapa.zoomIn();
        }
      }
    });

    this.btnMover.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(@SuppressWarnings("unused") final View view) {

        MapaActivity.this.controlMapa.scrollBy(40, 40);
      }
    });
  }

  @Override
  protected boolean isRouteDisplayed() {
    return true;
  }
}