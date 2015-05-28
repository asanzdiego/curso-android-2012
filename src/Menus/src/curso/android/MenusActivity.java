package curso.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class MenusActivity extends Activity {

  @SuppressWarnings("nls")
  final String[] datos = new String[] { "Elem1", "Elem2", "Elem3", "Elem4",
      "Elem5" };

  private TextView mensaje;
  private ListView lista;

  @Override
  public void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setContentView(R.layout.main);

    // Obtenemos las referencias a los controles
    this.mensaje = (TextView) this.findViewById(R.id.Mensaje);
    this.lista = (ListView) this.findViewById(R.id.Lista);

    final ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
        android.R.layout.simple_list_item_1, this.datos);

    this.lista.setAdapter(adaptador);

    // Asociamos los menus contextuales a los controles
    this.registerForContextMenu(this.mensaje);
    this.registerForContextMenu(this.lista);
  }

  @Override
  public boolean onCreateOptionsMenu(final Menu menu) {

    final MenuInflater inflater = this.getMenuInflater();
    inflater.inflate(R.menu.menu_principal, menu);

    return true;
  }

  @Override
  public void onCreateContextMenu(final ContextMenu menu, final View view,
      final ContextMenuInfo menuInfo) {
    super.onCreateContextMenu(menu, view, menuInfo);

    final MenuInflater inflater = this.getMenuInflater();

    if (view.getId() == R.id.Mensaje) {

      inflater.inflate(R.menu.menu_contextual_etiqueta, menu);

    } else if (view.getId() == R.id.Lista) {

      final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

      menu.setHeaderTitle(this.lista.getAdapter().getItem(info.position)
          .toString());

      inflater.inflate(R.menu.menu_contextual_lista, menu);
    }
  }

  @SuppressWarnings("nls")
  @Override
  public boolean onOptionsItemSelected(final MenuItem item) {
    switch (item.getItemId()) {
      case R.id.Menu1:
        this.mensaje.setText("Menú 1 pulsado!");
        return true;
      case R.id.Menu2:
        this.mensaje.setText("Menú 2 pulsado!");
        return true;
      case R.id.Menu3:
        this.mensaje.setText("Menú 3 pulsado!");
        return true;
      case R.id.Submenu31:
        this.mensaje.setText("Submenú 3.1 pulsado!");
        return true;
      case R.id.Submenu32:
        this.mensaje.setText("Submenú 3.2 pulsado!");
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @SuppressWarnings("nls")
  @Override
  public boolean onContextItemSelected(final MenuItem item) {

    final AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
        .getMenuInfo();

    switch (item.getItemId()) {
      case R.id.OpcionEtiqueta1:
        this.mensaje.setText("Etiqueta: Opcion 1 pulsada!");
        return true;
      case R.id.OpcionEtiqueta2:
        this.mensaje.setText("Etiqueta: Opcion 2 pulsada!");
        return true;
      case R.id.OpcionLista1:
        this.mensaje.setText("Lista[" + info.position + "]: Opcion 1 pulsada!");
        return true;
      case R.id.OpcionLista2:
        this.mensaje.setText("Lista[" + info.position + "]: Opcion 2 pulsada!");
        return true;
      default:
        return super.onContextItemSelected(item);
    }
  }
}