package curso.android;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.TextView;

public class RadioButtonActivity extends Activity {

  @Override
  public void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setContentView(R.layout.main);

    final TextView lblMensaje = (TextView) this.findViewById(R.id.Seleccion);
    final RadioGroup rg = (RadioGroup) this.findViewById(R.id.grupo);

    rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
      @SuppressWarnings("nls")
      public void onCheckedChanged(
          @SuppressWarnings("unused") final RadioGroup group,
          final int checkedId) {
        lblMensaje.setText("ID opción seleccionada: " + checkedId);
      }
    });
  }
}