package curso.android;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class ListViewActivity extends Activity {

  @SuppressWarnings("nls")
  private final Titular[] titulares = new Titular[] {
      new Titular("Título 1", "Subtítulo largo 1"),
      new Titular("Título 2", "Subtítulo largo 2"),
      new Titular("Título 3", "Subtítulo largo 3"),
      new Titular("Título 4", "Subtítulo largo 4"),
      new Titular("Título 5", "Subtítulo largo 5") };

  @Override
  public void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setContentView(R.layout.main);

    final TitularesAdapter adaptador = new TitularesAdapter(this,
        this.titulares);

    final ListView opciones = (ListView) this.findViewById(R.id.Opciones);

    opciones.setAdapter(adaptador);
  }
}
